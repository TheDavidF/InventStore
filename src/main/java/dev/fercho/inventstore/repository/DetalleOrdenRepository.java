package dev.fercho.inventstore.repository;

import dev.fercho.inventstore.model.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import java.util.List;

public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {


    Optional<DetalleOrden> findByIdAndOrdenId(Long id, Long detalleId);
}
