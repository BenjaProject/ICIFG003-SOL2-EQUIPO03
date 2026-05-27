package com.example.backend.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {
    private Long idCliente;
    private String rut;
    private String nombreCliente;
    private String apellidoCliente;
    private String correo;
    private String telefono;
    private String direccion;
    private LocalDate fechaRegistro;
}
