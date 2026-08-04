package dev.fercho.inventstore.service;

import dev.fercho.inventstore.dto.request.CambiarCantidadDetalleRequest;
import dev.fercho.inventstore.dto.request.CrearDetalleOrdenRequest;
import dev.fercho.inventstore.dto.request.CrearOrdenRequest;
import dev.fercho.inventstore.model.DetalleOrden;
import dev.fercho.inventstore.model.Orden;
import dev.fercho.inventstore.model.Producto;
import dev.fercho.inventstore.model.Usuario;
import dev.fercho.inventstore.model.enums.EstadoOrden;
import dev.fercho.inventstore.repository.DetalleOrdenRepository;
import dev.fercho.inventstore.repository.OrdenRepository;
import dev.fercho.inventstore.repository.ProductoRepository;
import dev.fercho.inventstore.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.PublicKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.LongFunction;


@Service
public class OrdenService {

    private OrdenRepository ordenRepository;
    private UsuarioRepository usuarioRepository;
    private ProductoRepository productoRepository;

    private DetalleOrdenRepository detalleOrdenRepository;

    public OrdenService(ProductoRepository productoRepository,
                        UsuarioRepository usuarioRepository,
                        OrdenRepository ordenRepository,
                        DetalleOrdenRepository detalleOrdenRepository) {
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.ordenRepository = ordenRepository;
        this.detalleOrdenRepository = detalleOrdenRepository;
    }


    public Orden crearOrden(CrearOrdenRequest request){
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(()-> new RuntimeException("No se encontro un usuario"));

        Orden orden = new Orden();
        orden.setUsuario(usuario);
        orden.setTipoOrden(request.getTipoOrden());

        BigDecimal total = BigDecimal.ZERO;

        for (CrearDetalleOrdenRequest detalleRequest: request.getDetalles()) {
            Producto producto = productoRepository
                    .findById(detalleRequest.getProductoId())
                    .orElseThrow(()-> new RuntimeException("Producto no encontrado"));
            if (!producto.isActivo()) {
                throw new RuntimeException(
                        "No se puede agregar un producto inactivo"
                );
            }

            BigDecimal precioUnitario = producto.getPrecio();
            BigDecimal subtotal = precioUnitario.multiply(
                    BigDecimal.valueOf(detalleRequest.getCantidad())
            );

            DetalleOrden detalle = new DetalleOrden();
            detalle.setProducto(producto);
            detalle.setCantidad(detalleRequest.getCantidad());
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setSubtotal(subtotal);
            detalle.setObservacion(detalleRequest.getObservacion());

            orden.agregarDetalle(detalle);

            total = total.add(subtotal);
        }

        orden.setTotal(total);

        return ordenRepository.save(orden);
    }


    public Orden buscarPorId(Long id){
        return ordenRepository.findById(id)
                .orElseThrow(
                        ()-> new RuntimeException("Orden no encontrada"));
    }


    public List<Orden> buscar(EstadoOrden estado, LocalDate fecha, Long usuarioId){
        if(estado != null){
            return ordenRepository.findByEstado(estado);
        }

        if(fecha != null){
            LocalDateTime inicioDia = fecha.atStartOfDay();
            LocalDateTime finDia = fecha.atTime(LocalTime.MAX);
            return ordenRepository.findByFechaCreacionBetween(inicioDia,finDia);
        }

        if(usuarioId != null){
            Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
            return ordenRepository.findByUsuario(usuario);
        }

        return ordenRepository.findAll();
    }

    public Orden agregarDetalle(Long id, CrearDetalleOrdenRequest request){
        Producto producto = productoRepository
                .findById(request.getProductoId())
                .orElseThrow(()-> new RuntimeException("Producto no encontrado"));
        BigDecimal precioUnitario = producto.getPrecio();
        BigDecimal subtotal = precioUnitario.multiply(
                BigDecimal.valueOf(request.getCantidad())
        );
        Orden orden = buscarPorId(id);
        DetalleOrden detalleOrden = new DetalleOrden();
        detalleOrden.setOrden(orden);
        detalleOrden.setCantidad(request.getCantidad());
        detalleOrden.setObservacion(request.getObservacion());
        detalleOrden.setPrecioUnitario(precioUnitario);
        detalleOrden.setProducto(producto);
        detalleOrden.setSubtotal(subtotal);
        orden.setTotal(orden.getTotal().add(subtotal));
        orden.agregarDetalle(detalleOrden);
        return ordenRepository.save(orden);
    }


    public Orden quitarDetalle(Long id, Long detalleId){
        Orden orden = buscarPorId(id);
        DetalleOrden detalleOrden = detalleOrdenRepository.findById(detalleId)
                .orElseThrow(()-> new RuntimeException("Detalle de orden no encontrado"));
        orden.setTotal(orden.getTotal().subtract(detalleOrden.getSubtotal()));
        orden.getDetalles().remove(detalleOrden);
        return ordenRepository.save(orden);
    }

    @Transactional
    public Orden actualizarCantidad(Long id, Long detalleId, CambiarCantidadDetalleRequest request){
        Orden orden = buscarPorId(id);
        DetalleOrden detalleOrden = detalleOrdenRepository.findById(detalleId)
                .orElseThrow(()-> new RuntimeException("Detalle de orden no encontrado"));
        BigDecimal precioUnitario = detalleOrden.getPrecioUnitario();
        BigDecimal nuevoSubtotal = precioUnitario.multiply(BigDecimal.valueOf(request.getCantidad()));
        orden.setTotal(orden.getTotal().subtract(detalleOrden.getSubtotal()));
        detalleOrden.setSubtotal(nuevoSubtotal);
        detalleOrden.setCantidad(request.getCantidad());
        orden.setTotal(orden.getTotal().add(detalleOrden.getSubtotal()));
        return ordenRepository.save(orden);
    }


    public Orden cancelarOrden(Long id){
        Orden orden = buscarPorId(id);
        orden.setEstado(EstadoOrden.CANCELADA);
        return ordenRepository.save(orden);
    }
}
