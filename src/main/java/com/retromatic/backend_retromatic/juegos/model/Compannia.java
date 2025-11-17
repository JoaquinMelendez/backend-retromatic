package com.retromatic.backend_retromatic.juegos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compannia {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String titulo;

    private Integer precio;
}
