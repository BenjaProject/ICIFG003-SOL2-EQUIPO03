package com.example.backend.services;

import com.example.backend.models.CategoriaProducto;
import com.example.backend.repositories.CategoriaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaProductoServiceImpl implements CategoriaProductoService {

    @Autowired
    private CategoriaProductoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaProducto> listarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaProducto buscarPorId(Long idCategoria) {
        return repository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + idCategoria));
    }

    @Override
    @Transactional
    public CategoriaProducto guardar(CategoriaProducto categoria) {
        return repository.save(categoria);
    }

    @Override
    @Transactional
    public CategoriaProducto actualizar(Long idCategoria, CategoriaProducto categoria) {
        CategoriaProducto existente = buscarPorId(idCategoria);
        
        existente.setNombreCategoria(categoria.getNombreCategoria());
        existente.setDescripcion(categoria.getDescripcion());
        
        return repository.save(existente);
    }

    @Override
    @Transactional
    public void eliminar(Long idCategoria) {
        repository.deleteById(idCategoria);
    }
}