package com.example.backend.controllers;

import com.example.backend.models.Producto;
import com.example.backend.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.backend.services.ProductoService;

@RestController
@RequestMapping("/api/v1/entities/productos")
public class ProductoController {

    private final ProductoRepository productoRepository;
    @Autowired
    private ProductoService productoService;

    ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllProductos() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductoById(@PathVariable("idProducto") Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> createProducto(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoRepository.save(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProducto(@PathVariable("idProducto") Long id, @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.update(id, producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable("idProducto") Long id) {
        productoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
