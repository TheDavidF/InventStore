package dev.fercho.inventstore.mapper;


import dev.fercho.inventstore.dto.request.AgregarDetalleCompraRequest;
import dev.fercho.inventstore.dto.response.CompraResponse;
import dev.fercho.inventstore.dto.response.DetalleCompraResponse;
import dev.fercho.inventstore.model.Compra;
import dev.fercho.inventstore.model.DetalleCompra;
import dev.fercho.inventstore.model.Insumo;
import dev.fercho.inventstore.repository.InsumoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompraMapper {


    public CompraResponse toResponse(Compra compra){
        CompraResponse response = new CompraResponse();
        response.setId(compra.getId());
        response.setEstado(compra.getEstado());
        response.setTotal(compra.getTotal());
        response.setFechaPagada(compra.getFechaPagada());
        response.setFechaCreacion(compra.getFechaCreacion());
        response.setProveedorId(compra.getProveedor().getId());
        response.setNombreProveedor(compra.getProveedor().getNombre());

        List<DetalleCompraResponse> detalles = compra.getDetallesCompra()
                .stream()
                .map(this::detalleToResponse)
                .toList();

        response.setDetallesCompra(detalles);
        return response;

    }

    public List<CompraResponse> toList(List<Compra> compras){
        return compras.stream()
                .map(this::toResponse)
                .toList();
    }

    private DetalleCompraResponse detalleToResponse(
            DetalleCompra detalle) {

        DetalleCompraResponse response = new DetalleCompraResponse();

        response.setId(detalle.getId());
        response.setInsumoId(detalle.getInsumo().getId());
        response.setInsumoNombre(detalle.getInsumo().getNombre());
        response.setCantidad(detalle.getCantidad());
        response.setUnidadMedida(detalle.getUnidadMedida());
        response.setPrecioUnitario(detalle.getPrecioUnitario());
        response.setSubtotal(detalle.getSubtotal());
        response.setObservacion(detalle.getObservacion());
        response.setUnidadesPorPresentacion(
                detalle.getUnidadesPorPresentacion()
        );
        return response;
    }


}
