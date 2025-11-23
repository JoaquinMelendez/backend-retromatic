package com.retromatic.backend_retromatic.juegos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retromatic.backend_retromatic.juegos.model.Categoria;
import com.retromatic.backend_retromatic.juegos.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("v1/api/categorias")
@Tag(name = "Categorías", description = "Operaciones para gestionar categorías de juegos")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    @Operation(
        summary = "Obtener todas las categorías",
        description = "Retorna una lista con todas las categorías registradas en el sistema."
    )
    public List<Categoria> getAllCategorias(){
        return categoriaService.getAllCategorias();
    }

    @GetMapping("/id")
    @Operation(
        summary = "Buscar categoría por ID",
        description = "Retorna una única categoría según su identificador."
    )
    public Categoria getCategoriaById(@PathVariable Long id){
        return categoriaService.getCategoriaById(id);
    }

    @PostMapping
    @Operation(
        summary = "Crear nueva categoría",
        description = "Permite registrar una nueva categoría en el sistema."
    )
    public Categoria createCategoria(@RequestBody Categoria categoria){
        return categoriaService.saveCategoria(categoria);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar una categoría existente",
        description = "Actualiza los datos de una categoría ya registrada mediante su ID."
    )
    public Categoria updateCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        Categoria categoriaExistente = categoriaService.getCategoriaById(id);
        if (categoriaExistente != null){
            categoriaExistente.setNombre(categoria.getNombre() );
            return categoriaService.saveCategoria(categoriaExistente);
        }
        return null;
    }
}
