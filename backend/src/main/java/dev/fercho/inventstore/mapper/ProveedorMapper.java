package dev.fercho.inventstore.mapper;


import dev.fercho.inventstore.dto.response.ProveedorResponse;
import dev.fercho.inventstore.model.Proveedor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProveedorMapper {


    public ProveedorResponse toResponse(Proveedor proveedor){
        ProveedorResponse response = new ProveedorResponse();
        response.setId(proveedor.getId());
        response.setNombre(proveedor.getNombre());
        response.setActivo(proveedor.isActivo());
        response.setTelefono(proveedor.getTelefono());
        response.setNumeroCuenta(proveedor.getNumeroCuenta());
        return response;
    }


    public List<ProveedorResponse> toList(List<Proveedor> proveedores){
        return proveedores.stream()
                .map(this::toResponse)
                .toList();
    }
}
