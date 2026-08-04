package dev.fercho.inventstore.repository;

import dev.fercho.inventstore.model.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {

}
