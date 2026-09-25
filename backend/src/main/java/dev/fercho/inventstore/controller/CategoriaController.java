package dev.fercho.inventstore.controller;


import dev.fercho.inventstore.dto.request.CrearCategoriaRequest;
import dev.fercho.inventstore.dto.response.CategoriaResponse;
import dev.fercho.inventstore.mapper.CategoriaMapper;
import dev.fercho.inventstore.model.Categoria;
import dev.fercho.inventstore.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final CategoriaMapper categoriaMapper;

    public CategoriaController(CategoriaService categoriaService, CategoriaMapper categoriaMapper){
        this.categoriaService = categoriaService;
        this.categoriaMapper = categoriaMapper;
    }


    @GetMapping
    public List<CategoriaResponse> obtenerCategorias(){
        return categoriaMapper.toList(categoriaService.obtenerCategorias());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponse crearCategoria(@RequestBody @Valid CrearCategoriaRequest request){
        Categoria categoria = categoriaService.crearCategoria(request);
        return categoriaMapper.toResponse(categoria);
    }
}
