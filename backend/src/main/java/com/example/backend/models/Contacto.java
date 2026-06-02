package com.example.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contacto")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContacto;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "correo", nullable = false, length = 150)
    private String correo;

    @Column(name = "mensaje", nullable = false, columnDefinition = "TEXT")
    private String mensaje;
}