package com.example.backend.controllers;

import com.example.backend.models.Carrito;
import com.example.backend.services.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/entities/carritos")
@CrossOrigin(origins = "http://localhost:4200")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping
    public ResponseEntity<List<Carrito>> listar() {
        return new ResponseEntity<>(carritoService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrito> obtenerPorId(@PathVariable Long id) {
        return new ResponseEntity<>(carritoService.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Carrito> crear(@RequestBody Carrito carrito) {
        return new ResponseEntity<>(carritoService.guardar(carrito), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carrito> actualizar(@PathVariable Long id, @RequestBody Carrito carrito) {
        return new ResponseEntity<>(carritoService.actualizar(id, carrito), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        carritoService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/")
    public ResponseEntity<Carrito> obtenerCarritoActivo() {
        return ResponseEntity.ok(carritoService.obtenerCarritoActivo());
    }

    @PostMapping("/agregar")
    public ResponseEntity<Carrito> agregarProducto(
            @RequestParam Long idProducto,
            @RequestParam(defaultValue = "1") Integer cantidad) {
        return ResponseEntity.ok(carritoService.agregarProducto(idProducto, cantidad));
    }


    @DeleteMapping("/eliminar")
    public ResponseEntity<Carrito> eliminarProducto(@RequestParam Long idProducto) {
        Carrito carritoActualizado = carritoService.eliminarProducto(idProducto);
        return ResponseEntity.ok(carritoActualizado);
    }

    @DeleteMapping("/vaciar")
    public ResponseEntity<Carrito> vaciarCarrito() {
        Carrito carritoVacio = carritoService.vaciarCarrito();
        return ResponseEntity.ok(carritoVacio);
}
}