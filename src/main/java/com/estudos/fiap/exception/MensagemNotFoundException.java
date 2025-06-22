package com.estudos.fiap.exception;

public class MensagemNotFoundException extends RuntimeException {

    public MensagemNotFoundException(String mensagem) {
        super(mensagem);
    }
}
