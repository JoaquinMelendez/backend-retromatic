package com.retromatic.backend_retromatic.usuarios;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.retromatic.backend_retromatic.usuarios.model.Region;
import com.retromatic.backend_retromatic.usuarios.repository.RegionRepository;
import com.retromatic.backend_retromatic.usuarios.service.RegionService;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {

    @InjectMocks
    private RegionService regionService;

    @Mock
    private RegionRepository regionRepository;

    private Region createRegion() {
        Region region = new Region();
        region.setId(1L);
        region.setNombre("Metropolitana");
        return region;
    }

    @Test
    void testGetAllRegiones() {
        when(regionRepository.findAll()).thenReturn(List.of(createRegion()));
        List<Region> regiones = regionService.getAllRegiones();

        assertNotNull(regiones);
        assertEquals(1, regiones.size());
        assertEquals("Metropolitana", regiones.get(0).getNombre());
    }

    @Test
    void testGetRegionById() {
        when(regionRepository.findById(1L)).thenReturn(Optional.of(createRegion()));
        Region region = regionService.getRegionById(1L);

        assertNotNull(region);
        assertEquals("Metropolitana", region.getNombre());
    }

    @Test
    void testSaveRegion() {
        Region region = createRegion();
        when(regionRepository.save(any(Region.class))).thenReturn(region);

        Region savedRegion = regionService.saveRegion(region);

        assertNotNull(savedRegion);
        assertEquals("Metropolitana", savedRegion.getNombre());
        assertEquals(1L, savedRegion.getId());
    }

    @Test
    void testDeleteRegion() {
        doNothing().when(regionRepository).deleteById(1L);

        regionService.deleteRegion(1L);

        verify(regionRepository, times(1)).deleteById(1L);
    }
}