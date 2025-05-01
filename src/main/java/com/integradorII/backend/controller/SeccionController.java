package com.integradorII.backend.controller;

import lombok.RequiredArgsConstructor;
import com.integradorII.backend.model.Seccion;
import org.springframework.web.bind.annotation.*;
import com.integradorII.backend.repository.SeccionRepository;

import java.util.List;

@RestController
@RequestMapping("/secciones")
@RequiredArgsConstructor
@CrossOrigin
public class SeccionController {

    private final SeccionRepository seccionRepository;

    @GetMapping
    public List<Seccion> listar() {
        return seccionRepository.findAll();
    }

    @PostMapping
    public Seccion crear(@RequestBody Seccion seccion) {
        return seccionRepository.save(seccion);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        seccionRepository.deleteById(id);
    }
}

