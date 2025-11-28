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
public class Vendedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_vend")
    private Long id;
	@Column(name = "nome_vend", nullable = false, length = 100)
    private String nome;
	@Column(name = "sobrenome_vend", length = 100)
    private String sobrenome;
	@Column(name = "email_vend", length = 100)
    private String email;
	@Column(name = "desconto_vend", nullable = false, length = 100)
    private float desconto;
	@Column(name = "observacao_vend", length = 100)
    private String observacao;

    @Enumerated(EnumType.STRING)
	@Column(name = "tipsta_vend", length = 25)
    private TipoStatus statusVendedor;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "codusu_vend")
    private Usuario usuario;
}