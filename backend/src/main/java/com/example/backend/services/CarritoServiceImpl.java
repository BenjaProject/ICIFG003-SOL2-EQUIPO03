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
import java.util.Optional;

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
    
    // 1. Buscamos el producto en la base de datos para verificar su stock real actual
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

    // 2. REGLA DE ORO: Verificamos si hay suficiente stock físico en la base de datos
        if (producto.getStock() < cantidad) {
            throw new IllegalArgumentException("No hay suficiente stock. Solamente quedan " + producto.getStock() + " unidades en bodega.");
        }

    // 3. RESTAMOS EL STOCK EN LA BASE DE DATOS: El producto reduce su inventario inmediatamente
        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto); // Actualiza la tabla 'producto' en PostgreSQL

    // 4. Gestionamos el item dentro del carrito
        Optional<DetalleCarrito> detalleExistente = carrito.getItems().stream()
                .filter(item -> item.getProducto().getIdProducto().equals(idProducto))
                .findFirst();

        if (detalleExistente.isPresent()) {
        // Si el producto ya estaba en el carrito, solo incrementamos su cantidad
            DetalleCarrito detalle = detalleExistente.get();
            detalle.setCantidad(detalle.getCantidad() + cantidad);
            detalleCarritoRepository.save(detalle);
        } 
        else {
        // Si es la primera vez que entra al carrito, creamos el detalle desde cero
            DetalleCarrito nuevoDetalle = DetalleCarrito.builder()
                    .carrito(carrito)
                    .producto(producto)
                    .cantidad(cantidad)
                    .precioUnitario(producto.getPrecio()) // Congelamos el precio de venta actual
                    .build();
        
            detalleCarritoRepository.save(nuevoDetalle);
            carrito.getItems().add(nuevoDetalle);
        }

        return carrito;
    }
}