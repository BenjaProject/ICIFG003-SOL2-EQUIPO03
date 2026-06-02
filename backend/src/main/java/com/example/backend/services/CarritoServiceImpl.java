package com.example.backend.services;

import com.example.backend.models.Carrito;
import com.example.backend.models.Cliente;
import com.example.backend.models.DetalleCarrito;
import com.example.backend.models.Producto;
import com.example.backend.repositories.CarritoRepository;
import com.example.backend.repositories.ClienteRepository;
import com.example.backend.repositories.DetalleCarritoRepository;
import com.example.backend.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarritoServiceImpl implements CarritoService {
    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private DetalleCarritoRepository detalleCarritoRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
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

    @Override
    @Transactional
    public Carrito obtenerCarritoActivo() {
        return carritoRepository.findByClienteIdCliente(1L)
                .orElseGet(() -> {
                    Cliente cliente = clienteRepository.findById(1L)
                            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: 1"));
                    Carrito nuevoCarrito = Carrito.builder()
                            .fechaCreacion(LocalDate.now())
                            .cliente(cliente)
                            .items(new ArrayList<>())
                            .build();
                    return carritoRepository.save(nuevoCarrito);
                });
    }

    @Override
    @Transactional
    public Carrito agregarProducto(Long idProducto, Integer cantidad) {
        Carrito carrito = obtenerCarritoActivo();
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + idProducto));

        if (carrito.getItems() == null) {
            carrito.setItems(new ArrayList<>());
        }

        DetalleCarrito existente = carrito.getItems().stream()
                .filter(item -> item.getProducto() != null
                        && item.getProducto().getIdProducto().equals(idProducto))
                .findFirst()
                .orElse(null);

        if (existente != null) {
            existente.setCantidad(existente.getCantidad() + cantidad);
            detalleCarritoRepository.save(existente);
        } else {
            DetalleCarrito nuevoDetalle = DetalleCarrito.builder()
                    .carrito(carrito)
                    .producto(producto)
                    .cantidad(cantidad)
                    .precioUnitario(producto.getPrecio())
                    .build();
            detalleCarritoRepository.save(nuevoDetalle);
            carrito.getItems().add(nuevoDetalle);
        }

        return carritoRepository.save(carrito);
    }
}