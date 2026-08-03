package dev.fercho.inventstore.repository;

import dev.fercho.inventstore.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {


        List<Producto> findAllByActivoTrue();
        List<Producto> findAllByActivoFalse();
}
