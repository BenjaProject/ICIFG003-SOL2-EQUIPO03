package com.example.backend.services;

import com.example.backend.models.DetalleCarrito;
import java.util.List;

public interface DetalleCarritoService {
    List<DetalleCarrito> listarTodos();
    DetalleCarrito buscarPorId(Long idDetalleCarrito);
    DetalleCarrito guardar(DetalleCarrito detalleCarrito);
    DetalleCarrito actualizar(Long idDetalleCarrito, DetalleCarrito detalleCarrito);
    void eliminar(Long idDetalleCarrito);
}