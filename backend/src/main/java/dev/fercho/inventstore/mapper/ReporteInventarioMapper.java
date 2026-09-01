package dev.fercho.inventstore.mapper;

import dev.fercho.inventstore.dto.response.ReporteInventarioResponse;
import dev.fercho.inventstore.model.Insumo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReporteInventarioMapper {

    public ReporteInventarioResponse toResponse(Insumo insumo) {

        ReporteInventarioResponse response = new ReporteInventarioResponse();

        response.setInsumoId(insumo.getId());
        response.setNombre(insumo.getNombre());
        response.setStockActual(insumo.getStockActual());
        response.setStockMinimo(insumo.getStockMinimo());

        response.setBajoStock(
                insumo.getStockActual() <= insumo.getStockMinimo()
        );

        return response;
    }

    public List<ReporteInventarioResponse> toList(List<Insumo> insumos) {
        return insumos.stream()
                .map(this::toResponse)
                .toList();
    }
}