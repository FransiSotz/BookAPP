package com.company.intecap.apibooks.respose;

import com.company.intecap.apibooks.model.Libro;

import java.util.List;

public class LibroResponse {
    private List<Libro> libros;

    public List<Libro> getLibros() {
        // getLibros: obtiene la información de la respuesta.
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        // setLibros: establece la información de la respuesta.
        this.libros = libros;
    }
}
