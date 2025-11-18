package com.retromatic.backend_retromatic.ventas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retromatic.backend_retromatic.ventas.model.Estado;
import com.retromatic.backend_retromatic.ventas.repository.EstadoRepository;

@Service
public class EstadoService {
    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> getAllEstados(){
        return estadoRepository.findAll();
    }

    public Estado getEstadoById(Long id){
        return estadoRepository.findById(id).orElse(null);
    }

    public Estado saveEstado(Estado estado){
        return estadoRepository.save(estado);
    }

    public void deleteEstado(Long id){
        estadoRepository.deleteById(id);
    }
}
