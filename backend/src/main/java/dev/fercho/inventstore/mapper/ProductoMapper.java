package dev.fercho.inventstore.mapper;


import dev.fercho.inventstore.dto.request.CrearProductoRequest;
import dev.fercho.inventstore.dto.response.ProductoResponse;
import dev.fercho.inventstore.model.Producto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductoMapper {

    public Producto toEntity(CrearProductoRequest request){
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setTipo(request.getTipo());
        producto.setPrecio(request.getPrecio());
        producto.setDescripcion(request.getDescripcion());
        return producto;
    }


    public List<ProductoResponse> toList(List<Producto> productos){
        return productos.stream()
                        .map(this::toResponse)
                        .toList();
    }
    public ProductoResponse toResponse(Producto producto){
        ProductoResponse productoResponse = new ProductoResponse();
        productoResponse.setId(producto.getId());
        productoResponse.setCategoriaId(producto.getCategoria().getId());
        productoResponse.setCategoriaNombre(producto.getCategoria().getNombre());
        productoResponse.setDescripcion(producto.getDescripcion());
        productoResponse.setTipo(producto.getTipo());
        productoResponse.setNombre(producto.getNombre());
        productoResponse.setPrecio(producto.getPrecio());
        productoResponse.setActivo(producto.isActivo());
        return productoResponse;
    }



}
