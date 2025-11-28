package com.tcc2.ellemVeigaOficial.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class ProdutoVenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_pv")
    private Long id;
	@Column(name = "qtd_pv", nullable = false, length = 100)
    private int quantidade;
	@Column(name = "tamanho_pv", nullable = false, length = 100)
    private String tamanho;
	@Column(name = "desconto_pv", length = 100)
    private Float desconto;
	@Column(name = "observacao_pv", length = 100)
	private String observacao;

	@Column(name = "valortot_pv", length = 100)
    private Double valorTotal;
	@Column(name = "valoruni_pv", length = 100)
    private Float valorUnitario;
	@Column(name = "nome_pv", nullable = false, length = 100)
    private String nome;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "codpro_pv", nullable = false)
    private Produto produto;
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "codven_pv", nullable = false)
    private Venda venda;
}