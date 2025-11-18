package com.retromatic.backend_retromatic.juegos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retromatic.backend_retromatic.juegos.model.Plataforma;
import com.retromatic.backend_retromatic.juegos.repository.PlataformaRepository;

@Service
public class PlataformaService {

    @Autowired
    private PlataformaRepository plataformaRepository;

    public List<Plataforma> getAllPlataformas(){
        return plataformaRepository.findAll();
    }

    public Plataforma getPlataformaById(Long id){
        return plataformaRepository.findById(id).orElse(null);
    }

    public Plataforma savePlataforma(Plataforma plataforma){
        return plataformaRepository.save(plataforma);
    }

    public void deletePlataforma(Long id){
        plataformaRepository.deleteById(id);
    }
}