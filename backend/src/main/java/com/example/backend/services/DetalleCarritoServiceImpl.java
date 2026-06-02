package com.example.backend.services;

import com.example.backend.models.DetalleCarrito;
import com.example.backend.repositories.DetalleCarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DetalleCarritoServiceImpl implements DetalleCarritoService {

    @Autowired
    private DetalleCarritoRepository detalleCarritoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DetalleCarrito> listarTodos() {
        return detalleCarritoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleCarrito buscarPorId(Long idDetalleCarrito) {
        return detalleCarritoRepository.findById(idDetalleCarrito)
                .orElseThrow(() -> new RuntimeException("Detalle de carrito no encontrado con ID: " + idDetalleCarrito));
    }

    @Override
    @Transactional
    public DetalleCarrito guardar(DetalleCarrito detalleCarrito) {
        return detalleCarritoRepository.save(detalleCarrito);
    }

    @Override
    @Transactional
    public DetalleCarrito actualizar(Long idDetalleCarrito, DetalleCarrito detalleCarrito) {
        DetalleCarrito existente = buscarPorId(idDetalleCarrito);
        existente.setCarrito(detalleCarrito.getCarrito());
        existente.setProducto(detalleCarrito.getProducto());
        existente.setCantidad(detalleCarrito.getCantidad());
        existente.setPrecioUnitario(detalleCarrito.getPrecioUnitario());
        
        return detalleCarritoRepository.save(existente);
    }

    @Override
    @Transactional
    public void eliminar(Long idDetalleCarrito) {
        detalleCarritoRepository.deleteById(idDetalleCarrito);
    }
}