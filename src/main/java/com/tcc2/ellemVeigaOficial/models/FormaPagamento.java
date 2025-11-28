package com.tcc2.ellemVeigaOficial.models;

public enum FormaPagamento {
    DINHEIRO("Dinheiro"),
    PIX("Pix"),
    CREDITO("Cartão de Crédito"),
    DEBITO("Cartão de Débito"),
    TED("TED/DOC");

    private FormaPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    private final String descricao;
}
