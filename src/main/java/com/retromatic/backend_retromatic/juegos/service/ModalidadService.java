package com.retromatic.backend_retromatic.juegos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retromatic.backend_retromatic.juegos.model.Modalidad;
import com.retromatic.backend_retromatic.juegos.repository.ModalidadRepository;

@Service
public class ModalidadService {

    @Autowired
    private ModalidadRepository modalidadRepository;

    public List<Modalidad> getAllModalidades(){
        return modalidadRepository.findAll();
    }

    public Modalidad getModalidadById(Long id){
        return modalidadRepository.findById(id).orElse(null);
    }

    public Modalidad saveModalidad(Modalidad modalidad){
        return modalidadRepository.save(modalidad);
    }

    public void deleteModalidad(Long id){
        modalidadRepository.deleteById(id);
    }
}