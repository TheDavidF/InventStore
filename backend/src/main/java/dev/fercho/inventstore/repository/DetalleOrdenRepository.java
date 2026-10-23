package dev.fercho.inventstore.repository;

import dev.fercho.inventstore.model.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {


    Optional<DetalleOrden> findByIdAndOrdenId(Long detalleId, Long id );
}
