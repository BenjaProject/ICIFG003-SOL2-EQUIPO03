package com.example.backend.controllers;

import com.example.backend.models.Producto;
import com.example.backend.repositories.ProductoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.backend.services.ProductoService;

@RestController
@RequestMapping("/api/v1/entities/productos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

    private final ProductoRepository productoRepository;
    @Autowired
    private ProductoService productoService;

    private Logger logger = LoggerFactory.getLogger(ProductoController.class);

    ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllProductos(@RequestParam(required = false) Long idCategoria) {
        logger.info("Recibiendo solicitud para obtener productos con idCategoria: {}", idCategoria);
        return ResponseEntity.ok(productoService.findAll(idCategoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductoById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> createProducto(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoRepository.save(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProducto(@PathVariable("id") Long id, @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.update(id, producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable("id") Long id) {
        productoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
