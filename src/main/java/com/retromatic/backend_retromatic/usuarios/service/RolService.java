package com.retromatic.backend_retromatic.usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retromatic.backend_retromatic.usuarios.model.Rol;
import com.retromatic.backend_retromatic.usuarios.repository.RolRepository;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    public List<Rol> getAllRoles(){
        return rolRepository.findAll();
    }

    public Rol getRolById(Long id){
        return rolRepository.findById(id).orElse(null);
    }

    public Rol saveRol(Rol rol){
        return rolRepository.save(rol);
    }

    public void deleteRol(Long id){
        rolRepository.deleteById(id);
    }
}
