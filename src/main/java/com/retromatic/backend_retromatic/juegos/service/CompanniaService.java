package com.retromatic.backend_retromatic.juegos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retromatic.backend_retromatic.juegos.model.Compannia;
import com.retromatic.backend_retromatic.juegos.repository.CompanniaRepository;

@Service
public class CompanniaService {

    @Autowired
    private CompanniaRepository companniaRepository;

    public List<Compannia> getAllCompannias(){
        return companniaRepository.findAll();
    }

    public Compannia getCompanniaById(Long id){
        return companniaRepository.findById(id).orElse(null);
    }

    public Compannia saveCompannia(Compannia compannia){
        return companniaRepository.save(compannia);
    }

    public void deleteCompannia(Long id){
        companniaRepository.deleteById(id);
    }
}