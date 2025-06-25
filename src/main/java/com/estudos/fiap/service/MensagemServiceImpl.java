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
        return mensagemRepository.save(mensagem);
    }

    @Override
    public Mensagem obterMensagem(UUID id) {
        return mensagemRepository.findById(id).orElseThrow(() -> new MensagemNotFoundException("Mensagem nao encontrada"));
    }

    @Override
    public Mensagem atualizarMensagem(UUID id, Mensagem novaMensagem) {

        Mensagem mensagemDb = mensagemRepository.findById(id)
                .orElseThrow(() -> new MensagemNotFoundException("Mensagem não encontrada com ID: " + id));

        mensagemDb.setConteudo(novaMensagem.getConteudo());
        mensagemDb.setUsuario(novaMensagem.getUsuario());

        return mensagemRepository.save(mensagemDb);
    }

    @Override
    public boolean removerMensagem(UUID id){
        var mensagem = obterMensagem(id);
        mensagemRepository.delete(mensagem);
        return true;
    }
}
