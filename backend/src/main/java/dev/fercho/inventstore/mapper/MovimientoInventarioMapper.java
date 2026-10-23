package dev.fercho.inventstore.mapper;


import dev.fercho.inventstore.dto.response.AjusteInventarioResponse;
import dev.fercho.inventstore.dto.response.MovimientoInventarioResponse;
import dev.fercho.inventstore.model.MovimientoInventario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovimientoInventarioMapper {

    public MovimientoInventarioResponse toResponse(MovimientoInventario entrada){
        MovimientoInventarioResponse entradaResponse = new MovimientoInventarioResponse();
        entradaResponse.setCantidad(entrada.getCantidad());
        entradaResponse.setId(entrada.getId());
        entradaResponse.setFecha(entrada.getFecha());
        entradaResponse.setMotivo(entrada.getMotivo());
        entradaResponse.setInsumoId(entrada.getInsumo().getId());
        entradaResponse.setTipo(entrada.getTipo());
        entradaResponse.setNombreInsumo(entrada.getInsumo().getNombre());
        return entradaResponse;

    }

    public AjusteInventarioResponse toAjusteResponse(MovimientoInventario entrada){
        AjusteInventarioResponse ajusteInventarioResponse = new AjusteInventarioResponse();
        ajusteInventarioResponse.setDiferencia(Math.abs(entrada.getStockAnterior()- entrada.getStockPosterior()));
        ajusteInventarioResponse.setMotivo(entrada.getMotivo());
        ajusteInventarioResponse.setInsumoId(entrada.getInsumo().getId());
        ajusteInventarioResponse.setInsumoNombre(entrada.getInsumo().getNombre());
        ajusteInventarioResponse.setNuevoStock(entrada.getStockPosterior());
        ajusteInventarioResponse.setStockAnterior(entrada.getStockAnterior());
        return ajusteInventarioResponse;
    }


    public List<MovimientoInventarioResponse> toList(List<MovimientoInventario> entradas){
        return entradas.stream()
                .map(this::toResponse)
                .toList();
    }
}
