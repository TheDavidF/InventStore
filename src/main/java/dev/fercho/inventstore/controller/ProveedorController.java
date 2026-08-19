package dev.fercho.inventstore.controller;


import dev.fercho.inventstore.dto.request.CrearProveedorRequest;
import dev.fercho.inventstore.dto.response.ProveedorResponse;
import dev.fercho.inventstore.mapper.ProveedorMapper;
import dev.fercho.inventstore.service.ProveedorService;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorMapper proveedorMapper;
    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorMapper proveedorMapper, ProveedorService proveedorService) {
        this.proveedorMapper = proveedorMapper;
        this.proveedorService = proveedorService;
    }


    @PostMapping
    public ProveedorResponse crearProveedor(@RequestBody CrearProveedorRequest request){
        return proveedorMapper.toResponse(proveedorService.crearProveedor(request));
    }

    @GetMapping
    public List<ProveedorResponse> obtenerProveedores(){
        return proveedorMapper.toList(proveedorService.obtenerProveedores());
    }
}
