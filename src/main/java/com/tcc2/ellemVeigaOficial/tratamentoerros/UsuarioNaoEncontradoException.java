package com.tcc2.ellemVeigaOficial.tratamentoerros;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }
}