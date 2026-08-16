package dev.fercho.inventstore.controller;


import dev.fercho.inventstore.dto.request.AjustarInventarioRequest;
import dev.fercho.inventstore.dto.request.CrearMovimientoInventarioRequest;
import dev.fercho.inventstore.dto.response.AjusteInventarioResponse;
import dev.fercho.inventstore.dto.response.MovimientoInventarioResponse;
import dev.fercho.inventstore.mapper.MovimientoInventarioMapper;
import dev.fercho.inventstore.model.MovimientoInventario;
import dev.fercho.inventstore.service.MovimientoInventarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class MovientoInventarioController {

    private final MovimientoInventarioService movimientoInventarioService;
    private final MovimientoInventarioMapper movimientoInventarioMapper;

    public MovientoInventarioController(MovimientoInventarioService movimientoInventarioService
            , MovimientoInventarioMapper movimientoInventarioMapper) {
        this.movimientoInventarioService = movimientoInventarioService;
        this.movimientoInventarioMapper = movimientoInventarioMapper;
    }


    @GetMapping
    public List<MovimientoInventarioResponse> obtenerMovimientos(){
        List<MovimientoInventario> movimientos = movimientoInventarioService.obtenerMovimientos();

        return movimientoInventarioMapper.toList(movimientos);
    }

    @PostMapping("/entradas")
    public MovimientoInventarioResponse registrarEntrada(
            @Valid @RequestBody CrearMovimientoInventarioRequest request) {

        MovimientoInventario movimiento =
                movimientoInventarioService.registrarEntrada(request);

        return movimientoInventarioMapper.toResponse(movimiento);
    }

    @PostMapping("/mermas")
    public MovimientoInventarioResponse registrarMerma(
            @Valid @RequestBody CrearMovimientoInventarioRequest request) {

        MovimientoInventario movimiento =
                movimientoInventarioService.registrarMerma(request);

        return movimientoInventarioMapper.toResponse(movimiento);
    }



    @PostMapping("/ajustes")
    public AjusteInventarioResponse registrarAjuste(@Valid @RequestBody AjustarInventarioRequest request){
        MovimientoInventario ajuste = movimientoInventarioService.ajustarInventario(request);

        return movimientoInventarioMapper.toAjusteResponse(ajuste);
    }


}
