package dev.fercho.inventstore.repository;

import dev.fercho.inventstore.model.ConsumoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsumoProductoRepository extends JpaRepository<ConsumoProducto, Long> {


    List<ConsumoProducto> findByProductoId(Long productoId);

    boolean existsByProductoIdAndInsumoId(
            Long productoId,
            Long insumoId
    );
}
