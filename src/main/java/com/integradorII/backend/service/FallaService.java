package com.integradorII.backend.service;

import com.integradorII.backend.model.Falla;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.integradorII.backend.repository.FallaRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FallaService implements IFallaService {

    private final FallaRepository fallaRepository;

    @Override
    public Falla registrarFalla(Falla falla) {
        if (falla.getFechaRegistro() == null) {
            ZonedDateTime limaTime = ZonedDateTime.now(ZoneId.of("America/Lima"));
            falla.setFechaRegistro(limaTime.toLocalDateTime());
        }
        return fallaRepository.save(falla);
    }

    @Override
    public Optional<Falla> buscarPorId(Long id) {
        return fallaRepository.findById(id);
    }

    @Override
    public List<Falla> listarTodas() {
        return fallaRepository.findAll();
    }

    @Override
    public void eliminarFalla(Long id) {
        fallaRepository.deleteById(id);
    }

    @Override
    public List<Falla> filtrarPorMaquina(String nombreMaquina) {
        return fallaRepository.findByMaquinaNombre(nombreMaquina);
    }

    @Override
    public List<Falla> filtrarPorTecnico(Long idTecnico) {
        return fallaRepository.findByTecnicoMantenimientoId(idTecnico);
    }

    @Override
    public List<Falla> filtrarPorFecha(LocalDateTime inicio, LocalDateTime fin) {
        return fallaRepository.findByFechaRegistroBetween(inicio, fin);
    }
}

