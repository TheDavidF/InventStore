package dev.fercho.inventstore.repository;

import dev.fercho.inventstore.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
