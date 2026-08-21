package dev.fercho.inventstore.service;

import dev.fercho.inventstore.dto.request.AjustarInventarioRequest;
import dev.fercho.inventstore.dto.request.CrearMovimientoInventarioRequest;
import dev.fercho.inventstore.model.Insumo;
import dev.fercho.inventstore.model.MovimientoInventario;
import dev.fercho.inventstore.model.enums.TipoMovimientoInventario;
import dev.fercho.inventstore.repository.InsumoRepository;
import dev.fercho.inventstore.repository.MovimientoInventarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovimientoInventarioService {

    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private final InsumoRepository insumoRepository;
    public MovimientoInventarioService(MovimientoInventarioRepository movimientoInventarioRepository, InsumoRepository insumoRepository) {
        this.movimientoInventarioRepository = movimientoInventarioRepository;
        this.insumoRepository = insumoRepository;
    }

    @Transactional
    public MovimientoInventario registrarSalidaVenta(
            Insumo insumo,
            Integer cantidad,
            String motivo) {

        Integer stockAnterior = insumo.getStockActual();

        aplicarMovimiento(
                insumo,
                TipoMovimientoInventario.SALIDA_VENTA,
                cantidad
        );

        Integer stockPosterior = insumo.getStockActual();

        MovimientoInventario movimiento = new MovimientoInventario();

        movimiento.setInsumo(insumo);
        movimiento.setTipo(TipoMovimientoInventario.SALIDA_VENTA);
        movimiento.setCantidad(cantidad);
        movimiento.setMotivo(motivo);
        movimiento.setStockAnterior(stockAnterior);
        movimiento.setStockPosterior(stockPosterior);

        return movimientoInventarioRepository.save(movimiento);
    }


    /*
     *
     * Ese método debe recibir:

        el Insumo;
        la cantidad real en unidades individuales;
        un motivo.

        Y debe registrar un movimiento de tipo ENTRADA, guardando también stockAnterior y stockPosterior.

        Después, en CompraService, al pagar una compra:

        calcular el total;
        recorrer todos los DetalleCompra;
        calcular cantidad × unidadesPorPresentacion;
        llamar al método de entrada de inventario para cada detalle;
        cambiar la compra a PAGADA;
        asignar fechaPagada;
        guardar todo dentro de la misma transacción.
     *
     *
     */


    @Transactional
    public MovimientoInventario registrarEntradaCompra(
            Insumo insumo,
            Integer cantidad,
            String motivo) {

        Integer stockAnterior = insumo.getStockActual();

         aplicarMovimiento(insumo, TipoMovimientoInventario.ENTRADA, cantidad);

        Integer stockPosterior = insumo.getStockActual();

        MovimientoInventario movimiento = new MovimientoInventario();

        movimiento.setInsumo(insumo);
        movimiento.setTipo(TipoMovimientoInventario.ENTRADA);
        movimiento.setCantidad(cantidad);
        movimiento.setMotivo(motivo);
        movimiento.setStockAnterior(stockAnterior);
        movimiento.setStockPosterior(stockPosterior);

        return movimientoInventarioRepository.save(movimiento);

    }

    private MovimientoInventario registrarMovimiento(
            CrearMovimientoInventarioRequest request, TipoMovimientoInventario tipo) {

        Insumo insumo = insumoRepository.findById(request.getInsumoId())
                .orElseThrow(() ->
                        new RuntimeException("No se encontró el insumo")
                );

        Integer stockAnterior = insumo.getStockActual();

        aplicarMovimiento(
                insumo,
                tipo,
                request.getCantidad()
        );

        Integer stockPosterior = insumo.getStockActual();

        MovimientoInventario movimiento = new MovimientoInventario();

        movimiento.setInsumo(insumo);
        movimiento.setTipo(tipo);
        movimiento.setCantidad(request.getCantidad());
        movimiento.setMotivo(request.getMotivo());
        movimiento.setStockAnterior(stockAnterior);
        movimiento.setStockPosterior(stockPosterior);

        return movimientoInventarioRepository.save(movimiento);
    }


    @Transactional
    public MovimientoInventario registrarEntrada(CrearMovimientoInventarioRequest request){

        return registrarMovimiento(request, TipoMovimientoInventario.ENTRADA);
    }

    @Transactional
    public MovimientoInventario registrarMerma(CrearMovimientoInventarioRequest request){

        return registrarMovimiento(request, TipoMovimientoInventario.MERMA);
    }

    @Transactional
    public MovimientoInventario ajustarInventario(AjustarInventarioRequest request){
        Insumo insumo = insumoRepository.findById(request.getInsumoId())
                .orElseThrow(() ->
                        new RuntimeException("No se encontró el insumo")
                );
        Integer stockAnterior = insumo.getStockActual();
        Integer nuevoStock = request.getNuevoStock();

        insumo.setStockActual(nuevoStock);


        MovimientoInventario movimiento = new MovimientoInventario();

        movimiento.setInsumo(insumo);
        movimiento.setTipo(TipoMovimientoInventario.AJUSTE);
        movimiento.setMotivo(request.getMotivo());
        movimiento.setCantidad(
                Math.abs(nuevoStock - stockAnterior)
        );
        movimiento.setStockAnterior(stockAnterior);
        movimiento.setStockPosterior(nuevoStock);


        return movimientoInventarioRepository.save(movimiento);
    }


    public List<MovimientoInventario> obtenerMovimientos(){
        return movimientoInventarioRepository.findAll();
    }

    private void aplicarMovimiento(
            Insumo insumo,
            TipoMovimientoInventario tipo,
            Integer cantidad) {

        switch (tipo) {

            case ENTRADA, DEVOLUCION ->
                    insumo.setStockActual(
                            insumo.getStockActual() + cantidad
                    );

            case SALIDA_VENTA, MERMA -> {
                    if (insumo.getStockActual() < cantidad) {
                        throw new RuntimeException("Stock insuficiente");
                    }

                    insumo.setStockActual(
                            insumo.getStockActual() - cantidad
                    );
                    }
        }
    }
}
