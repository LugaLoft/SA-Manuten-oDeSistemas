package com.tcc2.ellemVeigaOficial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.tcc2.ellemVeigaOficial.models.TipoStatus;
import com.tcc2.ellemVeigaOficial.models.Vendedor;


@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

    @Query("SELECT v FROM Vendedor v WHERE " +
           "(:id IS NULL OR v.id = :id) AND " +
           "(:nome IS NULL OR v.nome LIKE %:nome%) AND " +
           "(:sobrenome IS NULL OR v.sobrenome LIKE %:sobrenome%) AND " +
           "(:status IS NULL OR v.statusVendedor = :status)")
    List<Vendedor> buscarVendedores(@Param("id") Long id,
                                   @Param("nome") String nome,
                                   @Param("sobrenome") String sobrenome,
                                   @Param("status") TipoStatus status);
}