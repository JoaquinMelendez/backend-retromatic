package com.retromatic.backend_retromatic.ventas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retromatic.backend_retromatic.ventas.model.MetodoPago;
import com.retromatic.backend_retromatic.ventas.repository.MetodoPagoRepository;

@Service
public class MetodoPagoService {
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    public List<MetodoPago> getAllMetodoPagos(){
        return metodoPagoRepository.findAll();
    }

    public MetodoPago getMetodoPagoById(Long id){
        return metodoPagoRepository.findById(id).orElse(null);
    }

    public MetodoPago saveMetodoPago(MetodoPago metodoPago){
        return metodoPagoRepository.save(metodoPago);
    }

    public void deleteMetodoPago(Long id){
        metodoPagoRepository.deleteById(id);
    }
}
