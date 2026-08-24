package dev.fercho.inventstore.controller;


import dev.fercho.inventstore.dto.request.AbrirCajaRequest;
import dev.fercho.inventstore.dto.request.CerrarCajaRequest;
import dev.fercho.inventstore.dto.response.CajaResponse;
import dev.fercho.inventstore.mapper.CajaMapper;
import dev.fercho.inventstore.service.CajaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cajas")
public class CajaController {


    private final CajaMapper cajaMapper;
    private final CajaService cajaService;

    public CajaController(CajaMapper cajaMapper, CajaService cajaService) {
        this.cajaMapper = cajaMapper;
        this.cajaService = cajaService;
    }

    @PostMapping
    public CajaResponse abrirCaja(@Valid @RequestBody  AbrirCajaRequest req){
        return cajaMapper.toResponse(cajaService.abrirCaja(req));
    }

    @PatchMapping("/{id}/cerrar")
    public CajaResponse cerrarCaja( @PathVariable Long id,
                                    @Valid @RequestBody  CerrarCajaRequest request){
        return cajaMapper.toResponse(cajaService.cerrarCaja(request,id));
    }

    @GetMapping("/{id}")
    public CajaResponse obtenerCaja(@PathVariable Long id){
        return cajaMapper.toResponse(cajaService.obtenerPorId(id));
    }
}
