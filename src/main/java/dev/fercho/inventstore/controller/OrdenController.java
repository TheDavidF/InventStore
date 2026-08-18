package dev.fercho.inventstore.controller;


import dev.fercho.inventstore.dto.request.CambiarCantidadDetalleRequest;
import dev.fercho.inventstore.dto.request.CrearDetalleOrdenRequest;
import dev.fercho.inventstore.dto.request.CrearOrdenRequest;
import dev.fercho.inventstore.dto.response.OrdenResponse;
import dev.fercho.inventstore.mapper.OrdenMapper;
import dev.fercho.inventstore.model.Orden;
import dev.fercho.inventstore.model.enums.EstadoOrden;
import dev.fercho.inventstore.service.OrdenService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController()
@RequestMapping("/api/ordenes")
public class OrdenController {

    private final OrdenService ordenService;

    private final OrdenMapper ordenMapper;

    public OrdenController(OrdenService ordenService, OrdenMapper ordenMapper) {
        this.ordenService = ordenService;
        this.ordenMapper = ordenMapper;
    }

    @PostMapping
    public OrdenResponse crearOrden(@Valid@RequestBody CrearOrdenRequest request){
        Orden orden = ordenService.crearOrden(request);

        return ordenMapper.toResponse(orden);
    }

    @GetMapping("/{id}")
    public OrdenResponse buscarPorId(@PathVariable Long id){
        Orden orden = ordenService.buscarPorId(id);
        return ordenMapper.toResponse(orden);
    }

    @GetMapping
    public List<OrdenResponse> buscar(@RequestParam(required = false) EstadoOrden estado,
                                               @RequestParam (required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                                @RequestParam (required = false) Long usuarioId){
        List<Orden> ordenes = ordenService.buscar(estado, fecha, usuarioId);

        return ordenMapper.toList(ordenes);
    }

    @PatchMapping("/{id}/agregar-detalle")
    public OrdenResponse agregarDetalle(@PathVariable Long id, @Valid @RequestBody CrearDetalleOrdenRequest request){
        Orden orden = ordenService.agregarDetalle(id, request);
        return ordenMapper.toResponse(orden);
    }

    @PatchMapping("/{id}/quitar-detalle/{detalleId}")
    public OrdenResponse quitarDetalle(@PathVariable Long id, @PathVariable Long detalleId){
        Orden orden = ordenService.quitarDetalle(id,detalleId);

        return ordenMapper.toResponse(orden);
    }


    @PatchMapping("/{id}/actualizar-cantidad/{detalleId}")
    public OrdenResponse actualizarCantidad(@PathVariable Long id, @PathVariable Long detalleId, @Valid @RequestBody CambiarCantidadDetalleRequest request){
        Orden orden = ordenService.actualizarCantidad(id, detalleId,request);
        return ordenMapper.toResponse(orden);
    }

    @PatchMapping("/{id}/cancelar")
    public OrdenResponse cancelarOrden(@PathVariable Long id){
        Orden orden = ordenService.cancelarOrden(id);

        return ordenMapper.toResponse(orden);

    }

    @PatchMapping("/{id}/preparar")
    public OrdenResponse prepararOrden(@PathVariable Long id) {
        Orden orden = ordenService.prepararOrden(id);
        return ordenMapper.toResponse(orden);
    }

    @PatchMapping("/{id}/completar")
    public OrdenResponse completarOrden(@PathVariable Long id) {

        Orden orden = ordenService.completarOrden(id);

        return ordenMapper.toResponse(orden);
    }

    @PatchMapping("/{id}/entregar")
    public OrdenResponse entregarOrden(@PathVariable Long id) {

        Orden orden = ordenService.entregarOrden(id);

        return ordenMapper.toResponse(orden);
    }
}
