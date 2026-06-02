package com.example.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.models.Producto;
import com.example.backend.repositories.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoRepository productoRepository;
    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto update(Long id, Producto producto) {
        Producto existingProducto = productoRepository.findById(id).orElse(null);
        if (existingProducto != null) {
            existingProducto.setNombreProducto(producto.getNombreProducto());
            existingProducto.setDescripcion(producto.getDescripcion());
            existingProducto.setPrecio(producto.getPrecio());
            return productoRepository.save(existingProducto);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }

}
