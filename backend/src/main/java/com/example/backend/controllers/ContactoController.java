package com.example.backend.controllers;

import com.example.backend.models.Contacto;
import com.example.backend.services.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contactos")
@CrossOrigin(origins = "http://localhost:4200")
public class ContactoController {

    @Autowired
    private ContactoService contactoService;

    @GetMapping
    public ResponseEntity<List<Contacto>> listar() {
        return new ResponseEntity<>(contactoService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{idContacto}")
    public ResponseEntity<Contacto> obtenerPorId(@PathVariable Long idContacto) {
        return new ResponseEntity<>(contactoService.buscarPorId(idContacto), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Contacto> crear(@RequestBody Contacto contacto) {
        return new ResponseEntity<>(contactoService.guardar(contacto), HttpStatus.CREATED);
    }

    @PutMapping("/{idContacto}")
    public ResponseEntity<Contacto> actualizar(@PathVariable Long idContacto, @RequestBody Contacto contacto) {
        return new ResponseEntity<>(contactoService.actualizar(idContacto, contacto), HttpStatus.OK);
    }

    @DeleteMapping("/{idContacto}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idContacto) {
        contactoService.eliminar(idContacto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}