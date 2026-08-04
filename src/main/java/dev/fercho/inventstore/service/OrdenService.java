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

    private final OrdenRepository ordenRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    private final DetalleOrdenRepository detalleOrdenRepository;

    public OrdenService(ProductoRepository productoRepository,
                        UsuarioRepository usuarioRepository,
                        OrdenRepository ordenRepository,
                        DetalleOrdenRepository detalleOrdenRepository) {
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.ordenRepository = ordenRepository;
        this.detalleOrdenRepository = detalleOrdenRepository;
    }


    @Transactional
    public Orden crearOrden(CrearOrdenRequest request){
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(()-> new RuntimeException("No se encontro un usuario"));

        Orden orden = new Orden();
        orden.setUsuario(usuario);
        orden.setTipoOrden(request.getTipoOrden());

        BigDecimal total = BigDecimal.ZERO;

        for (CrearDetalleOrdenRequest detalleRequest: request.getDetalles()) {
            Producto producto = buscarProductoActivo(detalleRequest.getProductoId());

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


    @Transactional(readOnly = true)
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

    @Transactional
    public Orden agregarDetalle(Long id, CrearDetalleOrdenRequest request){
        Producto producto = buscarProductoActivo(request.getProductoId());
        BigDecimal precioUnitario = producto.getPrecio();
        BigDecimal subtotal = precioUnitario.multiply(
                BigDecimal.valueOf(request.getCantidad())
        );
        Orden orden = buscarPorId(id);
        validarOrdenModificable(orden);
        DetalleOrden detalleOrden = new DetalleOrden();
        detalleOrden.setCantidad(request.getCantidad());
        detalleOrden.setObservacion(request.getObservacion());
        detalleOrden.setPrecioUnitario(precioUnitario);
        detalleOrden.setProducto(producto);
        detalleOrden.setSubtotal(subtotal);
        orden.setTotal(orden.getTotal().add(subtotal));
        orden.agregarDetalle(detalleOrden);
        return ordenRepository.save(orden);
    }


    @Transactional
    public Orden quitarDetalle(Long id, Long detalleId){
        Orden orden = buscarPorId(id);
        validarOrdenModificable(orden);
        DetalleOrden detalleOrden = detalleOrdenRepository.findByIdAndOrdenId(detalleId, id)
                .orElseThrow(()-> new RuntimeException("El detalle no pertenece a la orden"));
        orden.setTotal(orden.getTotal().subtract(detalleOrden.getSubtotal()));
        orden.getDetalles().remove(detalleOrden);
        return ordenRepository.save(orden);
    }

    @Transactional
    public Orden actualizarCantidad(Long id, Long detalleId, CambiarCantidadDetalleRequest request){
        Orden orden = buscarPorId(id);
        validarOrdenModificable(orden);
        DetalleOrden detalleOrden = detalleOrdenRepository.findByIdAndOrdenId(detalleId, id)
                .orElseThrow(()-> new RuntimeException("El detalle no pertenece a la orden"));
        BigDecimal precioUnitario = detalleOrden.getPrecioUnitario();
        BigDecimal nuevoSubtotal = precioUnitario.multiply(BigDecimal.valueOf(request.getCantidad()));
        orden.setTotal(orden.getTotal().subtract(detalleOrden.getSubtotal()));
        detalleOrden.setSubtotal(nuevoSubtotal);
        detalleOrden.setCantidad(request.getCantidad());
        orden.setTotal(orden.getTotal().add(detalleOrden.getSubtotal()));
        return ordenRepository.save(orden);
    }


    @Transactional
    public Orden cancelarOrden(Long id){
        Orden orden = buscarPorId(id);
        if (orden.getEstado() == EstadoOrden.ENTREGADA) {
            throw new RuntimeException(
                    "Una orden entregada no puede cancelarse"
            );
        }
        orden.setEstado(EstadoOrden.CANCELADA);
        return ordenRepository.save(orden);
    }


    private Producto buscarProductoActivo(Long productoId) {

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado")
                );

        if (!producto.isActivo()) {
            throw new RuntimeException(
                    "No se puede agregar un producto inactivo"
            );
        }

        return producto;
    }


    private void validarOrdenModificable(Orden orden) {

        if (orden.getEstado() != EstadoOrden.CREADA && orden.getEstado() != EstadoOrden.PREPARANDO) {
            throw new RuntimeException(
                    "Solo se pueden modificar órdenes en estado CREADA"
            );
        }
    }
}
