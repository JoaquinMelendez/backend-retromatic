package com.retromatic.backend_retromatic.juegos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retromatic.backend_retromatic.juegos.model.Juego;
import com.retromatic.backend_retromatic.juegos.repository.JuegoRepository;

@Service
public class JuegoService {
    @Autowired
    private JuegoRepository juegoRepository;

    public List<Juego> getAllJuegos(){
        return juegoRepository.findAll();
    }

    public Juego getJuegoById(Long id){
        return juegoRepository.findById(id).orElse(null);
    }

    public Juego saveJuego(Juego juego){
        return juegoRepository.save(juego);
    }

    public void deleteJuego(Long id){
        juegoRepository.deleteById(id);
    }
}
