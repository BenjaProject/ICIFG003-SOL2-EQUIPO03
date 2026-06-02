package com.example.backend.services;

import java.util.List;

import com.example.backend.models.Producto;

public interface ProductoService {

    List<Producto>findAll(Long idCategoria);
    Producto findById(Long id);
    Producto save(Producto producto);
    Producto update(Long id, Producto producto);
    void deleteById(Long id);

}
