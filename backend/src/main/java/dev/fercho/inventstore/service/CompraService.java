package dev.fercho.inventstore.service;

import dev.fercho.inventstore.dto.request.AgregarDetalleCompraRequest;
import dev.fercho.inventstore.dto.request.CrearCompraRequest;
import dev.fercho.inventstore.exception.ConflictoEstadoException;
import dev.fercho.inventstore.exception.PeticionInvalidaException;
import dev.fercho.inventstore.exception.RecursoNoEncontradoException;
import dev.fercho.inventstore.model.Compra;
import dev.fercho.inventstore.model.DetalleCompra;
import dev.fercho.inventstore.model.Insumo;
import dev.fercho.inventstore.model.Proveedor;
import dev.fercho.inventstore.model.enums.EstadoCompra;
import dev.fercho.inventstore.repository.CompraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final ProveedorService proveedorService;
    private final MovimientoInventarioService movimientoInventarioService;


    private final  InsumoService insumoService;

    public CompraService(CompraRepository compraRepository, ProveedorService proveedorService
            ,InsumoService insumoService
            ,MovimientoInventarioService movimientoInventarioService) {
        this.compraRepository = compraRepository;
        this.proveedorService = proveedorService;
        this.insumoService = insumoService;
        this.movimientoInventarioService = movimientoInventarioService;
    }


    @Transactional
    public Compra crearCompra(CrearCompraRequest request){
        Proveedor proveedor = proveedorService.buscarPorId(request.getProveedorId());
        Compra compra = new Compra();
        compra.setProveedor(proveedor);
        if(request.getDetalles() != null){
            for (AgregarDetalleCompraRequest detalleRequest:
                    request.getDetalles()) {
                agregarDetalle(compra,detalleRequest);
            }
        }

        compraRepository.save(compra);

        for (DetalleCompra detalle:
                compra.getDetallesCompra()) {

            Integer cantidadReal =
                    detalle.getCantidad()
                            * detalle.getUnidadesPorPresentacion();

            movimientoInventarioService.registrarEntradaCompra(detalle.getInsumo()
                    ,cantidadReal
                    ,"Entrada Por compra #" +compra.getId() );
        }

        return compra;
    }

    @Transactional(readOnly = true)
    public List<Compra> obtenerCompras(){
        return compraRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Compra obtenerPorId(Long id){
        return compraRepository.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("Compra no encontrada"));
    }

    @Transactional
    public Compra agregarDetalleCompra(Long id, AgregarDetalleCompraRequest req){
        Compra compra = obtenerPorId(id);
        agregarDetalle(compra, req);
        return compraRepository.save(compra);
    }

    @Transactional
    public Compra pagarCompra(Long id){
        Compra compra = obtenerPorId(id);
        if(compra.getDetallesCompra().isEmpty()){
            throw new PeticionInvalidaException("La compra no tiene insumos registrados");
        }
        if (compra.getEstado() != EstadoCompra.PENDIENTE_PAGO) {
            throw new ConflictoEstadoException("La compra ya fue pagada");
        }
        calcularTotal(compra);
        compra.setEstado(EstadoCompra.PAGADA);
        compra.setFechaPagada(LocalDateTime.now());
        return compraRepository.save(compra);

    }


    private void calcularTotal(Compra compra){

        BigDecimal total = BigDecimal.ZERO;
        for (DetalleCompra detalle:
                compra.getDetallesCompra()) {
            if(detalle.getSubtotal() == null){
                throw new PeticionInvalidaException("Todos los detalles deben tener subtotal");
            }
            total = total.add(detalle.getSubtotal());
        }
        compra.setTotal(total);
    }

    private void agregarDetalle(
            Compra compra,
            AgregarDetalleCompraRequest req) {

        if (compra.getEstado() != EstadoCompra.PENDIENTE_PAGO) {
            throw new ConflictoEstadoException("La compra ya ha sido pagada");
        }

        Insumo insumo = insumoService.getById(req.getInsumoId());

        DetalleCompra detalleCompra = new DetalleCompra();
        detalleCompra.setCantidad(req.getCantidad());
        detalleCompra.setObservacion(req.getObservacion());
        detalleCompra.setUnidadMedida(req.getUnidadMedida());
        detalleCompra.setPrecioUnitario(req.getPrecioUnitario());
        detalleCompra.setUnidadesPorPresentacion(
                req.getUnidadesPorPresentacion()
        );
        detalleCompra.setInsumo(insumo);

        if (req.getPrecioUnitario() != null) {
            BigDecimal subtotal = req.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(req.getCantidad()));

            detalleCompra.setSubtotal(subtotal);
        }

        compra.agregarDetalle(detalleCompra);
    }

}
