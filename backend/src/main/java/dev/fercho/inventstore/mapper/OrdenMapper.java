package dev.fercho.inventstore.mapper;


import dev.fercho.inventstore.dto.response.DetalleOrdenResponse;
import dev.fercho.inventstore.dto.response.OrdenResponse;
import dev.fercho.inventstore.model.DetalleOrden;
import dev.fercho.inventstore.model.Orden;
import dev.fercho.inventstore.repository.DetalleOrdenRepository;
import org.hibernate.sql.model.ast.builder.CollectionRowDeleteByUpdateSetNullBuilder;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrdenMapper {


    public OrdenResponse toResponse(Orden orden){
        List<DetalleOrdenResponse> detalles = orden.getDetalles()
                .stream()
                .map(this::detalleOrdenToResponse)
                .toList();
        return new OrdenResponse(
                orden.getId(),
                orden.getEstado(),
                orden.getTipoOrden(),
                orden.getTotal(),
                orden.getFechaCreacion(),
                orden.getUsuario().getId(),
                orden.getUsuario().getNombre(),
                detalles);
    }


    public List<OrdenResponse> toList(List<Orden> ordenes){
        return ordenes.stream()
                .map(this::toResponse)
                .toList();
    }

    public DetalleOrdenResponse detalleOrdenToResponse(DetalleOrden detalleOrden){
        DetalleOrdenResponse response = new DetalleOrdenResponse();
        response.setObservacion(detalleOrden.getObservacion());
        response.setCantidad(detalleOrden.getCantidad());
        response.setSubtotal(detalleOrden.getSubtotal());
        response.setId(detalleOrden.getId());
        response.setProductoId(detalleOrden.getProducto().getId());
        response.setProductoNombre(detalleOrden.getProducto().getNombre());
        response.setPrecioUnitario(detalleOrden.getPrecioUnitario());

        return response;
    }
}
