package dev.fercho.inventstore.mapper;

import dev.fercho.inventstore.dto.response.PagoResponse;
import dev.fercho.inventstore.model.Pago;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PagoMapper {

    public PagoResponse toResponse(Pago pago, BigDecimal montoRecibido, BigDecimal vuelto){
        PagoResponse response = new PagoResponse();
        response.setPagoId(pago.getId());
        response.setVuelto(vuelto);
        response.setMontoPagado(pago.getMonto());
        response.setOrdenId(pago.getOrden().getId());
        response.setMontoRecibido(montoRecibido);
        response.setFecha(pago.getFecha());
        return response;
    }

    public PagoResponse toResponse(Pago pago){
        PagoResponse response = new PagoResponse();
        response.setPagoId(pago.getId());
        response.setMontoPagado(pago.getMonto());
        response.setOrdenId(pago.getOrden().getId());
        response.setFecha(pago.getFecha());
        return response;
    }

    public List<PagoResponse> toList(List<Pago> pagos){
        return pagos.stream()
                .map(this::toResponse)
                .toList();
    }
}
