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

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_pro")
    private Long id;
	
	@Column(name = "nome_pro", nullable = false, length = 100)
    private String nome;
	@Column(name = "peso_pro", length = 100)
    private Float peso;
	@Column(name = "modelagem_pro", length = 100)
    private Float modelagem;
	@Column(name = "desenvolvimento_pro", length = 100)
    private Float desenvolvimento;
	@Column(name = "corte_pro", length = 100)
    private Float corte;
	@Column(name = "malha_pro", length = 100)
    private Float malha;
	@Column(name = "etimarc_pro", length = 100)
    private Float etiquetaMarca;
	@Column(name = "eticomp_pro", length = 100)
    private Float etiquetaComposicao;
	@Column(name = "tag_pro", length = 100)
    private Float tag;
	@Column(name = "refil_pro", length = 100)
    private Float refilPistola;
	@Column(name = "sacopla_pro", length = 100)
    private Float sacoPlastico;
	@Column(name = "fita_pro", length = 100)
    private Float fita;
	@Column(name = "elastico_pro", length = 100)
    private Float elastico;
	@Column(name = "valcostura_pro", length = 100)
    private Float precoCostura;
	@Column(name = "embalagem_pro", length = 100)
    private Float embalagem;
	@Column(name = "custo_pro", nullable = false,length = 100)
    private Float custoProduto;
	@Column(name = "fatormult_pro", nullable = false, length = 100)
    private Float fatormult;
	@Column(name = "valoratacado_pro", nullable = false, length = 100)
    private Float valoratacado;
	@Column(name = "valorvarejo_pro", nullable = false, length = 100)
    private Float valorvarejo;
    @Column(name = "deslocamento_pro", length = 100)
    private Float deslocamento;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "codusu_pro")
    private Usuario usuario;
}