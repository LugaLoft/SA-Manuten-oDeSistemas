package com.tcc2.ellemVeigaOficial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tcc2.ellemVeigaOficial.models.FormaPagamento;
import com.tcc2.ellemVeigaOficial.models.Venda;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Date;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long>{
       List<Venda> findByDataBetween(Date dataInicio, Date dataFim);
       boolean existsByPedido(com.tcc2.ellemVeigaOficial.models.Pedido pedido);

       @Query("SELECT v FROM Venda v WHERE " +
              "v.data BETWEEN :dataInicio AND :dataFim AND " +
              "(:formaPagamento IS NULL OR v.formaPagamento = :formaPagamento)")
       List<Venda> findByDataBetweenAndFormaPagamento(
       @Param("dataInicio") Date dataInicio,
       @Param("dataFim") Date dataFim,
       @Param("formaPagamento") FormaPagamento formaPagamento);

       @Query("SELECT v FROM Venda v LEFT JOIN v.produtos p WHERE " +
              "(:idVenda IS NULL OR v.id = :idVenda) AND " +
              "(:nomeCliente IS NULL OR (v.cliente IS NOT NULL AND LOWER(v.cliente.nome) LIKE LOWER(CONCAT('%', :nomeCliente, '%')))) AND " +
              "(:idPedido IS NULL OR v.pedido.id = :idPedido) AND " +
              "(:dataInicial IS NULL OR v.data >= :dataInicial) AND " +
              "(:dataFinal IS NULL OR v.data <= :dataFinal)")
       List<Venda> buscarVendas(
              @Param("idVenda") Long idVenda,
              @Param("nomeCliente") String nomeCliente,
              @Param("idPedido") Long idPedido,
              @Param("dataInicial") Date dataInicial,
              @Param("dataFinal") Date dataFinal
       );


}