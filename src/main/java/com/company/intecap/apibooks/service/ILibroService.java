package com.company.intecap.apibooks.service;

import com.company.intecap.apibooks.model.Libro;
import com.company.intecap.apibooks.respose.LibroResponseRest;
import org.springframework.http.ResponseEntity;

public interface ILibroService {
    //Interface para acceder a los datos de la tabla libro de la base de datos (CRUD) definicion de metodos
    //listar todos los libros
    public ResponseEntity<LibroResponseRest> listarLibros(); //metodo para buscar todos los libros de la base de datos

    //buscar por id
     public ResponseEntity<LibroResponseRest> buscarLibroPorId(Long id); //metodo para buscar un libro por su id

    //guardar un libro
    public ResponseEntity<LibroResponseRest> crearLibro(Libro libro);

    //actualizar un libro

    public ResponseEntity<LibroResponseRest> actualizarLibro(Libro libro, Long id);

    //eliminar un libro
    public ResponseEntity<LibroResponseRest> eliminarLibro(Long id);


}
