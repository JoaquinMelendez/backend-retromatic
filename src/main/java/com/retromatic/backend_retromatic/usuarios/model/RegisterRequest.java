package com.retromatic.backend_retromatic.usuarios.model;

import lombok.Data;

@Data
public class RegisterRequest {
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasenna;
    private Long regionId;
    private Long comunaId;
    private String direccion;
}