package com.estudos.fiap.service;

import com.estudos.fiap.helper.MensagemHelper;
import com.estudos.fiap.model.Mensagem;
import com.estudos.fiap.repository.MensagemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MensagemServiceTest {

    @Mock
    private MensagemRepository mensagemRepository;

    @InjectMocks
    private MensagemServiceImpl mensagemService;


    @Test
    void devePermitirRegistrarMensagem() {

        //Arrange
        var mensagem = MensagemHelper.getMensagem();
        when(mensagemRepository.save(any(Mensagem.class)))
                .thenAnswer(i -> {
                    Mensagem m = i.getArgument(0);
                    if (m.getId() == null) {
                        m.setId(UUID.randomUUID());
                    }
                    return m;
                });
        //Act
        var mensagemRegistrada = mensagemService.registrarMensagem(mensagem);

        //Assert
        assertThat(mensagemRegistrada).isNotNull()
                .isInstanceOf(Mensagem.class);

        assertThat(mensagemRegistrada.getId()).isNotNull();
        assertThat(mensagemRegistrada.getUsuario()).isEqualTo(mensagem.getUsuario());
        assertThat(mensagemRegistrada.getConteudo()).isEqualTo(mensagem.getConteudo());
    }

    @Test
    void devePermitirObterMensagemPorId(){
        //Arrange
        var uuid = UUID.randomUUID();
        var mensagem = MensagemHelper.getMensagem();
        mensagem.setId(uuid);

        when(mensagemRepository.findById(any(UUID.class))).thenReturn(Optional.of(mensagem));

        //Act
        var mensagemObtida = mensagemService.obterMensagem(uuid);

        //Assert
        Mockito.verify(mensagemRepository, Mockito.times(1)).findById(uuid);
        assertThat(mensagemObtida).isEqualTo(mensagem);

    }

    @Test
    void devePermitirObterMensagens(){
        fail();
    }

    @Test
    void devePermitirRemoverMensagem(){

        var uuid = UUID.randomUUID();
        var mensagem = MensagemHelper.getMensagem();
        mensagem.setId(uuid);

        when(mensagemRepository.findById(uuid)).thenReturn(Optional.of(mensagem));

        boolean mensagemRemovida = mensagemService.removerMensagem(uuid);

        assertThat(mensagemRemovida).isTrue();
        verify(mensagemRepository).delete(mensagem);
    }

    @Test
    void devePermitirModificarMensagem(){

        var id = UUID.randomUUID();
        var mensagemAntiga = MensagemHelper.getMensagem();
        mensagemAntiga.setId(id);

        var mensagemNova = MensagemHelper.getMensagem();
        mensagemNova.setConteudo("Novo Conteudo");

        when(mensagemRepository.findById(any(UUID.class))).thenReturn(Optional.of(mensagemAntiga));
        when(mensagemRepository.save(any(Mensagem.class))).thenAnswer(i -> i.getArgument(0));

        var mensagemObtida = mensagemService.atualizarMensagem(id, mensagemNova);

        assertThat(mensagemObtida).isInstanceOf(Mensagem.class).isNotNull();
        assertThat(mensagemObtida.getId()).isEqualTo(mensagemAntiga.getId());
        assertThat(mensagemObtida.getUsuario()).isEqualTo(mensagemNova.getUsuario());
        assertThat(mensagemObtida.getConteudo()).isEqualTo(mensagemNova.getConteudo());
        verify(mensagemRepository, times(1)).save(any(Mensagem.class));

    }
}


