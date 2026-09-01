package dev.fercho.inventstore.mapper;

import dev.fercho.inventstore.dto.response.InsumoResponse;
import dev.fercho.inventstore.model.Insumo;
import dev.fercho.inventstore.service.InsumoService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InsumoMapper {

    public InsumoResponse toResponse(Insumo insumo){
        InsumoResponse response = new InsumoResponse();
        response.setNombre(insumo.getNombre());
        response.setId(insumo.getId());
        response.setActivo(insumo.isActivo());
        response.setStockActual(insumo.getStockActual());
        response.setStockMinimo(insumo.getStockMinimo());
        return response;
    }

    public List<InsumoResponse> toList(List<Insumo> insumos){
        return insumos.stream()
                .map(this::toResponse)
                .toList();
    }

}
