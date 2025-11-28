package com.tcc2.ellemVeigaOficial.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.tcc2.ellemVeigaOficial.models.Usuario;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsuario(String usuario);
    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE " +
           "(:codigo IS NULL OR u.id = :codigo) AND " +
           "(:nome IS NULL OR u.nome LIKE %:nome%) AND " +
           "(:sobrenome IS NULL OR u.sobrenome LIKE %:sobrenome%)")
    List<Usuario> buscarUsuarios(@Param("codigo") Long codigo,
                                                 @Param("nome") String nome,
                                                 @Param("sobrenome") String sobrenome);
}