package dev.fercho.inventstore.repository;

import dev.fercho.inventstore.model.Caja;
import dev.fercho.inventstore.model.enums.EstadoCaja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CajaRepository extends JpaRepository<Caja, Long> {

    boolean existsByEstadoAndUsuarioId(EstadoCaja estado, Long usuarioId);

    Optional<Caja> findByEstadoAndUsuarioId(EstadoCaja estadoCaja, Long usuarioId);

/*
* crear la consulta para obtener cajas por rango de fechaCierre y, de preferencia, solo las que estén en estado CERRADA.
*
*
* */

    List<Caja> findByFechaCierreBetweenAndEstado(LocalDateTime desde, LocalDateTime hasta, EstadoCaja estado);

}
