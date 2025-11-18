package com.retromatic.backend_retromatic.usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retromatic.backend_retromatic.usuarios.model.Region;
import com.retromatic.backend_retromatic.usuarios.repository.RegionRepository;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public List<Region> getAllRegiones(){
        return regionRepository.findAll();
    }

    public Region getRegionById(Long id){
        return regionRepository.findById(id).orElse(null);
    }

    public Region saveRegion(Region region){
        return regionRepository.save(region);
    }

    public void deleteRegion(Long id){
        regionRepository.deleteById(id);
    }
}
