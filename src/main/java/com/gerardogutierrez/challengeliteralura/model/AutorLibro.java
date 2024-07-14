package com.gerardogutierrez.challengeliteralura.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "autores")
public class AutorLibro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaMuerte;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libro;

    public AutorLibro(){}

    public AutorLibro(DatosAutorLibro datosAutorLibro){
        this.nombre = datosAutorLibro.nombre();
        this.fechaNacimiento = datosAutorLibro.fechaNacimiento();
        this.fechaMuerte = datosAutorLibro.fechaMuerte();
    }

    @Override
    public String toString() {
        StringBuilder librosString = new StringBuilder();
        librosString.append("Libros: ");

        for(int i = 0; i < libro.size() ; i++) {
            librosString.append(libro.get(i).getTitulo());
            if (i < libro.size() - 1 ){
                librosString.append(", ");
            }
        }
        return String.format("*-*-*-*-*-* Autor *-*-*-*-*-*%nNombre:" +
                " %s%n%s%nFecha de Nacimiento: %s%nFecha de Muerte:" +
                " %s%n*-*-*-*-*-*-*-*-*-*-*-*-*-*%n",nombre,librosString.toString(),fechaNacimiento,fechaMuerte);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(Integer fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    public List<Libro> getLibro() {
        return libro;
    }

    public void setLibro(List<Libro> libro) {
        this.libro = libro;
    }

}
