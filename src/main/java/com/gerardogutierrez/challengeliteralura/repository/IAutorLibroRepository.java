package com.gerardogutierrez.challengeliteralura.repository;

import com.gerardogutierrez.challengeliteralura.model.AutorLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IAutorLibroRepository extends JpaRepository<AutorLibro, Long> {
    Optional<AutorLibro> findByNombre(String nombre);

    @Query("SELECT a FROM AutorLibro a WHERE a.fechaNacimiento <= :año AND a.fechaMuerte >= :año")
    List<AutorLibro> listaAutoresVivosPorAño(Integer año);
}
