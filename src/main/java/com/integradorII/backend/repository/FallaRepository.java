package com.integradorII.backend.repository;

import com.integradorII.backend.model.Falla;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FallaRepository extends JpaRepository<Falla, Long> {
    List<Falla> findByMaquinaNombre(String nombre);
    List<Falla> findByTecnicoMantenimientoId(Long id);
    List<Falla> findByFechaRegistroBetween(LocalDateTime start, LocalDateTime end);
}
