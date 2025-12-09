package com.retromatic.backend_retromatic.juegos;

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

import com.retromatic.backend_retromatic.juegos.model.Categoria;
import com.retromatic.backend_retromatic.juegos.repository.CategoriaRepository;
import com.retromatic.backend_retromatic.juegos.service.CategoriaService;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @InjectMocks
    private CategoriaService categoriaService;

    @Mock
    private CategoriaRepository categoriaRepository;

    private Categoria createCategoria() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Acción");
        return categoria;
    }

    @Test
    void testFindAll() {
        when(categoriaRepository.findAll()).thenReturn(List.of(createCategoria()));
        List<Categoria> categorias = categoriaService.getAllCategorias();

        assertNotNull(categorias);
        assertEquals(1, categorias.size());
        assertEquals("Acción", categorias.get(0).getNombre());
    }

    @Test
    void testFindById() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(createCategoria()));
        Categoria categoria = categoriaService.getCategoriaById(1L);

        assertNotNull(categoria);
        assertEquals("Acción", categoria.getNombre());
    }

    @Test
    void testSave() {
        Categoria categoria = createCategoria();
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        Categoria savedCategoria = categoriaService.saveCategoria(categoria);

        assertNotNull(savedCategoria);
        assertEquals("Acción", savedCategoria.getNombre());
        assertEquals(1L, savedCategoria.getId());
    }

    @Test
    void testDeleteById() {
        doNothing().when(categoriaRepository).deleteById(1L);

        categoriaService.deleteCategoria(1L);

        verify(categoriaRepository, times(1)).deleteById(1L);
    }
}
