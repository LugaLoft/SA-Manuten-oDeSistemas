package com.tcc2.ellemVeigaOficial.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_usu")
    private Long id;

    @Column(name = "nome_usu", nullable = false, length = 100)
    private String nome;

    @Column(name = "sobrenome_usu", length = 100)
    private String sobrenome;

    @Column(name = "usuario_usu", unique = true, nullable = false, length = 50)
    private String usuario;
    
    @Column(name = "email_usu", unique = true, nullable = false, length = 100)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "senha_usu", nullable = false)
    private String senha;

    public Usuario(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }
}