package com.estudos.fiap.service;

import com.estudos.fiap.exception.MensagemNotFoundException;
import com.estudos.fiap.model.Mensagem;
import com.estudos.fiap.repository.MensagemRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MensagemServiceImpl implements MensagemService {

    private final MensagemRepository mensagemRepository;

    public MensagemServiceImpl(MensagemRepository mensagemRepository) {
        this.mensagemRepository = mensagemRepository;
    }

    @Override
    public Mensagem registrarMensagem(Mensagem mensagem) {
        mensagem.setId(UUID.randomUUID());
        return mensagemRepository.save(mensagem);
    }

    @Override
    public Mensagem obterMensagem(UUID id) {
        return mensagemRepository.findById(id).orElseThrow(() -> new MensagemNotFoundException("Mensagem nao encontrada"));
    }
}
