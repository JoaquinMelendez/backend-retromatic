package com.retromatic.backend_retromatic.usuarios.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String rol;

}
