package dev.fercho.inventstore.service;



import dev.fercho.inventstore.dto.request.AbrirCajaRequest;
import dev.fercho.inventstore.model.Caja;
import dev.fercho.inventstore.model.Usuario;
import dev.fercho.inventstore.model.enums.EstadoCaja;
import dev.fercho.inventstore.repository.CajaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CajaService {

    private final CajaRepository cajaRepository;

    private final UsuarioService usuarioService;

    public CajaService(CajaRepository cajaRepository, UsuarioService usuarioService) {
        this.cajaRepository = cajaRepository;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public Caja abrirCaja(AbrirCajaRequest request){
        Usuario usuario = usuarioService.buscarPorId(request.getUsuarioId());
        if(cajaRepository.existsByEstadoAndUsuarioId(EstadoCaja.ABIERTA, usuario.getId())){
            throw new RuntimeException("El usuario ya tiene una caja abierta");
        }

        Caja caja = new Caja();
        caja.setUsuario(usuario);
        caja.setMontoInicial(request.getMontoInicial());
        return cajaRepository.save(caja);
    }
}
