package com.gerardogutierrez.challengeliteralura.model;

public enum Idioma {
    en("[en]", "Inglés"),
    es("[es]", "Español"),
    fr("[fr]", "Francés"),
    pt("[pt]", "Portugués");

    private String idiomasEnGutendex;
    private String idiomasEnEspanol;
    Idioma(String idiomasEnGutendex, String idiomasEnEspanol){
        this.idiomasEnGutendex = idiomasEnGutendex;
        this.idiomasEnEspanol = idiomasEnEspanol;
    }

    public static Idioma fromString(String text){
        for (Idioma idioma : Idioma.values()){
            if (idioma.idiomasEnGutendex.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("No se encontro el idioma solicitado: " + text);
    }

    public static Idioma fromEspanol(String text){
        for (Idioma idioma : Idioma.values()){
            if (idioma.idiomasEnEspanol.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("No se encontro el idioma solicitado: " + text);
    }

    public String getIdiomaGutendex() {
        return idiomasEnGutendex;
    }

    public String getIdiomaEspanol() {
        return idiomasEnEspanol;
    }
}
