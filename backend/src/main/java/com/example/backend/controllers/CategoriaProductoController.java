package com.example.backend.controllers;

import com.example.backend.models.CategoriaProducto;
import com.example.backend.services.CategoriaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoriaProductoController {

    @Autowired
    private CategoriaProductoService service;

    @GetMapping
    public ResponseEntity<List<CategoriaProducto>> listar() {
        return new ResponseEntity<>(service.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaProducto> obtenerPorId(@PathVariable Long idCategoria) {
        return new ResponseEntity<>(service.buscarPorId(idCategoria), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoriaProducto> crear(@RequestBody CategoriaProducto categoria) {
        return new ResponseEntity<>(service.guardar(categoria), HttpStatus.CREATED);
    }

    @PutMapping("/{idCategoria}")
    public ResponseEntity<CategoriaProducto> actualizar(@PathVariable Long idCategoria, @RequestBody CategoriaProducto categoria) {
        return new ResponseEntity<>(service.actualizar(idCategoria, categoria), HttpStatus.OK);
    }

    @DeleteMapping("/{idCategoria}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idCategoria) {
        service.eliminar(idCategoria);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}