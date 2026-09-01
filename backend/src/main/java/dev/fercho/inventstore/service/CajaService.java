package dev.fercho.inventstore.service;



import dev.fercho.inventstore.dto.request.AbrirCajaRequest;
import dev.fercho.inventstore.dto.request.CerrarCajaRequest;
import dev.fercho.inventstore.exception.ConflictoEstadoException;
import dev.fercho.inventstore.exception.RecursoNoEncontradoException;
import dev.fercho.inventstore.model.Caja;
import dev.fercho.inventstore.model.Pago;
import dev.fercho.inventstore.model.Usuario;
import dev.fercho.inventstore.model.enums.EstadoCaja;
import dev.fercho.inventstore.repository.CajaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CajaService {

    private final CajaRepository cajaRepository;

    private final UsuarioService usuarioService;

    public CajaService(CajaRepository cajaRepository, UsuarioService usuarioService) {
        this.cajaRepository = cajaRepository;
        this.usuarioService = usuarioService;
    }

    @Transactional(readOnly = true)
    public Caja obtenerCajaAbierta (Long usuarioId){
         return cajaRepository
                 .findByEstadoAndUsuarioId(EstadoCaja.ABIERTA, usuarioId)
                .orElseThrow(()->
                        new RecursoNoEncontradoException("La caja no existe")
                );

    }

    @Transactional
    public Caja abrirCaja(AbrirCajaRequest request){
        Usuario usuario = usuarioService.buscarPorId(request.getUsuarioId());
        if(cajaRepository.existsByEstadoAndUsuarioId(EstadoCaja.ABIERTA, usuario.getId())){
            throw new ConflictoEstadoException("El usuario ya tiene una caja abierta");
        }

        Caja caja = new Caja();
        caja.setUsuario(usuario);
        caja.setMontoInicial(request.getMontoInicial());
        return cajaRepository.save(caja);
    }


    public Caja obtenerPorId(Long cajaId){
        return cajaRepository.findById(cajaId)
                .orElseThrow(()->
                        new RecursoNoEncontradoException("Caja no encontrada"));
    }


    @Transactional
    public Caja cerrarCaja(CerrarCajaRequest req, Long cajaId){
        Caja caja = obtenerPorId(cajaId);
        if(caja.getEstado() == EstadoCaja.CERRADA){
            throw new ConflictoEstadoException("La caja ya ha sido cerrada");
        }
        BigDecimal montoEsperado = BigDecimal.ZERO;
        for (Pago pago:
                caja.getPagos()) {
            montoEsperado = montoEsperado.add(pago.getMonto());
        }
        montoEsperado = montoEsperado.add(caja.getMontoInicial());
        caja.setMontoEsperado(montoEsperado);
        caja.setMontoFinal(req.getMontoFinal());
        caja.setFechaCierre(LocalDateTime.now());
        caja.setEstado(EstadoCaja.CERRADA);
        return cajaRepository.save(caja);
    }
}
