package dev.fercho.inventstore.controller;


import dev.fercho.inventstore.dto.request.CrearCategoriaRequest;
import dev.fercho.inventstore.dto.response.CategoriaResponse;
import dev.fercho.inventstore.mapper.CategoriaMapper;
import dev.fercho.inventstore.model.Categoria;
import dev.fercho.inventstore.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private CategoriaService categoriaService;
    private CategoriaMapper categoriaMapper;

    public CategoriaController(CategoriaService categoriaService, CategoriaMapper categoriaMapper){
        this.categoriaService = categoriaService;
        this.categoriaMapper = categoriaMapper;
    }


    @PostMapping
    public CategoriaResponse crearCategoria(@RequestBody @Valid CrearCategoriaRequest request){
        Categoria categoria = categoriaService.crearCategoria(request);
        return categoriaMapper.toResponse(categoria);
    }
}
