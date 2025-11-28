package com.tcc2.ellemVeigaOficial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import com.tcc2.ellemVeigaOficial.dto.VendasPorProdutoDTO;
import com.tcc2.ellemVeigaOficial.models.ProdutoVenda;
import com.tcc2.ellemVeigaOficial.models.Venda;

@Repository
public interface ProdutoVendaRepository extends JpaRepository<ProdutoVenda, Long> {
    List<ProdutoVenda> findByVenda(Venda venda);
    boolean existsByProdutoId(Long produtoId);

    @Query("SELECT pv FROM ProdutoVenda pv WHERE " +
           "(:codigoVenda IS NULL OR pv.venda.id = :codigoVenda) AND " +
           "(:nomeProduto IS NULL OR pv.produto.nome LIKE %:nomeProduto%)")
    List<ProdutoVenda> buscarProdutoVendas(@Param("codigoVenda") Long codigoVenda,
                                          @Param("nomeProduto") String nomeProduto);

       @Query("SELECT new com.tcc2.ellemVeigaOficial.dto.VendasPorProdutoDTO(pv.nome, SUM(pv.valorTotal)) " +
       "FROM ProdutoVenda pv " +
       "WHERE pv.venda.data BETWEEN :dataInicial AND :dataFinal " +
       "GROUP BY pv.nome")
List<VendasPorProdutoDTO> agruparVendasPorNomeProduto(
        @Param("dataInicial") Date dataInicial,
        @Param("dataFinal") Date dataFinal);
}