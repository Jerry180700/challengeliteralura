package com.gerardogutierrez.challengeliteralura.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(@JsonAlias("id") Integer id,
                         @JsonAlias("title") String titulo,
                         @JsonAlias("authors") List<DatosAutorLibro> autor,
                         @JsonAlias("languages")List<String> idiomas,
                         @JsonAlias("download_count") Integer numeroDescargas) {
}
