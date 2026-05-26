package com.example.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "veterinario")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Veterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVeterinario;
    @Column(name = "nombre_vet", nullable = false, length = 100)
    private String nombreVet;
    @Column(name = "apellido_vet", nullable = false, length = 100)
    private String apellidoVet;
    @Column(name = "especialidad", nullable = false, length = 100)
    private String especialidad;
    @Column(name = "correo", nullable = false, length = 150)
    private String correo;
    @Column(name = "telefono", nullable = true, length = 20)
    private String telefono;

}
