package com.example.backend.controllers;

import com.example.backend.models.DetalleCarrito;
import com.example.backend.services.DetalleCarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles-carrito")
@CrossOrigin(origins = "http://localhost:4200")
public class DetalleCarritoController {

    @Autowired
    private DetalleCarritoService detalleCarritoService;

    @GetMapping
    public ResponseEntity<List<DetalleCarrito>> listar() {
        return new ResponseEntity<>(detalleCarritoService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{idDetalleCarrito}")
    public ResponseEntity<DetalleCarrito> obtenerPorId(@PathVariable Long idDetalleCarrito) {
        return new ResponseEntity<>(detalleCarritoService.buscarPorId(idDetalleCarrito), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DetalleCarrito> crear(@RequestBody DetalleCarrito detalleCarrito) {
        return new ResponseEntity<>(detalleCarritoService.guardar(detalleCarrito), HttpStatus.CREATED);
    }

    @PutMapping("/{idDetalleCarrito}")
    public ResponseEntity<DetalleCarrito> actualizar(@PathVariable Long idDetalleCarrito, @RequestBody DetalleCarrito detalleCarrito) {
        return new ResponseEntity<>(detalleCarritoService.actualizar(idDetalleCarrito, detalleCarrito), HttpStatus.OK);
    }

    @DeleteMapping("/{idDetalleCarrito}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idDetalleCarrito) {
        detalleCarritoService.eliminar(idDetalleCarrito);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}