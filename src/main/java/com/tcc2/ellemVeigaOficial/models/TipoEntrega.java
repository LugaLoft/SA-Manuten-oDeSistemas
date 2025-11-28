package com.tcc2.ellemVeigaOficial.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoEntrega {
    CARRO("Carro"),
    SEDEX("SEDEX"),
    RETIRADA("Retirada"),
    TRANSPORTADORA("Transportadora");

    private final String descricao;
}
