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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.retromatic.backend_retromatic.juegos.model.Categoria;
import com.retromatic.backend_retromatic.juegos.model.Clasificacion;
import com.retromatic.backend_retromatic.juegos.model.Compannia;
import com.retromatic.backend_retromatic.juegos.model.Juego;
import com.retromatic.backend_retromatic.juegos.model.JuegoCategoria;
import com.retromatic.backend_retromatic.juegos.model.JuegoRequest;
import com.retromatic.backend_retromatic.juegos.model.Modalidad;
import com.retromatic.backend_retromatic.juegos.model.Plataforma;
import com.retromatic.backend_retromatic.juegos.repository.CategoriaRepository;
import com.retromatic.backend_retromatic.juegos.repository.ClasificacionRepository;
import com.retromatic.backend_retromatic.juegos.repository.CompanniaRepository;
import com.retromatic.backend_retromatic.juegos.repository.JuegoCategoriaRepository;
import com.retromatic.backend_retromatic.juegos.repository.JuegoCompanniaRepository;
import com.retromatic.backend_retromatic.juegos.repository.JuegoModalidadRepository;
import com.retromatic.backend_retromatic.juegos.repository.JuegoPlataformaRepository;
import com.retromatic.backend_retromatic.juegos.repository.JuegoRepository;
import com.retromatic.backend_retromatic.juegos.repository.ModalidadRepository;
import com.retromatic.backend_retromatic.juegos.repository.PlataformaRepository;
import com.retromatic.backend_retromatic.juegos.service.JuegoService;

@ExtendWith(MockitoExtension.class)
class JuegoServiceTest {

    @InjectMocks
    private JuegoService juegoService;

    // --- Mocks Principales ---
    @Mock private JuegoRepository juegoRepository;
    @Mock private ClasificacionRepository clasificacionRepository;
    @Mock private CategoriaRepository categoriaRepository;
    @Mock private CompanniaRepository companniaRepository;
    @Mock private PlataformaRepository plataformaRepository;
    @Mock private ModalidadRepository modalidadRepository;

    // --- Mocks de Tablas Intermedias ---
    @Mock private JuegoCompanniaRepository juegoCompanniaRepository;
    @Mock private JuegoPlataformaRepository juegoPlataformaRepository;
    @Mock private JuegoModalidadRepository juegoModalidadRepository;
    @Mock private JuegoCategoriaRepository juegoCategoriaRepository;

    private Juego createJuego() {
        Juego juego = new Juego();
        juego.setId(1L);
        juego.setTitulo("Super Mario 64");
        juego.setDescripcion("Clásico de plataformas");
        juego.setPrecio(59990);
        return juego;
    }

    private JuegoRequest createJuegoRequest() {
        JuegoRequest request = new JuegoRequest();
        request.setTitulo("Super Mario 64");
        request.setDescripcion("Clásico de plataformas");
        request.setPrecio(59990);
        request.setClasificacionId(1L);
        // Listas de IDs para relaciones
        request.setCategoriaIds(List.of(10L));
        request.setPlataformaIds(List.of(20L));
        request.setModalidadIds(List.of(30L));
        request.setCompaniaIds(List.of(40L));
        return request;
    }

    @Test
    void testGetAllJuegos() {
        when(juegoRepository.findAll()).thenReturn(List.of(createJuego()));
        List<Juego> juegos = juegoService.getAllJuegos();

        assertNotNull(juegos);
        assertEquals(1, juegos.size());
        assertEquals("Super Mario 64", juegos.get(0).getTitulo());
    }

    @Test
    void testGetJuegoById() {
        when(juegoRepository.findById(1L)).thenReturn(Optional.of(createJuego()));
        Juego juego = juegoService.getJuegoById(1L);

        assertNotNull(juego);
        assertEquals("Super Mario 64", juego.getTitulo());
    }

    @Test
    void testCrearJuego() {
        JuegoRequest request = createJuegoRequest();
        Juego juegoGuardadoMock = createJuego(); 
        
        // Mocks para encontrar entidades relacionadas
        when(clasificacionRepository.findById(1L)).thenReturn(Optional.of(new Clasificacion()));
        when(categoriaRepository.findById(10L)).thenReturn(Optional.of(new Categoria()));
        when(plataformaRepository.findById(20L)).thenReturn(Optional.of(new Plataforma()));
        when(modalidadRepository.findById(30L)).thenReturn(Optional.of(new Modalidad()));
        when(companniaRepository.findById(40L)).thenReturn(Optional.of(new Compannia()));

        // Mock del guardado del juego principal
        when(juegoRepository.save(any(Juego.class))).thenReturn(juegoGuardadoMock);

        // Ejecución
        Juego resultado = juegoService.crearJuego(request);

        // Verificaciones
        assertNotNull(resultado);
        verify(juegoRepository, times(1)).save(any(Juego.class));
        
        // Verificar que se guardaron las relaciones en las tablas intermedias
        verify(juegoCategoriaRepository, times(1)).save(any(JuegoCategoria.class));
        verify(juegoPlataformaRepository, times(1)).save(any(com.retromatic.backend_retromatic.juegos.model.JuegoPlataforma.class));
        verify(juegoModalidadRepository, times(1)).save(any(com.retromatic.backend_retromatic.juegos.model.JuegoModalidad.class));
        verify(juegoCompanniaRepository, times(1)).save(any(com.retromatic.backend_retromatic.juegos.model.JuegoCompannia.class));
    }

    @Test
    void testActualizarJuego() {
        JuegoRequest request = createJuegoRequest();
        request.setTitulo("Super Mario 64 Updated");
        Juego juegoExistente = createJuego();

        when(juegoRepository.findById(1L)).thenReturn(Optional.of(juegoExistente));
        when(clasificacionRepository.findById(1L)).thenReturn(Optional.of(new Clasificacion()));
        when(juegoRepository.save(any(Juego.class))).thenReturn(juegoExistente);

        // Simulamos que existen entidades para las nuevas relaciones
        when(categoriaRepository.findById(10L)).thenReturn(Optional.of(new Categoria()));
        when(plataformaRepository.findById(20L)).thenReturn(Optional.of(new Plataforma()));
        when(modalidadRepository.findById(30L)).thenReturn(Optional.of(new Modalidad()));
        when(companniaRepository.findById(40L)).thenReturn(Optional.of(new Compannia()));

        Juego resultado = juegoService.actualizarJuego(1L, request);

        // Verificamos que se llamó a los deleteByJuego (limpieza previa)
        verify(juegoCategoriaRepository, times(1)).deleteByJuego(juegoExistente);
        verify(juegoPlataformaRepository, times(1)).deleteByJuego(juegoExistente);
        
        assertEquals("Super Mario 64 Updated", resultado.getTitulo());
    }

    @Test
    void testDeleteJuego() {
        Juego juego = createJuego();
        when(juegoRepository.findById(1L)).thenReturn(Optional.of(juego));

        juegoService.deleteJuego(1L);

        // Verificamos que se borran las relaciones antes que el juego
        verify(juegoCategoriaRepository).deleteByJuego(juego);
        verify(juegoPlataformaRepository).deleteByJuego(juego);
        verify(juegoRepository).delete(juego);
    }

    @Test
    void testFiltrarJuegos() {
        // Probamos filtrar solo por Plataforma (ID 20)
        Long plataformaId = 20L;
        when(juegoRepository.findByPlataformaId(plataformaId)).thenReturn(List.of(createJuego()));

        List<Juego> resultados = juegoService.filtrarJuegos(null, plataformaId, null, null, null);

        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        verify(juegoRepository).findByPlataformaId(plataformaId);
    }
}