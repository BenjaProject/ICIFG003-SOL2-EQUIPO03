package com.example.backend.services;

import com.example.backend.models.CategoriaProducto;
import java.util.List;

public interface CategoriaProductoService {
    List<CategoriaProducto> listarTodos();
    CategoriaProducto buscarPorId(Long idCategoria);
    CategoriaProducto guardar(CategoriaProducto categoria);
    CategoriaProducto actualizar(Long idCategoria, CategoriaProducto categoria);
    void eliminar(Long idCategoria);
}