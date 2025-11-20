package com.retromatic.backend_retromatic.usuarios.model;

import lombok.Data;

@Data
public class LoginRequest {

    private String correo;

    private String contrasenna;
}
