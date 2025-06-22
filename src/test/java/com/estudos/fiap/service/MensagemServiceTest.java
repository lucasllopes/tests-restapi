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
        Mockito.when(mensagemRepository.save(Mockito.any(Mensagem.class))).thenAnswer(i -> i.getArgument(0));

        //Act
        var mensagemRegistrada = mensagemService.registrarMensagem(mensagem);

        //Assert
        Assertions.assertThat(mensagemRegistrada).isNotNull()
                .isInstanceOf(Mensagem.class);

        Assertions.assertThat(mensagemRegistrada.getId()).isNotNull();
        Assertions.assertThat(mensagemRegistrada.getUsuario()).isEqualTo(mensagem.getUsuario());
        Assertions.assertThat(mensagemRegistrada.getConteudo()).isEqualTo(mensagem.getConteudo());
    }

    @Test
    void devePermitirObterMensagemPorId(){
        //Arrange
        var uuid = UUID.randomUUID();
        var mensagem = MensagemHelper.getMensagem();
        mensagem.setId(uuid);

        Mockito.when(mensagemRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(mensagem));

        //Act
        var mensagemObtida = mensagemService.obterMensagem(uuid);

        //Assert
        Mockito.verify(mensagemRepository, Mockito.times(1)).findById(uuid);
        Assertions.assertThat(mensagemObtida).isEqualTo(mensagem);

    }

    @Test
    void devePermitirObterMensagens(){
        Assertions.fail();
    }

    @Test
    void devePermitirRemoverMensagem(){
        Assertions.fail();
    }

    @Test
    void devePermitirModificarMensagem(){
        Assertions.fail();
    }
}


