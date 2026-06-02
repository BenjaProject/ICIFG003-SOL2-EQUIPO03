package com.example.backend.repositories;

import com.example.backend.models.DetalleCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleCarritoRepository extends JpaRepository<DetalleCarrito, Long> {
}
