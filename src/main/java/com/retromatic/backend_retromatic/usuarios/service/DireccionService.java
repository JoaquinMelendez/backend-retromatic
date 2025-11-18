package com.retromatic.backend_retromatic.usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retromatic.backend_retromatic.usuarios.model.Direccion;
import com.retromatic.backend_retromatic.usuarios.repository.DireccionRepository;

@Service
public class DireccionService {
    @Autowired
    private DireccionRepository direccionRepository;

    public List<Direccion> getAllDirecciones(){
        return direccionRepository.findAll();
    }

    public Direccion getDireccionById(Long id){
        return direccionRepository.findById(id).orElse(null);
    }

    public Direccion saveDireccion(Direccion direccion){
        return direccionRepository.save(direccion);
    }

    public void deleteDireccion(Long id){
        direccionRepository.deleteById(id);
    }
}
