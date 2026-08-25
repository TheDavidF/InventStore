package dev.fercho.inventstore.service;

import dev.fercho.inventstore.model.Caja;
import dev.fercho.inventstore.model.Compra;
import dev.fercho.inventstore.model.Insumo;
import dev.fercho.inventstore.model.Orden;
import dev.fercho.inventstore.model.enums.EstadoCaja;
import dev.fercho.inventstore.repository.CajaRepository;
import dev.fercho.inventstore.repository.CompraRepository;
import dev.fercho.inventstore.repository.InsumoRepository;
import dev.fercho.inventstore.repository.OrdenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Service
public class ReporteService {

    private final OrdenRepository ordenRepository;
    private final CompraRepository compraRepository;

    private final InsumoRepository insumoRepository;

    private final CajaRepository cajaRepository;

    public ReporteService(OrdenRepository ordenRepository
            , CompraRepository compraRepository
            ,InsumoRepository insumoRepository
            ,CajaRepository cajaRepository) {
        this.ordenRepository = ordenRepository;
        this.compraRepository = compraRepository;
        this.insumoRepository = insumoRepository;
        this.cajaRepository = cajaRepository;
    }

    public List<Orden> obtenerVentas(LocalDate desde, LocalDate hasta){
        LocalDateTime inicio = LocalDateTime.of(desde, LocalTime.MIN);
        LocalDateTime fin = LocalDateTime.of(hasta, LocalTime.MAX);
        return ordenRepository.findByFechaCreacionBetweenAndPagoIsNotNull(inicio, fin);
    }

    public List<Compra> obtenerCompras(LocalDate desde, LocalDate hasta){
        LocalDateTime inicio = LocalDateTime.of(desde, LocalTime.MIN);
        LocalDateTime fin = LocalDateTime.of(hasta, LocalTime.MAX);
        return compraRepository.findByFechaCreacionBetween(inicio, fin);
    }

    public List<Insumo> obtenerInventario(){
        return insumoRepository.findAll();
    }

    public List<Caja> obtenerCajas(LocalDate desde, LocalDate hasta) {

        LocalDateTime inicio = LocalDateTime.of(desde, LocalTime.MIN);
        LocalDateTime fin = LocalDateTime.of(hasta, LocalTime.MAX);

        return cajaRepository.findByFechaCierreBetweenAndEstado(
                inicio,
                fin,
                EstadoCaja.CERRADA
        );
    }

}
