package dev.fercho.inventstore.service;


import dev.fercho.inventstore.dto.request.ActualizarProductoRequest;
import dev.fercho.inventstore.dto.request.CrearProductoRequest;
import dev.fercho.inventstore.model.Categoria;
import dev.fercho.inventstore.model.Producto;
import dev.fercho.inventstore.repository.CategoriaRepository;
import dev.fercho.inventstore.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private ProductoRepository productoRepository;
    private CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Producto crearProducto(CrearProductoRequest request){
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setTipo(request.getTipo());
        producto.setPrecio(request.getPrecio());
        producto.setDescripcion(request.getDescripcion());
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(()-> new RuntimeException("Categoria no encontrada"));
        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }


    public List<Producto> obtenerProductos(){
        return productoRepository.findAll();
    }

    public Producto actualizarProducto(Long id, ActualizarProductoRequest request){
        Producto producto = productoRepository.findById(id)
                .orElseThrow(
                        ()-> new RuntimeException("No se encontro ningun producto"));
        producto.setDescripcion(request.getDescripcion());
        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());

        return productoRepository.save(producto);
    }
}
