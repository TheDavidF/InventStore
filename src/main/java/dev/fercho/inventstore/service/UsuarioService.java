package dev.fercho.inventstore.service;




import dev.fercho.inventstore.dto.request.ActualizarUsuarioRequest;
import dev.fercho.inventstore.exception.CorreoDuplicadoException;
import dev.fercho.inventstore.model.Producto;
import dev.fercho.inventstore.model.Usuario;
import dev.fercho.inventstore.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }


    public Usuario actualizarUsuario(Long id, ActualizarUsuarioRequest request){
        Usuario usuario =buscarPorId(id);
        usuario.setNombre(request.getNombre());
        usuario.setRol(request.getRol());
        usuario.setCorreo(request.getCorreo());

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(()->
                        new RuntimeException("Usuario no encontrado"));

    }

    public Usuario crearUsuario(Usuario usuario){

        if (usuarioRepository.existsByCorreo(usuario.getCorreo())){
            throw new CorreoDuplicadoException(usuario.getCorreo());
        }

        String contrasenaProtegida =
                passwordEncoder.encode(usuario.getContrasena());

        usuario.setContrasena(contrasenaProtegida);
        usuario.setActivo(true);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuariosActivos(){
        return usuarioRepository.findByActivoTrue();
    }


    public List<Usuario> listarUsuariosInactivos() {
        return usuarioRepository.findByActivoFalse();
    }

    public Usuario desactivarUsuario(Long id){
        Usuario usuario = buscarPorId(id);
        usuario.setActivo(false);
        return usuarioRepository.save(usuario);
    }


    public Usuario activarUsuario(Long id){
        Usuario usuario = buscarPorId(id);
        usuario.setActivo(true);
        return usuarioRepository.save(usuario);
    }



}
