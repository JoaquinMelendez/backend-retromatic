package com.retromatic.backend_retromatic.usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retromatic.backend_retromatic.usuarios.model.Comuna;
import com.retromatic.backend_retromatic.usuarios.repository.ComunaRepository;

@Service
public class ComunaService {
    @Autowired
    private ComunaRepository comunaRepository;

    public List<Comuna> getAllComunas(){
        return comunaRepository.findAll();
    }

    public Comuna getComunaById(Long id){
        return comunaRepository.findById(id).orElse(null);
    }

    public Comuna saveComuna(Comuna comuna){
        return comunaRepository.save(comuna);
    }

    public void deleteComuna(Long id){
        comunaRepository.deleteById(id);
    }
}
