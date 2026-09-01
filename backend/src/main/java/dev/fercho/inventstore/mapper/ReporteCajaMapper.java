package dev.fercho.inventstore.mapper;

import dev.fercho.inventstore.dto.response.ReporteCajaResponse;
import dev.fercho.inventstore.model.Caja;
import dev.fercho.inventstore.model.Pago;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
public class ReporteCajaMapper {

    public ReporteCajaResponse toResponse(Caja caja) {

        ReporteCajaResponse response = new ReporteCajaResponse();

        BigDecimal totalPagos = BigDecimal.ZERO;

        if (caja.getPagos() != null) {
            for (Pago pago : caja.getPagos()) {
                totalPagos = totalPagos.add(pago.getMonto());
            }
        }

        response.setCajaId(caja.getId());

        response.setUsuarioId(caja.getUsuario().getId());
        response.setUsuarioNombre(caja.getUsuario().getNombre());

        response.setFechaApertura(caja.getFechaApertura());
        response.setFechaCierre(caja.getFechaCierre());

        response.setMontoInicial(caja.getMontoInicial());
        response.setTotalPagos(totalPagos);
        response.setMontoEsperado(caja.getMontoEsperado());
        response.setMontoFinal(caja.getMontoFinal());

        if (caja.getMontoEsperado() != null
                && caja.getMontoFinal() != null) {

            response.setDiferencia(
                    caja.getMontoFinal()
                            .subtract(caja.getMontoEsperado())
            );
        }

        response.setEstado(caja.getEstado());

        return response;
    }

    public List<ReporteCajaResponse> toList(List<Caja> cajas) {
        return cajas.stream()
                .map(this::toResponse)
                .toList();
    }
}
