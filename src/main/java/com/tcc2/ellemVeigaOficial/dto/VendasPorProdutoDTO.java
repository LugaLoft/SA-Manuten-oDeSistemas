package com.tcc2.ellemVeigaOficial.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class VendasPorProdutoDTO {
    private String nomeProduto;
    private Double valorTotal;
}
