package com.integradorII.backend.controller;

import lombok.RequiredArgsConstructor;
import com.integradorII.backend.model.Maquina;
import org.springframework.web.bind.annotation.*;
import com.integradorII.backend.repository.MaquinaRepository;

import java.util.List;

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

    @PostMapping
    public Maquina crear(@RequestBody Maquina maquina) {
        return maquinaRepository.save(maquina);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        maquinaRepository.deleteById(id);
    }
}

