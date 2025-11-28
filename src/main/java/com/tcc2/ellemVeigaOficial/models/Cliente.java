package com.tcc2.ellemVeigaOficial.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_cli")
    private Long id;
    @Column(name = "nome_cli", nullable = false, length = 100)
    private String nome;
    @Column(name = "observacao_cli", length = 255)
    private String observacao;

    @Column(name = "logadouro_cli", nullable = false, length = 255)
    private String logadouro;
    @Column(name = "cidade_cli", length = 100)
    private String cidade;
    @Column(name = "estado_cli", length = 100)
    private String estado;
    @Column(name = "cep_cli", length = 10)
    private String cep;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "codusu_cli")
    private Usuario usuario;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipent_cli", length = 25)
    private TipoEntrega tipo_entrega;
    @Enumerated(EnumType.STRING)
    @Column(name = "tippag_cli", length = 25)
    private FormaPagamento forma_pagamento;

}