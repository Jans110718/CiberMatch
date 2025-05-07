package com.pe.cibertec.CiberMatch.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pe.cibertec.CiberMatch.model.Movie;
import com.pe.cibertec.CiberMatch.repository.PeliculaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/peliculas")
@CrossOrigin(origins = "*")
public class MovieController {

    @Autowired
    private PeliculaRepository peliculaRepo;

    @PostMapping
    public Movie registrar(@RequestBody Movie peli) {
        return peliculaRepo.save(peli);
    }
    @GetMapping
    public Map<String, Object> listar(@RequestParam(defaultValue = "1") int page) {
        // Restar 1 para que la paginación empiece desde la página 0 internamente
        Pageable pageable = PageRequest.of(page - 1, 9);  // Página 1 se traduce a página 0, tamaño 9
        
        Page<Movie> peliculasPage = peliculaRepo.findAll(pageable);
        
        Map<String, Object> response = new HashMap<>();
        response.put("page", page);  // Página solicitada en base 1
        response.put("totalPages", peliculasPage.getTotalPages());
        response.put("totalElements", peliculasPage.getTotalElements());
        response.put("results", peliculasPage.getContent());

        // Construir enlaces de paginación
        String baseUrl = "/api/peliculas";  // La URL base de la API
        String previousPageUrl = (page > 1) ? baseUrl + "?page=" + (page - 1) : null;
        String nextPageUrl = (page < peliculasPage.getTotalPages()) ? baseUrl + "?page=" + (page + 1) : null;
        
        response.put("previousPage", previousPageUrl);
        response.put("nextPage", nextPageUrl);
        
        return response;
    }




    @GetMapping("/{id}")
    public Movie obtener(@PathVariable int id) {
        Optional<Movie> opt = peliculaRepo.findById(id);
        return opt.orElse(null);
    }

    @PutMapping
    public Movie actualizar(@RequestBody Movie peli) {
        return peliculaRepo.save(peli);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        peliculaRepo.deleteById(id);
    }
}
