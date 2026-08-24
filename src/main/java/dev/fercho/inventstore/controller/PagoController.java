package dev.fercho.inventstore.controller;


import dev.fercho.inventstore.dto.request.CrearPagoRequest;
import dev.fercho.inventstore.dto.response.PagoResponse;
import dev.fercho.inventstore.mapper.PagoMapper;
import dev.fercho.inventstore.model.Pago;
import dev.fercho.inventstore.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {


    private final PagoService pagoService;
    private final PagoMapper pagoMapper;

    public PagoController(PagoService pagoService, PagoMapper pagoMapper) {
        this.pagoService = pagoService;
        this.pagoMapper = pagoMapper;
    }

    @PostMapping
    public PagoResponse registrarPago(@RequestBody @Valid CrearPagoRequest req){
        //quitar la logica del controller en un futuro
        Pago pago =pagoService.registrarPago(req);
        BigDecimal vuelto = req.getMontoRecibido()
                .subtract(pago.getMonto());
        return pagoMapper.toResponse(pago,req.getMontoRecibido(),vuelto);
    }

    @GetMapping
    public List<PagoResponse> obtenerPagos(){
        return pagoMapper.toList(pagoService.obtenerPagos());
    }
}
