package com.retromatic.backend_retromatic.juegos.model;
import java.util.List;
import lombok.Data;

@Data
public class JuegoRequest {
    private String titulo;
    private String descripcion;
    private Integer precio;
    private String urlPortada;

    private Long clasificacionId;
    private List<Long> categoriaIds;
    private List<Long> plataformaIds;
    private List<Long> modalidadIds;
    private List<Long> companiaIds;

}