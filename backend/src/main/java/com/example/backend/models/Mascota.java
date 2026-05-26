package com.example.backend.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Entity
@Table(name = "mascota")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMascota;
    @Column(name = "nombre_mascota", nullable = false, length = 100)
    private String nombreMascota;
    @Column(name = "especie", nullable = false, length = 50)
    private String especie;
    @Column(name = "raza", nullable = false, length = 100)
    private String raza;
    @Column(name = "sexo", nullable = false, length = 1)
    private char sexo;
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;
    @Column(name = "peso", precision = 5, scale = 2, nullable = false)
    private BigDecimal peso;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente",nullable = false)
    private Cliente cliente;
}
