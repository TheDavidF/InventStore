package dev.fercho.inventstore.repository;

import dev.fercho.inventstore.model.Compra;
import dev.fercho.inventstore.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {


    List<Compra> findByFechaCreacionBetween(LocalDateTime inicio , LocalDateTime fin);
}
