package dev.fercho.inventstore.mapper;

import dev.fercho.inventstore.dto.request.CrearCategoriaRequest;
import dev.fercho.inventstore.dto.request.CrearUsuarioRequest;
import dev.fercho.inventstore.dto.response.CategoriaResponse;
import dev.fercho.inventstore.model.Categoria;
import org.springframework.stereotype.Component;


@Component
public class CategoriaMapper {


    public Categoria toEntity(CrearCategoriaRequest request){
        Categoria categoria = new Categoria();
        categoria.setNombre(request.getNombre());
        return categoria;
    }


    public CategoriaResponse toResponse(Categoria categoria){
        CategoriaResponse categoriaResponse = new CategoriaResponse();
        categoriaResponse.setId(categoria.getId());
        categoriaResponse.setActiva(categoria.isActiva());
        categoriaResponse.setNombre(categoria.getNombre());
        return categoriaResponse;
    }
}
