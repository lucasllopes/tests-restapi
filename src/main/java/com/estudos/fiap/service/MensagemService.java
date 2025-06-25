package com.estudos.fiap.service;

import com.estudos.fiap.model.Mensagem;

import java.util.UUID;

public interface MensagemService {

    Mensagem registrarMensagem(Mensagem mensagem);

    Mensagem obterMensagem(UUID id);


    Mensagem atualizarMensagem(UUID id, Mensagem mensagemNova);

    boolean removerMensagem(UUID id);
}
