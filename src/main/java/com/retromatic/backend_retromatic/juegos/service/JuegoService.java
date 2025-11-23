package com.retromatic.backend_retromatic.juegos.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.retromatic.backend_retromatic.juegos.model.Categoria;
import com.retromatic.backend_retromatic.juegos.model.Clasificacion;
import com.retromatic.backend_retromatic.juegos.model.Compannia;
import com.retromatic.backend_retromatic.juegos.model.Juego;
import com.retromatic.backend_retromatic.juegos.model.JuegoCategoria;
import com.retromatic.backend_retromatic.juegos.model.JuegoCompannia;
import com.retromatic.backend_retromatic.juegos.model.JuegoModalidad;
import com.retromatic.backend_retromatic.juegos.model.JuegoPlataforma;
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

@Service
public class JuegoService {
    @Autowired
    private JuegoRepository juegoRepository;
    @Autowired
    private ClasificacionRepository clasificacionRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private CompanniaRepository companniaRepository;

    @Autowired
    private PlataformaRepository plataformaRepository;
    @Autowired
    private ModalidadRepository modalidadRepository;

    @Autowired
    private JuegoCompanniaRepository juegoCompanniaRepository;
    @Autowired
    private JuegoPlataformaRepository juegoPlataformaRepository;
    @Autowired
    private JuegoModalidadRepository juegoModalidadRepository;
    @Autowired
    private JuegoCategoriaRepository juegoCategoriaRepository;

    public List<Juego> getAllJuegos(){
        return juegoRepository.findAll();
    }

    public Juego getJuegoById(Long id){
        return juegoRepository.findById(id).orElse(null);
    }

    public Juego saveJuego(Juego juego){
        return juegoRepository.save(juego);
    }


    @Transactional
    public Juego crearJuego(JuegoRequest request) {

        Juego juego = new Juego();
        juego.setTitulo(request.getTitulo());
        juego.setDescripcion(request.getDescripcion());
        juego.setPrecio(request.getPrecio());
        juego.setUrlPortada(request.getUrlPortada());

        Clasificacion clasificacion = clasificacionRepository.findById(request.getClasificacionId())
                .orElseThrow(() -> new RuntimeException("Clasificación no encontrada"));
        juego.setClasificacion(clasificacion);

        Juego guardado = juegoRepository.save(juego);

        // CATEGORÍAS
        if (request.getCategoriaIds() != null) {
            for (Long categoriaId : request.getCategoriaIds()) {
                Categoria cat = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

                JuegoCategoria jc = new JuegoCategoria();
                jc.setJuego(guardado);
                jc.setCategoria(cat);

                juegoCategoriaRepository.save(jc);
            }
        }

        // PLATAFORMAS
        if (request.getPlataformaIds() != null) {
            for (Long plataformaId : request.getPlataformaIds()) {
                Plataforma plataforma = plataformaRepository.findById(plataformaId)
                    .orElseThrow(() -> new RuntimeException("Plataforma no encontrada"));

                JuegoPlataforma jp = new JuegoPlataforma();
                jp.setJuego(guardado);
                jp.setPlataforma(plataforma);

                juegoPlataformaRepository.save(jp);
            }
        }

        // MODALIDADES
        if (request.getModalidadIds() != null) {
            for (Long modalidadId : request.getModalidadIds()) {
                Modalidad modalidad = modalidadRepository.findById(modalidadId)
                    .orElseThrow(() -> new RuntimeException("Modalidad no encontrada"));

                JuegoModalidad jm = new JuegoModalidad();
                jm.setJuego(guardado);
                jm.setModalidad(modalidad);

                juegoModalidadRepository.save(jm);
            }
        }

        // COMPAÑÍAS
        if (request.getCompaniaIds() != null) {
            for (Long companiaId : request.getCompaniaIds()) {
                Compannia compania = companniaRepository.findById(companiaId)
                    .orElseThrow(() -> new RuntimeException("Compañía no encontrada"));

                JuegoCompannia jco = new JuegoCompannia();
                jco.setJuego(guardado);
                jco.setCompannia(compania);

                juegoCompanniaRepository.save(jco);
            }
        }

        return guardado;
    }

