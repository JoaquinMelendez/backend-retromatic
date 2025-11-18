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


@RestController
@RequestMapping("/api/categorias")
//@Tag(name = "Categoria Management System")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    //@Operation(summary = "Lol")
    public List<Categoria> getAllCategorias(){
        return categoriaService.getAllCategorias();
    }

    @GetMapping("/id")
    //Operation()
    public Categoria getCategoriaById(@PathVariable Long id){
        return categoriaService.getCategoriaById(id);
    }

    @PostMapping
    //Operation()
    public Categoria createCategoria(@RequestBody Categoria categoria){
        return categoriaService.saveCategoria(categoria);
    }

    @PutMapping("/{id}")
    public Categoria updateCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        Categoria categoriaExistente = categoriaService.getCategoriaById(id);
        if (categoriaExistente != null){
            categoriaExistente.setNombre(categoria.getNombre() );
            return categoriaService.saveCategoria(categoriaExistente);
        }
        return null;
    }
}
