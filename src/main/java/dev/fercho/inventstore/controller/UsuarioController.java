package dev.fercho.inventstore.controller;


import dev.fercho.inventstore.dto.request.ActualizarUsuarioRequest;
import dev.fercho.inventstore.dto.request.CrearUsuarioRequest;
import dev.fercho.inventstore.dto.response.UsuarioResponse;
import dev.fercho.inventstore.mapper.UsuarioMapper;
import dev.fercho.inventstore.model.Usuario;
import dev.fercho.inventstore.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    public UsuarioController(UsuarioService usuarioService,
                             UsuarioMapper usuarioMapper){
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @PostMapping
    public UsuarioResponse crearUsuario(@Valid @RequestBody CrearUsuarioRequest request){

        Usuario usuario = usuarioMapper.toEntity(request);

        Usuario usuarioGuardado = usuarioService.crearUsuario(usuario);

        return  usuarioMapper.toResponse(usuarioGuardado);
    }

    @GetMapping
    public List<UsuarioResponse> listarUsuariosActivos(){
        List<Usuario> usuariosActivos =
                usuarioService.listarUsuariosActivos();
        return usuarioMapper.toResponseList(usuariosActivos);
    }


    @GetMapping("/inactivos")
    public List<UsuarioResponse> listarUsuariosInactivos(){
        List<Usuario> usuariosInactivos = usuarioService.listarUsuariosInactivos();
        return usuarioMapper.toResponseList(usuariosInactivos);
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscarPorId(@PathVariable Long id){
        Usuario usuario = usuarioService.buscarPorId(id);

        return usuarioMapper.toResponse(usuario);
    }


    @PutMapping("/{id}")
    public UsuarioResponse actualizarUsuario(@PathVariable Long id,@Valid @RequestBody ActualizarUsuarioRequest request){
        Usuario usuario = usuarioService.actualizarUsuario(id,request);
        return usuarioMapper.toResponse(usuario);
    }


    @PatchMapping("/{id}/desactivar")
    public UsuarioResponse desactivarUsuario(@PathVariable Long id){
        Usuario usuario = usuarioService.desactivarUsuario(id);
        return usuarioMapper.toResponse(usuario);
    }

    @PatchMapping("/{id}/activar")
    public UsuarioResponse activarUsuario(@PathVariable Long id){
        Usuario usuario = usuarioService.activarUsuario(id);
        return usuarioMapper.toResponse(usuario);
    }
}
