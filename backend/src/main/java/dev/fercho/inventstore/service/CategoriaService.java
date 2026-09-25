package dev.fercho.inventstore.service;

import dev.fercho.inventstore.dto.request.CrearCategoriaRequest;
import dev.fercho.inventstore.model.Categoria;
import dev.fercho.inventstore.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService {

    CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria crearCategoria(CrearCategoriaRequest request){
        Categoria categoria = new Categoria();
        categoria.setNombre(request.getNombre());
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> obtenerCategorias(){
        return categoriaRepository.findAll();
    }
}
