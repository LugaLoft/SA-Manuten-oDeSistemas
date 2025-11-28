package com.tcc2.ellemVeigaOficial.tratamentoerros;

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException(String message) {
        super(message);
    }
}
