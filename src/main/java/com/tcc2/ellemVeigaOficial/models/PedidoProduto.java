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
public class PedidoProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_pp")
    private Long id;
	
	@Column(name = "qtd_pp", nullable = false, length = 100)
    private int quantidade;

	@Column(name = "observacao_pp", length = 100)
    private String observacao;

    @Column(name = "nome_pp", length = 100)
    private String nome;

    @Column(name = "tamanho_pp", nullable = false, length = 100)
    private String tamanho;
    
    @Column(name = "desconto_pp", length = 100)
    private Float desconto;

    @Column(name = "valortot_pp", length = 100)
    private Float valor_total;

    @Column(name = "valoruni_pp", length = 100)
    private Float valor_unitario;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "codpro_pp", nullable = false)
    private Produto produto;
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "codped_pp", nullable = false)
	private Pedido pedido;
}