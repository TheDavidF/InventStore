package dev.fercho.inventstore.repository;

import dev.fercho.inventstore.model.Caja;
import dev.fercho.inventstore.model.enums.EstadoCaja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CajaRepository extends JpaRepository<Caja, Long> {

    boolean existsByEstadoAndUsuarioId(EstadoCaja estado, Long usuarioId);

    Optional<Caja> findByEstadoAndUsuarioId(EstadoCaja estadoCaja, Long usuarioId);


}
