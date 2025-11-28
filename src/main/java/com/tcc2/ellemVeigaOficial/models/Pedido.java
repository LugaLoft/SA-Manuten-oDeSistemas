package com.tcc2.ellemVeigaOficial.models;

import java.util.Date;
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

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_ped")
    private Long id;
	
	@Column(name = "data_ped", nullable = false, length = 100)
    private Date data;

	@Column(name = "descrevend_ped", length = 100)
    private Float desconto_revendedor;

	@Column(name = "frete_ped", length = 100)
    private Float frete;

	@Column(name = "valtot_ped", nullable = false, length = 100)
    private Float valor_total;

	@Column(name = "valent_ped", nullable = false, length = 100)
    private Float valor_entrada;

    @Column(name = "valrest_ped", nullable = false, length = 100)
    private Float valor_restante;

	@Column(name = "qtdparcela_ped", nullable = false, length = 100)
    private int qtd_parcela;

	@Column(name = "dataent_ped", length = 100)
    private Date data_entrega;

    @Enumerated(EnumType.STRING)
	@Column(name = "status_ped", length = 25)
    private TipoStatus status_pedido;
		
    @Enumerated(EnumType.STRING)
	@Column(name = "forpag_ped", length = 25)
    private FormaPagamento forma_pagamento;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "codusu_ped")
	private Usuario usuario;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "codcli_ped")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipent_ped", length = 25)
    private TipoEntrega tipo_entrega;

}