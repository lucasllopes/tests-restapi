package com.estudos.fiap.repository;

import com.estudos.fiap.helper.MensagemHelper;
import com.estudos.fiap.model.Mensagem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MensagemRepositoryTest {

    @Mock
    private MensagemRepository mensagemRepository;


    @Test
    void devePermitirRegistrarMensagem() {
        //Arrange
        var mensagem = MensagemHelper.getMensagem();
        when(mensagemRepository.save(any(Mensagem.class))).thenReturn(mensagem);

        // Act
        var mensagemArmazenada = mensagemRepository.save(mensagem);

        // Assert
        verify(mensagemRepository, times(1)).save(mensagem);
    }

    @Test
    void devePermitirConsultarMensagem() {

        //Arrange
        var id = UUID.randomUUID();
        var mensagem = MensagemHelper.getMensagem();
        mensagem.setId(id);

        when(mensagemRepository.findById(any(UUID.class))).thenReturn(Optional.of(mensagem));

        //Act
        var mensagemEncontrada = mensagemRepository.findById(id);

        //Assert
        Assertions.assertThat(mensagemEncontrada).isNotNull()
                .containsSame(mensagem);
    }

    @Test
    void devePermitirDeletarMensagem() {

        //Arrange
        var id = UUID.randomUUID();
        var mensagem = MensagemHelper.getMensagem();
        mensagem.setId(id);

        doNothing().when(mensagemRepository).deleteById(any(UUID.class));

        //Act
        mensagemRepository.deleteById(id);

        //Assert
        verify(mensagemRepository, times(1)).deleteById(id);
    }



}
