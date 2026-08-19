package dev.fercho.inventstore.controller;


import dev.fercho.inventstore.dto.request.AgregarDetalleCompraRequest;
import dev.fercho.inventstore.dto.request.CrearCompraRequest;
import dev.fercho.inventstore.dto.response.CompraResponse;
import dev.fercho.inventstore.mapper.CompraMapper;
import dev.fercho.inventstore.service.CompraService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    private CompraService compraService;
    private CompraMapper compraMapper;

    public CompraController(CompraService compraService, CompraMapper compraMapper) {
        this.compraService = compraService;
        this.compraMapper = compraMapper;
    }


    @PostMapping
    public CompraResponse crearCompra(@RequestBody CrearCompraRequest request){
        return  compraMapper.toResponse(compraService.crearCompra(request));
    }

    @GetMapping
    public List<CompraResponse> obtenerCompras(){
        return compraMapper.toList(compraService.obtenerCompras());
    }

    @PostMapping("/{id}/agregar-detalle")
    public CompraResponse agregarDetalleCompra(@PathVariable Long id, @Valid @RequestBody AgregarDetalleCompraRequest req){
        return compraMapper.toResponse(compraService.agregarDetalleCompra(id,req));

    }
}
