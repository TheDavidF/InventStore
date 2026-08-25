package dev.fercho.inventstore.service;

import dev.fercho.inventstore.dto.request.CrearConsumoProducto;
import dev.fercho.inventstore.exception.RecursoNoEncontradoException;
import dev.fercho.inventstore.model.ConsumoProducto;
import dev.fercho.inventstore.model.Insumo;
import dev.fercho.inventstore.model.Producto;
import dev.fercho.inventstore.repository.ConsumoProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConsumoProductoService {

    private final InsumoService insumoService;
    private final ProductoService productoService;
    private final ConsumoProductoRepository consumoProductoRepository;

    public ConsumoProductoService(InsumoService insumoService
            , ProductoService productoService
            , ConsumoProductoRepository consumoProductoRepository) {
        this.insumoService = insumoService;
        this.productoService = productoService;
        this.consumoProductoRepository = consumoProductoRepository;

    }

    @Transactional
    public ConsumoProducto crearConsumo(CrearConsumoProducto request) {

        if (consumoProductoRepository.existsByProductoIdAndInsumoId(
                request.getProductoId(),
                request.getInsumoId())) {

            throw new RuntimeException(
                    "El insumo ya está asociado a este producto"
            );
        }

        Producto producto =
                productoService.buscarPorId(request.getProductoId());

        Insumo insumo =
                insumoService.getById(request.getInsumoId());

        ConsumoProducto consumoProducto = new ConsumoProducto();

        consumoProducto.setCantidad(request.getCantidad());

        producto.agregarConsumo(consumoProducto);
        insumo.agregarConsumo(consumoProducto);

        return consumoProductoRepository.save(consumoProducto);
    }

    public List<ConsumoProducto> obtenerConsumos(){
        return consumoProductoRepository.findAll();
    }



    public ConsumoProducto buscarPorId(Long id){
        return consumoProductoRepository.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("No se encontro ningun consumo"));
    }


    @Transactional
    public ConsumoProducto actualizarCantidad(Long id, Integer nuevaCantidad) {

        if (nuevaCantidad == null || nuevaCantidad <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        ConsumoProducto consumoProducto = buscarPorId(id);
        consumoProducto.setCantidad(nuevaCantidad);

        return consumoProductoRepository.save(consumoProducto);
    }


    @Transactional
    public void eliminarConsumo(Long id) {
        ConsumoProducto consumoProducto = buscarPorId(id);
        consumoProductoRepository.delete(consumoProducto);
    }

    public List<ConsumoProducto> obtenerPorProducto(Long productoId){
        return consumoProductoRepository.findByProductoId(productoId);
    }

}
