package com.gerardogutierrez.challengeliteralura.principal;

import com.gerardogutierrez.challengeliteralura.model.*;
import com.gerardogutierrez.challengeliteralura.repository.IAutorLibroRepository;
import com.gerardogutierrez.challengeliteralura.repository.ILibroRepository;
import com.gerardogutierrez.challengeliteralura.service.ConsumoApi;
import com.gerardogutierrez.challengeliteralura.service.ConvierteDatos;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private ILibroRepository repositoryLibro;
    private IAutorLibroRepository repositoryAutor;
    private List<AutorLibro> autores;
    private List<Libro> libros;

    public Principal(ILibroRepository repositoryLibro, IAutorLibroRepository repositoryAutor) {
        this.repositoryLibro = repositoryLibro;
        this.repositoryAutor = repositoryAutor;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar Libros Por Título
                    2 - Mostrar Libros Registrados
                    3 - Mostrar Autores Registrados
                    4 - Autores Vivos En Determinado Año
                    5 - Buscar Libros Por Idioma
                    6 - Top 5 Libros Más Descargados
                    
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            while (!teclado.hasNextInt()) {
                System.out.println("Número Inválido, Ingrese Un Número Válido!");
                teclado.nextLine();
            }
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarLibros();
                    break;
                case 3:
                    mostrarAutores();
                    break;
                case 4:
                    autoresVivosPorAño();
                    break;
                case 5:
                    buscarLibroPorIdioma();
                    break;
                case 6:
                    top5LibrosMasDescargados();
                    break;
                case 0:
                    System.out.println("Saliendo De La Aplicación");
                    break;
                default:
                    System.out.printf("Opción Inválida, Inténtelo Nuevamente\n");
            }
        }
    }

    private DatosBusqueda getBusqueda() {
        System.out.println("Escribe El Nombre Del Libro Que Desea Buscar: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));
        DatosBusqueda datos = conversor.obtenerDatos(json, DatosBusqueda.class);
        return datos;

    }

    private void buscarLibro() {
        DatosBusqueda datosBusqueda = getBusqueda();
        if (datosBusqueda != null && !datosBusqueda.resultado().isEmpty()) {
            DatosLibro primerLibro = datosBusqueda.resultado().get(0);

            Libro libro = new Libro(primerLibro);
            //System.out.println("*-*-*-* Libro *-*-*-*");
            System.out.println(libro);
            //System.out.println("*-*-*-*-*-*-*-*-*-*-*");

            Optional<Libro> libroExiste = repositoryLibro.findByTitulo(libro.getTitulo());
            if (libroExiste.isPresent()){
                System.out.println("\nEl Libro Ya Está Registrado\n");
            }else {

                if (!primerLibro.autor().isEmpty()) {
                    DatosAutorLibro autor = primerLibro.autor().get(0);
                    AutorLibro autor1 = new AutorLibro(autor);
                    Optional<AutorLibro> autorOptional = repositoryAutor.findByNombre(autor1.getNombre());

                    if (autorOptional.isPresent()) {
                        AutorLibro autorExiste = autorOptional.get();
                        libro.setAutor(autorExiste);
                        repositoryLibro.save(libro);
                    } else {
                        AutorLibro autorNuevo = repositoryAutor.save(autor1);
                        libro.setAutor(autorNuevo);
                        repositoryLibro.save(libro);
                    }

                    Integer numeroDescargas = libro.getNumeroDescargas() != null ? libro.getNumeroDescargas() : 0;
                    System.out.println("*-*-*-*-*-* Libro *-*-*-*-*-*");
                    System.out.printf("Título: %s%nAutor: %s%nIdioma: %s%nNúmero de Descargas: %s%n",
                            libro.getTitulo(), autor1.getNombre(), libro.getLenguaje(), libro.getNumeroDescargas());
                    System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
                } else {
                    System.out.println("Sin Autor");
                }
            }
        } else {
            System.out.println("Libro No Encontrado");
        }
    }
    private void mostrarLibros() {
        libros = repositoryLibro.findAll();
        libros.stream()
                .forEach(System.out::println);
    }

    private void mostrarAutores() {
        autores = repositoryAutor.findAll();
        autores.stream()
                .forEach(System.out::println);
    }

    private void autoresVivosPorAño() {
        System.out.println("Ingrese El Año Que Desea Buscar: ");
        var año = teclado.nextInt();
        autores = repositoryAutor.listaAutoresVivosPorAño(año);
        autores.stream()
                .forEach(System.out::println);
    }

    private List<Libro> datosBusquedaLenguaje(String idioma){
        var dato = Idioma.fromString(idioma);
        System.out.println("Idioma buscado: " + dato);

        List<Libro> libroPorIdioma = repositoryLibro.findByLenguaje(dato);
        return libroPorIdioma;
    }

    private void buscarLibroPorIdioma(){
        System.out.println("Seleccione El Idioma Que Desea Buscar: ");

        var opcion = -1;
        while (opcion != 0) {
            var opciones = """
                    1. en - Inglés
                    2. es - Español
                    3. fr - Francés
                    4. pt - Portugués
                    
                    0. Volver Al Menú Anterior
                    """;
            System.out.println(opciones);
            while (!teclado.hasNextInt()) {
                System.out.println("Formato Inválido, Ingrese Un Número Válido");
                teclado.nextLine();
            }
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    List<Libro> librosEnIngles = datosBusquedaLenguaje("[en]");
                    librosEnIngles.forEach(System.out::println);
                    break;
                case 2:
                    List<Libro> librosEnEspañol = datosBusquedaLenguaje("[es]");
                    librosEnEspañol.forEach(System.out::println);
                    break;
                case 3:
                    List<Libro> librosEnFrances = datosBusquedaLenguaje("[fr]");
                    librosEnFrances.forEach(System.out::println);
                    break;
                case 4:
                    List<Libro> librosEnPortugues = datosBusquedaLenguaje("[pt]");
                    librosEnPortugues.forEach(System.out::println);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("No Selecciono Un Idioma");
            }
        }
    }
    private void top5LibrosMasDescargados() {
        List<Libro> topLibros = repositoryLibro.top5LibrosMasDescargados();
        topLibros.forEach(System.out::println);
    }
}