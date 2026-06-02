package com.example.backend.services;

import com.example.backend.models.Contacto;
import java.util.List;

public interface ContactoService {
    List<Contacto> listarTodos();
    Contacto buscarPorId(Long idContacto);
    Contacto guardar(Contacto contacto);
    Contacto actualizar(Long idContacto, Contacto contacto);
    void eliminar(Long idContacto);
}