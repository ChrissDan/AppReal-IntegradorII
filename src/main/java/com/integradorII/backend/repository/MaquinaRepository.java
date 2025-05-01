package com.integradorII.backend.repository;

import com.integradorII.backend.model.Maquina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaquinaRepository extends JpaRepository<Maquina, Long> {
    List<Maquina> findBySeccionId(Long seccionId);
}
