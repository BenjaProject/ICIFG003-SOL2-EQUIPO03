package com.example.backend.services;

import com.example.backend.models.Cliente;
import com.example.backend.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    @Override
    @Transactional
    public Cliente actualizar(Long id, Cliente cliente) {
        Cliente existente = buscarPorId(id);
        // Actualizamos usando los nuevos getters y setters que generó Lombok
        existente.setRut(cliente.getRut());
        existente.setNombreCliente(cliente.getNombreCliente());
        existente.setApellidoCliente(cliente.getApellidoCliente());
        existente.setCorreo(cliente.getCorreo());
        existente.setTelefono(cliente.getTelefono());
        existente.setDireccion(cliente.getDireccion());
        // Validamos que no se sobreescriba la fecha de registro original a menos que venga en el JSON
        if (cliente.getFechaRegistro() != null) {
            existente.setFechaRegistro(cliente.getFechaRegistro());
        }
        
        return clienteRepository.save(existente);
    }
    @Override
    @Transactional
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}