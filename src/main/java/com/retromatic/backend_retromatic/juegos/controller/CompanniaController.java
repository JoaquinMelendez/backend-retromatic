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


@RestController
@RequestMapping("/api/compannias")
//@Tag(name = "Compannia Management System")
public class CompanniaController {

    @Autowired
    private CompanniaService companniaService;

    @GetMapping
    //@Operation(summary = "Lol")
    public List<Compannia> getAllCompannias(){
        return companniaService.getAllCompannias();
    }

    @GetMapping("/id")
    //Operation()
    public Compannia getCompanniaById(@PathVariable Long id){
        return companniaService.getCompanniaById(id);
    }

    @PostMapping
    //Operation()
    public Compannia createCompannia(@RequestBody Compannia compannia){
        return companniaService.saveCompannia(compannia);
    }

    @PutMapping("/{id}")
    public Compannia updateCompannia(@PathVariable Long id, @RequestBody Compannia compannia) {
        Compannia companniaExistente = companniaService.getCompanniaById(id);
        if (companniaExistente != null){
            companniaExistente.setNombre(compannia.getNombre() );
            return companniaService.saveCompannia(companniaExistente);
        }
        return null;
    }
}
