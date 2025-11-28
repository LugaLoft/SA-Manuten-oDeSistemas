package com.tcc2.ellemVeigaOficial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.tcc2.ellemVeigaOficial.models.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    @Query("SELECT p FROM Produto p WHERE " +
           "(:codigo IS NULL OR p.id = :codigo) AND " +
           "(:nome IS NULL OR p.nome LIKE %:nome%)")
    List<Produto> buscarProdutos(@Param("codigo") Long codigo,
                                @Param("nome") String nome);
}