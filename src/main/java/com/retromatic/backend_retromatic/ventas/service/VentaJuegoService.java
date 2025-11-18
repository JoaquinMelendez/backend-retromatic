package com.retromatic.backend_retromatic.ventas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retromatic.backend_retromatic.ventas.model.VentaJuego;
import com.retromatic.backend_retromatic.ventas.repository.VentaJuegoRepository;

@Service
public class VentaJuegoService {
    @Autowired
    private VentaJuegoRepository ventaJuegoRepository;

    public List<VentaJuego> getAllVentaJuegos(){
        return ventaJuegoRepository.findAll();
    }

    public VentaJuego getVentaJuegoById(Long id){
        return ventaJuegoRepository.findById(id).orElse(null);
    }

    public VentaJuego saveVentaJuego(VentaJuego ventaJuego){
        return ventaJuegoRepository.save(ventaJuego);
    }

    public void deleteVentaJuego(Long id){
        ventaJuegoRepository.deleteById(id);
    }
}
