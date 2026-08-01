package dev.fercho.inventstore.controller;


import dev.fercho.inventstore.dto.request.CrearProductoRequest;
import dev.fercho.inventstore.dto.response.ProductoResponse;
import dev.fercho.inventstore.mapper.ProductoMapper;
import dev.fercho.inventstore.model.Producto;
import dev.fercho.inventstore.service.ProductoService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private ProductoService productoService;
    private ProductoMapper productoMapper;

    public ProductoController(ProductoService productoService, ProductoMapper productoMapper) {
        this.productoService = productoService;
        this.productoMapper = productoMapper;
    }

    @PostMapping
    public ProductoResponse crearProducto(@RequestBody @Valid CrearProductoRequest request){
        Producto producto = productoService.crearProducto(request);
        return productoMapper.toResponse(producto);
    }


    @GetMapping
    public List<ProductoResponse> obtenerProductos(){
        List<Producto> productos = productoService.obtenerProductos();
        return productoMapper.toList(productos);
    }
}
