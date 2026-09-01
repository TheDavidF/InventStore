package dev.fercho.inventstore.service;


import dev.fercho.inventstore.dto.request.CrearInsumoRequest;
import dev.fercho.inventstore.exception.RecursoNoEncontradoException;
import dev.fercho.inventstore.model.Insumo;
import dev.fercho.inventstore.repository.InsumoRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsumoService {

    private final InsumoRepository insumoRepository;

    public InsumoService(InsumoRepository insumoRepository) {
        this.insumoRepository = insumoRepository;
    }

    public Insumo crearInsumo(CrearInsumoRequest request){
        Insumo insumo = new Insumo();
        insumo.setNombre(request.getNombre());
        insumo.setStockActual(request.getStockInicial());
        insumo.setStockMinimo(request.getStockMinimo());

        return insumoRepository.save(insumo);
    }

    public Insumo getById(Long id){
        return insumoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Insumo no encontrado"));
    }


    public List<Insumo> obtenerInsumos(){
        return insumoRepository.findAll();
    }

    public List<Insumo> obtenerPorStockMinimo(){
        return insumoRepository.findInsumosBajoStockMinimo();
    }
}
