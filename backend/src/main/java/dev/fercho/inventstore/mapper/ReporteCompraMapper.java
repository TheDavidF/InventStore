package dev.fercho.inventstore.mapper;

import dev.fercho.inventstore.dto.response.ReporteComprasResponse;
import dev.fercho.inventstore.model.Compra;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ReporteCompraMapper {


    public ReporteComprasResponse toResponse(Compra compra) {

        ReporteComprasResponse response = new ReporteComprasResponse();

        response.setCompraid(compra.getId());
        response.setFechaCreacion(compra.getFechaCreacion());
        response.setNombreProveedor(compra.getProveedor().getNombre());
        response.setEstado(compra.getEstado());
        response.setTotal(compra.getTotal());
        response.setFechaPagada(compra.getFechaPagada());

        return response;
    }

    public List<ReporteComprasResponse> toList(List<Compra> compras) {
        return compras.stream()
                .map(this::toResponse)
                .toList();
    }
}
