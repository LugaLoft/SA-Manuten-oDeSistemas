package com.tcc2.ellemVeigaOficial.models;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FluxoCaixa {
    private LocalDate data;
    private String nomeProduto;
    private String nomeCliente;
    private Long idVenda;
    private Float desconto;
    private Float valor;
    private Float valorSemComissao;
    private FormaPagamento formaPagamento;
    private String tipo;
}
