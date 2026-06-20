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
        Carrito carrito = carritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con ID: " + id));
        return ordenarItemsYRetornar(carrito);
    }
    @Override
    @Transactional
    public Carrito guardar(Carrito carrito) {
        return ordenarItemsYRetornar(carritoRepository.save(carrito));
    }
    @Override
    @Transactional
    public Carrito actualizar(Long id, Carrito carrito) {
        Carrito existente = buscarPorId(id);
        existente.setFechaCreacion(carrito.getFechaCreacion());
        existente.setCliente(carrito.getCliente());
        return ordenarItemsYRetornar(carritoRepository.save(existente));
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
        Carrito carrito = carritoRepository.findByClienteIdClienteAndCompradoFalse(1L)
                .orElseGet(() -> {
                    Cliente cliente = clienteRepository.findById(1L)
                            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: 1"));
                    Carrito nuevoCarrito = Carrito.builder()
                            .fechaCreacion(LocalDate.now())
                            .cliente(cliente)
                            .items(new ArrayList<>())
                            .comprado(false)
                            .build();
                    return carritoRepository.save(nuevoCarrito);
                });
        return ordenarItemsYRetornar(carrito);
    }

    @Override
    @Transactional
    public Carrito agregarProducto(Long idProducto, Integer cantidad) {
        Carrito carrito = obtenerCarritoActivo();
    
    //Buscamos el producto en la base de datos para verificar su stock real actual
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

    //Verificamos si hay suficiente stock físico en la base de datos
        if (producto.getStock() < cantidad) {
            throw new IllegalArgumentException("No hay suficiente stock. Solamente quedan " + producto.getStock() + " unidades en bodega.");
        }

    //RESTAMOS EL STOCK EN LA BASE DE DATOS: El producto reduce su inventario inmediatamente
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

        return ordenarItemsYRetornar(carrito);
    }

    @Override
    @Transactional
    public Carrito eliminarProducto(Long idProducto) {
        Carrito carrito = obtenerCarritoActivo();
        DetalleCarrito detalle = carrito.getItems().stream().
            filter(item -> item.getProducto().getIdProducto().equals(idProducto))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("El producto no está en el carrito"));

        Producto producto = detalle.getProducto();
        producto.setStock(producto.getStock() + detalle.getCantidad());
        productoRepository.save(producto);

        carrito.getItems().remove(detalle);
        detalleCarritoRepository.delete(detalle);

        return ordenarItemsYRetornar(carritoRepository.save(carrito));
    }
    
    @Override
    @Transactional
    public Carrito vaciarCarrito() {
        Carrito carrito = obtenerCarritoActivo();
        for (DetalleCarrito detalle : carrito.getItems()) {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepository.save(producto);
        }
        detalleCarritoRepository.deleteAll(carrito.getItems());
        carrito.getItems().clear();
        return ordenarItemsYRetornar(carritoRepository.save(carrito));
    }

    @Override
    @Transactional
    public Carrito comprarCarrito() {
        Carrito carrito = obtenerCarritoActivo();
        if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new IllegalArgumentException("No se puede realizar una compra con el carrito vacío.");
        }

        // Marcamos el carrito actual como comprado
        carrito.setComprado(true);
        carritoRepository.save(carrito);

        // Creamos y retornamos un nuevo carrito activo y vacío para el cliente genérico
        Cliente cliente = clienteRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: 1"));
        Carrito nuevoCarrito = Carrito.builder()
                .fechaCreacion(LocalDate.now())
                .cliente(cliente)
                .items(new ArrayList<>())
                .comprado(false)
                .build();

        return ordenarItemsYRetornar(carritoRepository.save(nuevoCarrito));
    }

    @Override
    @Transactional
    public Carrito restarProducto(Long idProducto, Integer cantidad) {
        Carrito carrito = obtenerCarritoActivo();

        DetalleCarrito detalle = carrito.getItems().stream()
                .filter(item -> item.getProducto().getIdProducto().equals(idProducto))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("El producto no está en el carrito"));

        // Devolvemos el stock restado al producto físico
        Producto producto = detalle.getProducto();
        producto.setStock(producto.getStock() + cantidad);
        productoRepository.save(producto);

        if (detalle.getCantidad() <= cantidad) {
            // Si la cantidad a restar es igual o mayor a la que tenemos, removemos el item
            carrito.getItems().remove(detalle);
            detalleCarritoRepository.delete(detalle);
        } else {
            // Sino, solo decrementamos la cantidad en el detalle
            detalle.setCantidad(detalle.getCantidad() - cantidad);
            detalleCarritoRepository.save(detalle);
        }

        return ordenarItemsYRetornar(carritoRepository.save(carrito));
    }

    private Carrito ordenarItemsYRetornar(Carrito carrito) {
        if (carrito != null && carrito.getItems() != null) {
            carrito.getItems().sort((a, b) -> {
                Long idA = a.getIdDetalleCarrito();
                Long idB = b.getIdDetalleCarrito();
                if (idA == null && idB == null) return 0;
                if (idA == null) return 1; // Enviar nuevos items al final
                if (idB == null) return -1;
                return idA.compareTo(idB);
            });
        }
        return carrito;
    }
}