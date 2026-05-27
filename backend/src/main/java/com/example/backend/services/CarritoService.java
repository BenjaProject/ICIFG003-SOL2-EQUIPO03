package com.example.backend.services;
import com.example.backend.models.Carrito;
import java.util.List;

public interface CarritoService {
    List<Carrito> listarTodos();
    Carrito buscarPorId(Long id);
    Carrito guardar(Carrito carrito);
    Carrito actualizar(Long id, Carrito carrito);
    void eliminar(Long id);
}