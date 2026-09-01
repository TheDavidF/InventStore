package dev.fercho.inventstore.mapper;


import dev.fercho.inventstore.dto.request.CrearUsuarioRequest;
import dev.fercho.inventstore.dto.response.UsuarioResponse;
import dev.fercho.inventstore.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioMapper {

    public Usuario toEntity(CrearUsuarioRequest request){
        Usuario usuario = new Usuario();

        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());
        usuario.setContrasena(request.getContrasena());
        usuario.setRol(request.getRol());

        return usuario;
    }

    public UsuarioResponse toResponse(Usuario usuario){

        UsuarioResponse response = new UsuarioResponse();

        response.setId(usuario.getId());
        response.setCorreo(usuario.getCorreo());
        response.setNombre(usuario.getNombre());
        response.setRol(usuario.getRol());
        response.setActivo(usuario.getActivo());

        return response;

    }

    public List<UsuarioResponse> toResponseList(List<Usuario> usuarios){
        return usuarios.stream()
                .map(this::toResponse)
                .toList();
    }
}
