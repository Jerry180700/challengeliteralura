package com.gerardogutierrez.challengeliteralura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;
    @ManyToOne
    private AutorLibro autor;
    @Enumerated(EnumType.STRING)
    private Idioma lenguaje;
    private Integer numeroDescargas;

    public Libro(){}

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.lenguaje = Idioma.fromString(datosLibro.idiomas().toString().split(",")[0].trim());
        this.numeroDescargas = datosLibro.numeroDescargas();
    }

    @Override
    public String toString() {
        String nombreAutor = (autor != null) ? autor.getNombre() : "Autor desconocido";
        return String.format("*-*-*-*-*-* Libro *-*-*-*-*-*%nTítulo:" +
                " %s%nAutor: %s%nIdioma: %s%nNúmero de Descargas:" +
                " %d%n*-*-*-*-*-*-*-*-*-*-*-*-*-*%n",titulo,nombreAutor,lenguaje,numeroDescargas);
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public AutorLibro getAutor() {
        return autor;
    }

    public void setAutor(AutorLibro autor) {
        this.autor = autor;
    }

    public Idioma getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(Idioma lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

}
