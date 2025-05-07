package com.pe.cibertec.CiberMatch.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pe.cibertec.CiberMatch.model.Movie;

public interface PeliculaRepository extends JpaRepository<Movie, Integer> {
    // Puedes agregar métodos personalizados si necesitas búsquedas específicas
}