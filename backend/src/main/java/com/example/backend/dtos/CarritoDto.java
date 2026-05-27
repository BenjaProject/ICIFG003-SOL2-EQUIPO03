package com.example.backend.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarritoDto {
    private Long idCarrito;
    private LocalDate fechaCreacion;
    private ClienteDto cliente;
    private List<DetalleCarritoDto> items;
    private BigDecimal totalAcumulado;
}
