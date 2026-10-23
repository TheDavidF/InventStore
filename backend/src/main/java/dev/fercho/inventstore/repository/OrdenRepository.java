package dev.fercho.inventstore.repository;

import dev.fercho.inventstore.model.Orden;
import dev.fercho.inventstore.model.Usuario;
import dev.fercho.inventstore.model.enums.EstadoOrden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Long> {

    List<Orden> findByEstado(EstadoOrden estado);

    List<Orden> findByFechaCreacionBetweenAndPagoIsNotNull(LocalDateTime inicio , LocalDateTime fin);
    List<Orden> findByFechaCreacionBetween(LocalDateTime inicio , LocalDateTime fin);

    List<Orden> findByUsuario(Usuario usuario);

}