        @Transactional
    public Juego actualizarJuego(Long id, JuegoRequest request) {

        Juego juego = juegoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Juego no encontrado"));

        juego.setTitulo(request.getTitulo());
        juego.setDescripcion(request.getDescripcion());
        juego.setPrecio(request.getPrecio());
        juego.setUrlPortada(request.getUrlPortada());

        Clasificacion clasificacion = clasificacionRepository.findById(request.getClasificacionId())
                .orElseThrow(() -> new RuntimeException("Clasificación no encontrada"));
        juego.setClasificacion(clasificacion);

        juegoCategoriaRepository.deleteByJuego(juego);
        juegoPlataformaRepository.deleteByJuego(juego);
        juegoModalidadRepository.deleteByJuego(juego);
        juegoCompanniaRepository.deleteByJuego(juego);

        Juego guardado = juegoRepository.save(juego);

        if (request.getCategoriaIds() != null) {
            for (Long categoriaId : request.getCategoriaIds()) {
                Categoria cat = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

                JuegoCategoria jc = new JuegoCategoria();
                jc.setJuego(guardado);
                jc.setCategoria(cat);

                juegoCategoriaRepository.save(jc);
            }
        }

        if (request.getPlataformaIds() != null) {
            for (Long plataformaId : request.getPlataformaIds()) {
                Plataforma plataforma = plataformaRepository.findById(plataformaId)
                    .orElseThrow(() -> new RuntimeException("Plataforma no encontrada"));

                JuegoPlataforma jp = new JuegoPlataforma();
                jp.setJuego(guardado);
                jp.setPlataforma(plataforma);

                juegoPlataformaRepository.save(jp);
            }
        }

        // MODALIDADES
        if (request.getModalidadIds() != null) {
            for (Long modalidadId : request.getModalidadIds()) {
                Modalidad modalidad = modalidadRepository.findById(modalidadId)
                    .orElseThrow(() -> new RuntimeException("Modalidad no encontrada"));

                JuegoModalidad jm = new JuegoModalidad();
                jm.setJuego(guardado);
                jm.setModalidad(modalidad);

                juegoModalidadRepository.save(jm);
            }
        }

        // COMPAÑÍAS
        if (request.getCompaniaIds() != null) {
            for (Long companiaId : request.getCompaniaIds()) {
                Compannia compania = companniaRepository.findById(companiaId)
                    .orElseThrow(() -> new RuntimeException("Compañía no encontrada"));

                JuegoCompannia jco = new JuegoCompannia();
                jco.setJuego(guardado);
                jco.setCompannia(compania);

                juegoCompanniaRepository.save(jco);
            }
        }

        return guardado;
    }


    @Transactional
    public void deleteJuego(Long id){

        Juego juego = juegoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Juego no encontrado"));

        juegoCategoriaRepository.deleteByJuego(juego);
        juegoPlataformaRepository.deleteByJuego(juego);
        juegoModalidadRepository.deleteByJuego(juego);
        juegoCompanniaRepository.deleteByJuego(juego);

        juegoRepository.delete(juego);
    }

    public List<Juego> obtenerPorCompannia(Long companniaId) {
        return juegoRepository.findByCompanniaId(companniaId);
    }

    public List<Juego> obtenerPorPlataforma(Long plataformaId) {
        return juegoRepository.findByPlataformaId(plataformaId);
    }

    public List<Juego> obtenerPorModalidad(Long modalidadId) {
        return juegoRepository.findByModalidadId(modalidadId);
    }

    public List<Juego> obtenerPorCategoria(Long categoriaId) {
        return juegoRepository.findByCategoriaId(categoriaId);
    }

    public List<Juego> obtenerPorClasificacion(Long clasificacionId) {
        return juegoRepository.findByClasificacionId(clasificacionId);
    }

    public List<Juego> filtrarJuegos(
            Long companniaId,
            Long plataformaId,
            Long modalidadId,
            Long categoriaId,
            Long clasificacionId
    ) {
        // Si no se pasa ningún filtro, devolvemos todo
        if (companniaId == null &&
            plataformaId == null &&
            modalidadId == null &&
            categoriaId == null &&
            clasificacionId == null) {
            return juegoRepository.findAll();
        }

        // Usamos un Set para ir intersectando resultados
        Set<Juego> resultado = null;

        if (companniaId != null) {
            List<Juego> porCompannia = juegoRepository.findByCompanniaId(companniaId);
            resultado = new HashSet<>(porCompannia);
        }

        if (plataformaId != null) {
            List<Juego> porPlataforma = juegoRepository.findByPlataformaId(plataformaId);
            if (resultado == null) {
                resultado = new HashSet<>(porPlataforma);
            } else {
                resultado.retainAll(porPlataforma); // intersección
            }
        }

        if (modalidadId != null) {
            List<Juego> porModalidad = juegoRepository.findByModalidadId(modalidadId);
            if (resultado == null) {
                resultado = new HashSet<>(porModalidad);
            } else {
                resultado.retainAll(porModalidad);
            }
        }

        if (categoriaId != null) {
            List<Juego> porCategoria = juegoRepository.findByCategoriaId(categoriaId);
            if (resultado == null) {
                resultado = new HashSet<>(porCategoria);
            } else {
                resultado.retainAll(porCategoria);
            }
        }

        if (clasificacionId != null) {
            List<Juego> porClasificacion = juegoRepository.findByClasificacionId(clasificacionId);
            if (resultado == null) {
                resultado = new HashSet<>(porClasificacion);
            } else {
                resultado.retainAll(porClasificacion);
            }
        }

        if (resultado == null) {
            return juegoRepository.findAll();
        }

        return new ArrayList<>(resultado);
    }

}
