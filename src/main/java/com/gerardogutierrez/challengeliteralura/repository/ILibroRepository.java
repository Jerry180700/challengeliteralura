package com.gerardogutierrez.challengeliteralura.repository;

import com.gerardogutierrez.challengeliteralura.model.Idioma;
import com.gerardogutierrez.challengeliteralura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ILibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByLenguaje(Idioma idioma);

    Optional<Libro> findByTitulo(String titulo);

    @Query("SELECT l FROM Libro l ORDER BY l.numeroDescargas DESC LIMIT 5")
    List<Libro> top5LibrosMasDescargados();
}
