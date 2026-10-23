package dev.fercho.inventstore.service;


import dev.fercho.inventstore.dto.request.CrearPagoRequest;
import dev.fercho.inventstore.exception.ConflictoEstadoException;
import dev.fercho.inventstore.exception.PeticionInvalidaException;
import dev.fercho.inventstore.model.Caja;
import dev.fercho.inventstore.model.Orden;
import dev.fercho.inventstore.model.Pago;
import dev.fercho.inventstore.model.Usuario;
import dev.fercho.inventstore.model.enums.EstadoOrden;
import dev.fercho.inventstore.repository.PagoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PagoService {


    private final PagoRepository pagoRepository;

    private final OrdenService ordenService;
    private final CajaService cajaService;

    public PagoService(PagoRepository pagoRepository
            , OrdenService ordenService
            , CajaService cajaService) {
        this.pagoRepository = pagoRepository;
        this.ordenService = ordenService;
        this.cajaService = cajaService;
    }



    @Transactional
    public Pago registrarPago(CrearPagoRequest req){
        Orden orden = ordenService.buscarPorId(req.getOrdenId());
        if(orden.getEstado() == EstadoOrden.CANCELADA){
           throw new ConflictoEstadoException("La orden ya ha sido cancelada y no se preparo");
        }
        if(orden.getPago() != null){
            throw new ConflictoEstadoException("La orden ya fue pagada");
        }
        Usuario usuario = orden.getUsuario();
        Caja caja = cajaService.obtenerCajaAbierta(usuario.getId());
        if(req.getMontoRecibido().compareTo(orden.getTotal()) < 0){
            throw new PeticionInvalidaException("Monto insuficiente");
        }
        Pago pago = new Pago();
        pago.setOrden(orden);
        pago.setCaja(caja);
        pago.setMonto(orden.getTotal());
        orden.setPago(pago);
        return pagoRepository.save(pago);
    }


    public List<Pago> obtenerPagos(){
        return pagoRepository.findAll();
    }
}
