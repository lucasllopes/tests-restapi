package com.estudos.fiap.service;

import com.estudos.fiap.helper.MensagemHelper;
import com.estudos.fiap.model.Mensagem;
import com.estudos.fiap.repository.MensagemRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class MensagemServiceIT {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private MensagemService mensagemService;

    @Test
    void devePermitirRegistrarMensagem() {

        var mensagem = MensagemHelper.getMensagem();

        var mensagemRegistrada = mensagemService.registrarMensagem(mensagem);

        assertThat(mensagemRegistrada).isNotNull().isInstanceOf(Mensagem.class);
        assertThat(mensagemRegistrada.getId()).isNotNull();
        assertThat(mensagemRegistrada.getUsuario()).isEqualTo(mensagem.getUsuario());
        assertThat(mensagemRegistrada.getConteudo()).isEqualTo(mensagem.getConteudo());

    }

    @Test
    void devePermitirObterMensagem() {

        var id = UUID.fromString("2bb84a7a-daac-4ea2-8497-70de0880bdb3");
        var mensagemObtida = mensagemService.obterMensagem(id);

        assertThat(mensagemObtida).isNotNull()
                .isInstanceOf(Mensagem.class);

        assertThat(mensagemObtida.getUsuario()).isNotNull();
        assertThat(mensagemObtida.getConteudo()).isNotNull();
        assertThat(mensagemObtida.getId()).isNotNull();

    }

    @Test
    void devePermitirRemoverMensagem() {

        //Arrange
        var id = UUID.fromString("2bb84a7a-daac-4ea2-8497-70de0880bdb3");

        //Act
        var mensagemRemovida = mensagemService.removerMensagem(id);

        //Assert
        assertThat(mensagemRemovida).isTrue();

    }
}
