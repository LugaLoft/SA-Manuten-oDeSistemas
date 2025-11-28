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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_ven")
    private Long id;
	@Column(name = "data_ven", nullable = false, length = 100)
    private Date data;
	@Column(name = "comissao_ven", length = 100)
    private Float comissao;
	@Column(name = "valtot_ven", nullable = false, length = 100)
    private Float valorTotal;
	@Column(name = "qtdparcela_ven", length = 100)
    private int parcela;
	@Column(name = "observacao_ven", length = 100)
    private String observacao;
	@Column(name = "frete_ven", length = 100)
    private Float frete;
	@Column(name = "valent_ven", nullable = false, length = 100)
    private Float valorEntrada;
	@Column(name = "valrest_ven", nullable = false, length = 100)
    private Float valorRestante;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "codusu_ven")
	private Usuario usuario;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "codped_ven")
    private Pedido pedido;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "codcli_ven")
    private Cliente cliente;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "codvend_ven")
    private Vendedor vendedor;
	
    @Enumerated(EnumType.STRING)
	@Column(name = "forpag_ven", length = 25)
    private FormaPagamento formaPagamento;
	
    @Enumerated(EnumType.STRING)
	@Column(name = "tipent_ven", length = 25)
    private TipoEntrega tipoEntrega;
}