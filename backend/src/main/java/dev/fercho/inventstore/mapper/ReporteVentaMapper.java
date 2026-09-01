package dev.fercho.inventstore.mapper;

import dev.fercho.inventstore.dto.response.ReporteVentaResponse;
import dev.fercho.inventstore.model.Orden;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ReporteVentaMapper {

    public ReporteVentaResponse toResponse(Orden orden) {

        ReporteVentaResponse response = new ReporteVentaResponse();

        response.setOrdenId(orden.getId());
        response.setFecha(orden.getFechaCreacion());
        response.setTotal(orden.getTotal());
        response.setTipoOrden(orden.getTipoOrden());
        response.setEstado(orden.getEstado());

        response.setUsuarioId(orden.getUsuario().getId());
        response.setUsuarioNombre(orden.getUsuario().getNombre());

        return response;
    }

    public List<ReporteVentaResponse> toList(List<Orden> ordenes) {
        return ordenes.stream()
                .map(this::toResponse)
                .toList();
    }
}
