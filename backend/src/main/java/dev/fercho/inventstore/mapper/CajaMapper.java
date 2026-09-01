package dev.fercho.inventstore.mapper;

import dev.fercho.inventstore.dto.response.CajaResponse;
import dev.fercho.inventstore.model.Caja;
import dev.fercho.inventstore.model.Pago;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class CajaMapper {

    public CajaResponse toResponse(Caja caja) {

        CajaResponse response = new CajaResponse();

        BigDecimal totalPagos = BigDecimal.ZERO;

        if (caja.getPagos() != null) {
            for (Pago pago : caja.getPagos()) {
                totalPagos = totalPagos.add(pago.getMonto());
            }
        }

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

        return response;
    }
}
