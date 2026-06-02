package com.example.backend.services;

import com.example.backend.models.Contacto;
import com.example.backend.repositories.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactoServiceImpl implements ContactoService {

    @Autowired
    private ContactoRepository contactoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Contacto> listarTodos() {
        return contactoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Contacto buscarPorId(Long idContacto) {
        return contactoRepository.findById(idContacto)
                .orElseThrow(() -> new RuntimeException("Mensaje de contacto no encontrado con ID: " + idContacto));
    }

    @Override
    @Transactional
    public Contacto guardar(Contacto contacto) {
        return contactoRepository.save(contacto);
    }

    @Override
    @Transactional
    public Contacto actualizar(Long idContacto, Contacto contacto) {
        Contacto existente = buscarPorId(idContacto);
        
        existente.setNombre(contacto.getNombre());
        existente.setCorreo(contacto.getCorreo());
        existente.setMensaje(contacto.getMensaje());
        
        return contactoRepository.save(existente);
    }

    @Override
    @Transactional
    public void eliminar(Long idContacto) {
        contactoRepository.deleteById(idContacto);
    }
}