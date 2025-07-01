package com.integradorII.backend.controller;

import com.integradorII.backend.model.EstadoFalla;
import lombok.RequiredArgsConstructor;
import com.integradorII.backend.model.Falla;
import org.springframework.web.bind.annotation.*;
import com.integradorII.backend.service.IFallaService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fallas")
@RequiredArgsConstructor
@CrossOrigin
public class FallaController {

    private final IFallaService fallaService;

    @PostMapping
    public Falla crear(@RequestBody Falla falla) {
        return fallaService.registrarFalla(falla);
    }

    @PutMapping("/{id}")
    public Falla actualizar(@PathVariable Long id, @RequestBody Falla actualizada) {
        return fallaService.buscarPorId(id).map(falla -> {
            falla.setDescripcion(actualizada.getDescripcion());
            falla.setSeccion(actualizada.getSeccion());
            falla.setMaquina(actualizada.getMaquina());
            falla.setEstado(actualizada.getEstado());
            falla.setFechaActualizacion(actualizada.getFechaActualizacion());
            falla.setTecnicoMantenimiento(actualizada.getTecnicoMantenimiento());
            return fallaService.registrarFalla(falla);
        }).orElseThrow(() -> new RuntimeException("Falla no encontrada"));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        fallaService.eliminarFalla(id);
    }

    @GetMapping
    public List<Falla> listarTodas() {
        return fallaService.listarTodas();
    }

    @GetMapping("/por-maquina")
    public List<Falla> porMaquina(@RequestParam String nombre) {
        return fallaService.filtrarPorMaquina(nombre);
    }

    @GetMapping("/por-tecnico")
    public List<Falla> porTecnico(@RequestParam Long id) {
        return fallaService.filtrarPorTecnico(id);
    }

    @GetMapping("/por-fecha")
    public List<Falla> porFechas(@RequestParam String desde, @RequestParam String hasta) {
        LocalDateTime inicio = LocalDateTime.parse(desde);
        LocalDateTime fin = LocalDateTime.parse(hasta);
        return fallaService.filtrarPorFecha(inicio, fin);
    }

    @GetMapping("/pendientes")
    public List<Falla> listarPendientes() {
        return fallaService.listarTodas().stream()
                .filter(f -> f.getEstado() == EstadoFalla.PENDIENTE)
                .collect(Collectors.toList());
    }

    @GetMapping("/enProceso")
    public List<Falla> listarEnProceso() {
        return fallaService.listarTodas().stream()
                .filter(f -> f.getEstado() == EstadoFalla.EN_PROCESO)
                .collect(Collectors.toList());
    }

}

