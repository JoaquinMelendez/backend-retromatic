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

import com.retromatic.backend_retromatic.juegos.model.Compannia;
import com.retromatic.backend_retromatic.juegos.service.CompanniaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/v1/api/compannias")
@Tag(
    name = "Compañías",
    description = "Operaciones para gestionar compañías desarrolladoras"
)
public class CompanniaController {

    @Autowired
    private CompanniaService companniaService;

    @GetMapping
    @Operation(
        summary = "Obtener todas las compañías",
        description = "Retorna una lista con todas las compañías registradas."
    )
    public List<Compannia> getAllCompannias(){
        return companniaService.getAllCompannias();
    }

    @GetMapping("/id")
    @Operation(
        summary = "Obtener compañía por ID",
        description = "Retorna una compañía según su identificador."
    )
    public Compannia getCompanniaById(@PathVariable Long id){
        return companniaService.getCompanniaById(id);
    }

    @PostMapping
    @Operation(
        summary = "Crear una nueva compañía",
        description = "Permite registrar una nueva compañía de videojuegos."
    )
    public Compannia createCompannia(@RequestBody Compannia compannia){
        return companniaService.saveCompannia(compannia);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar compañía existente",
        description = "Actualiza los datos de una compañía existente mediante su ID."
    )
    public Compannia updateCompannia(@PathVariable Long id, @RequestBody Compannia compannia) {
        Compannia companniaExistente = companniaService.getCompanniaById(id);
        if (companniaExistente != null){
            companniaExistente.setNombre(compannia.getNombre() );
            return companniaService.saveCompannia(companniaExistente);
        }
        return null;
    }
}
