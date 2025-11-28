package com.tcc2.ellemVeigaOficial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.Date;
import java.util.List;
import com.tcc2.ellemVeigaOficial.models.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    @Query("SELECT p FROM Pedido p WHERE " +
               "(:codigo IS NULL OR p.id = :codigo) AND " +
               "(:startDate IS NULL OR p.data BETWEEN :startDate AND :endDate)")
        List<Pedido> buscarPedidos(@Param("codigo") Long codigo,
                                 @Param("startDate") Date startDate,
                                 @Param("endDate") Date endDate);
}