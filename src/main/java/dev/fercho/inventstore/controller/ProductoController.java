package dev.fercho.inventstore.controller;


import dev.fercho.inventstore.dto.request.ActualizarProductoRequest;
import dev.fercho.inventstore.dto.request.CrearProductoRequest;
import dev.fercho.inventstore.dto.response.ProductoResponse;
import dev.fercho.inventstore.dto.response.UsuarioResponse;
import dev.fercho.inventstore.mapper.ProductoMapper;
import dev.fercho.inventstore.model.Producto;
import dev.fercho.inventstore.service.ProductoService;
import jakarta.validation.Valid;
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

    @PutMapping("/{id}")
    public ProductoResponse actualizarProducto(@PathVariable Long id, @Valid @RequestBody ActualizarProductoRequest request){
        Producto producto = productoService.actualizarProducto(id,request);
        return productoMapper.toResponse(producto);
    }

    @GetMapping("/{id}")
    public ProductoResponse buscarPorId(@PathVariable Long id){
        Producto producto = productoService.buscarPorId(id);

        return productoMapper.toResponse(producto);
    }

    @PatchMapping("/{id}/desactivar")
    public ProductoResponse desactivarProducto(@PathVariable Long id){
        Producto producto = productoService.desactivarProducto(id);
        return productoMapper.toResponse(producto);
    }

    @PatchMapping("/{id}/reactivar")
    public ProductoResponse reactivarProducto(@PathVariable Long id){
        Producto producto = productoService.reactivarProducto(id);

        return productoMapper.toResponse(producto);
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<ProductoResponse> obtenerPorCategoria(@PathVariable Long categoriaId){
        List<Producto> productos = productoService.obtenerPorCategoria(categoriaId);
        return productoMapper.toList(productos);
    }

    @GetMapping("/buscar")
    public List<ProductoResponse> buscarProducto(@RequestParam String nombre){
        List<Producto> productos = productoService.buscarProducto(nombre);
        return productoMapper.toList(productos);
    }


}
