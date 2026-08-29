package dev.fercho.inventstore.repository;

import dev.fercho.inventstore.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {


    boolean existsByCorreo(String correo);

    Optional<Usuario> findByCorreo(String correo);

    List<Usuario> findByActivoTrue();

    List<Usuario> findByActivoFalse();
}
