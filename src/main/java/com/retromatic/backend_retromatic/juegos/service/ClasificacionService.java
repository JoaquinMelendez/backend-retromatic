package com.retromatic.backend_retromatic.juegos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retromatic.backend_retromatic.juegos.model.Clasificacion;
import com.retromatic.backend_retromatic.juegos.repository.ClasificacionRepository;

@Service
public class ClasificacionService {

    @Autowired
    private ClasificacionRepository clasificacionRepository;

    public List<Clasificacion> getAllClasificaciones(){
        return clasificacionRepository.findAll();
    }

    public Clasificacion getClasificacionById(Long id){
        return clasificacionRepository.findById(id).orElse(null);
    }

    public Clasificacion saveClasificacion(Clasificacion clasificacion){
        return clasificacionRepository.save(clasificacion);
    }

    public void deleteClasificacion(Long id){
        clasificacionRepository.deleteById(id);
    }
}