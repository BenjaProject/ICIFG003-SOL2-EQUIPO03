package com.example.backend.models;

import java.time.LocalDate;

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


@Data
@Entity
@Table(name = "cliente")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;
    @Column(name = "rut", nullable = false, unique = true, length = 12)
    private String rut;
    @Column(name = "nombre_cliente", nullable = false, length = 100)
    private String nombreCliente;
    @Column(name = "apellido_cliente", nullable = false, length = 100)
    private String apellidoCliente;
    @Column(name = "correo", nullable = true, length = 150)
    private String correo;
    @Column(name = "telefono", nullable = true, length = 20)
    private String telefono;
    @Column(name = "direccion", nullable = true, length = 200)
    private String direccion;
    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

}
