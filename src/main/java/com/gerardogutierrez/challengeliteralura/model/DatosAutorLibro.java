package com.gerardogutierrez.challengeliteralura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosAutorLibro(@JsonAlias("name") String nombre,
                              @JsonAlias("birth_year") Integer fechaNacimiento,
                              @JsonAlias("death_year") Integer fechaMuerte) {
}
