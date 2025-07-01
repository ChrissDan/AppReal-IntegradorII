package com.integradorII.backend.controller;

import lombok.RequiredArgsConstructor;
import com.integradorII.backend.model.Maquina;
import org.springframework.web.bind.annotation.*;
import com.integradorII.backend.repository.MaquinaRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/maquinas")
@RequiredArgsConstructor
@CrossOrigin
public class MaquinaController {

    private final MaquinaRepository maquinaRepository;

    @GetMapping
    public List<Maquina> listar() {
        return maquinaRepository.findAll();
    }

    @GetMapping("/por-seccion/{id}")
    public List<Maquina> porSeccion(@PathVariable Long id) {
        return maquinaRepository.findBySeccionId(id);
    }

    @GetMapping("/{id}")
    public Optional<Maquina> buscarPorId(@PathVariable Long id) {
        return maquinaRepository.findById(id);
    }

    @PostMapping
    public Maquina crear(@RequestBody Maquina maquina) {
        return maquinaRepository.save(maquina);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        maquinaRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Maquina actualizar(@PathVariable Long id, @RequestBody Maquina maquinaActualizada) {
        return maquinaRepository.findById(id).map(maquina -> {
            maquina.setNombre(maquinaActualizada.getNombre());
            maquina.setSeccion(maquinaActualizada.getSeccion());
            return maquinaRepository.save(maquina);
        }).orElseThrow(() -> new RuntimeException("Máquina no encontrada"));
    }
}

