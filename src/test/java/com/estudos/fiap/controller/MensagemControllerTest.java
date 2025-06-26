package com.estudos.fiap.controller;

import com.estudos.fiap.exception.MensagemNotFoundException;
import com.estudos.fiap.handler.GlobalExceptionHandler;
import com.estudos.fiap.helper.MensagemHelper;
import com.estudos.fiap.model.Mensagem;
import com.estudos.fiap.service.MensagemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MensagemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MensagemService mensagemService;

    @InjectMocks
    private MensagemController mensagemController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(mensagemController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((servletRequest, servletResponse, filterChain) ->{
                    servletResponse.setCharacterEncoding("UTF-8");
                    filterChain.doFilter(servletRequest, servletResponse);
                },"/*" )
                .build();
    }

    @Test
    void devePermitirRegistrarMensagem() throws Exception {
        //Arrange
        var mensagemRequest = MensagemHelper.getMensagem();
        when(mensagemService.registrarMensagem(any(Mensagem.class))).thenAnswer(i -> i.getArgument(0));

        //Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/mensagens").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(mensagemRequest))
                ).andDo(print())
                .andExpect(status().isCreated());
        verify(mensagemService, times(1)).registrarMensagem(any(Mensagem.class));

    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void devePermitirObterMensagem() throws Exception {

        //Arrange
        var id = UUID.fromString("c0a801f6-3e97-4cbe-943e-5f235a6e2b0f");

        var mensagemResponse = MensagemHelper.getMensagem();
        mensagemResponse.setId(id);
        mensagemResponse.setDataCriacao(LocalDateTime.now());
        mensagemResponse.setDataAlteracao(LocalDateTime.now());

        when(mensagemService.obterMensagem(any(UUID.class))).thenReturn(mensagemResponse);

        //Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/mensagens/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(mensagemService, times(1)).obterMensagem(any(UUID.class));

    }

    @Test
    void deveGerarExceptionAoObterMensagemComIdNaoExistente() throws Exception {

        //Arrange
        var id = UUID.fromString("c0a801f6-3e97-4cbe-943e-5f235a6e2b0f");

        when(mensagemService.obterMensagem(any(UUID.class))).thenThrow(new MensagemNotFoundException("Mensagem nao encontrada"));

        //Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/mensagens/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(mensagemService, times(1)).obterMensagem(any(UUID.class));
    }

    @Test
    void deveGerarExceptionAoObterMensagemComIdInvalido() throws Exception {

        //Arrange
        var id = "123";

        //Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/mensagens/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
