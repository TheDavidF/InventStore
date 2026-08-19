package dev.fercho.inventstore.service;

import dev.fercho.inventstore.dto.request.ActualizarProveedorRequest;
import dev.fercho.inventstore.dto.request.CrearProveedorRequest;
import dev.fercho.inventstore.model.Proveedor;
import dev.fercho.inventstore.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {


    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public Proveedor crearProveedor(CrearProveedorRequest request){
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(request.getNombre());
        proveedor.setNumeroCuenta(request.getNumeroCuenta());
        proveedor.setTelefono(request.getTelefono());

        return proveedorRepository.save(proveedor);
    }

    public Proveedor buscarPorId(Long id){
        return proveedorRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No se encontro el proveedor"));
    }


    public Proveedor actualizarProveedor(Long id, ActualizarProveedorRequest request){
        Proveedor proveedor = buscarPorId(id);
        proveedor.setTelefono(request.getTelefono());
        proveedor.setNombre(request.getNombre());
        proveedor.setNumeroCuenta(request.getNumeroCuenta());

        return proveedorRepository.save(proveedor);
    }

    public List<Proveedor> obtenerProveedores(){
        return proveedorRepository.findAll();
    }
}
