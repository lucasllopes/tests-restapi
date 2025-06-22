package com.estudos.fiap.helper;

import com.estudos.fiap.model.Mensagem;

public abstract class MensagemHelper {

    public static Mensagem getMensagem(){
        return Mensagem.builder()
                .usuario("Lucas")
                .conteudo("Conteudo")
                .build();
    }
}
