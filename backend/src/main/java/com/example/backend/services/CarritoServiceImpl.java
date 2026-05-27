package com.example.backend.services;

import com.example.backend.models.Carrito;
import com.example.backend.repositories.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CarritoServiceImpl implements CarritoService {
    @Autowired
    private CarritoRepository carritoRepository;
    @Override
    @Transactional(readOnly = true)
    public List<Carrito> listarTodos() {
        return carritoRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public Carrito buscarPorId(Long id) {
        return carritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con ID: " + id));
    }
    @Override
    @Transactional
    public Carrito guardar(Carrito carrito) {
        return carritoRepository.save(carrito);
    }
    @Override
    @Transactional
    public Carrito actualizar(Long id, Carrito carrito) {
        Carrito existente = buscarPorId(id);
        existente.setFechaCreacion(carrito.getFechaCreacion());
        existente.setCliente(carrito.getCliente());
        return carritoRepository.save(existente);
    }
    @Override
    @Transactional
    public void eliminar(Long id) {
        Carrito carrito = buscarPorId(id);
        carritoRepository.delete(carrito);
    }
}