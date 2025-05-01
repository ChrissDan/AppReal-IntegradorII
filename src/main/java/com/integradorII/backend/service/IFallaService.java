package com.integradorII.backend.service;

import com.integradorII.backend.model.Falla;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IFallaService {
    Falla registrarFalla(Falla falla);
    Optional<Falla> buscarPorId(Long id);
    List<Falla> listarTodas();
    void eliminarFalla(Long id);
    List<Falla> filtrarPorMaquina(String nombreMaquina);
    List<Falla> filtrarPorTecnico(Long idTecnico);
    List<Falla> filtrarPorFecha(LocalDateTime inicio, LocalDateTime fin);
}

