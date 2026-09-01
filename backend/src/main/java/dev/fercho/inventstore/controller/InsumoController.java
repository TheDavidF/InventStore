package dev.fercho.inventstore.controller;


import dev.fercho.inventstore.dto.request.CrearInsumoRequest;
import dev.fercho.inventstore.dto.response.InsumoResponse;
import dev.fercho.inventstore.mapper.InsumoMapper;
import dev.fercho.inventstore.model.Insumo;
import dev.fercho.inventstore.service.InsumoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insumos")
public class InsumoController {


    private final InsumoService insumoService;
    private final InsumoMapper insumoMapper;

    public InsumoController(InsumoService insumoService, InsumoMapper insumoMapper) {
        this.insumoService = insumoService;
        this.insumoMapper = insumoMapper;
    }

    @GetMapping
    public List<InsumoResponse> obtenerInsumos(){
        List<Insumo> insumos = insumoService.obtenerInsumos();
        return insumoMapper.toList(insumos);
    }

    @GetMapping("/{id}")
    public InsumoResponse getById(@PathVariable Long id){
        return insumoMapper.toResponse(insumoService.getById(id));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InsumoResponse crearInsumo(@RequestBody@Valid CrearInsumoRequest request){
        return insumoMapper.toResponse(insumoService.crearInsumo(request));
    }

    @GetMapping("/stock-bajo")
    public List<InsumoResponse> obtenerPorStockMinimo(){
        List<Insumo> insumos = insumoService.obtenerPorStockMinimo();
        return insumoMapper.toList(insumos);
    }
}
