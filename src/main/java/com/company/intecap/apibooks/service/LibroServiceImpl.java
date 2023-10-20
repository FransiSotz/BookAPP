package com.company.intecap.apibooks.service;

import com.company.intecap.apibooks.model.Libro;
import com.company.intecap.apibooks.dao.ILibroDao;
import com.company.intecap.apibooks.respose.LibroResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service //anotacion para indicar que es un servicio
public class LibroServiceImpl implements ILibroService {

    private static final Logger log = Logger.getLogger(LibroServiceImpl.class.getName());

    @Autowired //@Autowired: inyecta el repositorio de libros para poder utilizarlo en este servicio
    private ILibroDao libroDao; //variable de tipo ILibroDao para acceder a los metodos de la interface ILibroDao

    @Override
    @Transactional(readOnly = true) //anotacion para indicar que es un metodo de solo lectura
    public ResponseEntity<LibroResponseRest> listarLibros() {
        log.info("inicio metodo buscarLibros");

        LibroResponseRest response = new LibroResponseRest(); //creacion de un objeto de tipo LibroResponseRest

        try {
            List<Libro> listLibros = (List<Libro>) libroDao.findAll(); //creacion de una lista de libros para guardar los libros que se encuentren en la base de datos
            response.getLibroResponse().setLibros(listLibros); //se asigna la lista de libros al objeto de tipo LibroResponseRest
            response.setMetadata("Respuesta exitosa", "200", "Lista de Libros"); //se asigna el mensaje de respuesta al objeto de tipo LibroResponseRest
        } catch (Exception e) {
            log.severe("Error en el metodo buscarLibros: " + e.getMessage()); //se muestra un mensaje de error en caso de que ocurra una excepcion
            e.getStackTrace();
            response.setMetadata("Respuesta no exitosa", "500", "Error al consultar la lista de libros"); //se asigna el mensaje de respuesta al objeto de tipo LibroResponseRest

            return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo LibroResponseRest y el codigo de estado 500

        }
        return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo LibroResponseRest y el codigo de estado 200
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<LibroResponseRest> buscarLibroPorId(Long id) {
        log.info("inicio metodo buscarLibroPorId");

        LibroResponseRest response = new LibroResponseRest(); //creacion de un objeto de tipo LibroResponseRest
        List<Libro> list = new ArrayList<>();

        try {
            Optional<Libro> libro = libroDao.findById(id); //se busca un libro por su id
            if (libro.isPresent()) {
                list.add(libro.get()); //se agrega el libro a la lista
                response.getLibroResponse().setLibros(list); //se asigna la lista de libros al objeto de tipo LibroResponseRest
                response.setMetadata("Respuesta exitosa", "200", "Libro encontrado"); //se asigna el mensaje de respuesta al objeto de tipo LibroResponseRest
            } else {
                log.severe("No se encontro el libro con el Id: " + id); //se muestra un mensaje de error en caso de que no se encuentre el libro
                response.setMetadata("Respuesta no exitosa", "404", "No se encontro el libro con el Id: " + id); //se asigna el mensaje de respuesta al objeto de tipo LibroResponseRest
                return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo LibroResponseRest y el codigo de estado 404
            }
        } catch (Exception e) {
            log.severe("Errror al buscar el libro : " + e.getMessage());
            e.getStackTrace();
            response.setMetadata("Error al buscar el libro", "500", "Error al consultar el libro"); //se asigna el mensaje de respuesta al objeto de tipo LibroResponseRest
            return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo LibroResponseRest y el codigo de estado 500
        }

        return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo LibroResponseRest y el codigo de estado 200
    }

    @Override
    @Transactional
    public ResponseEntity<LibroResponseRest> crearLibro(Libro libro) {
        log.info("Iniciando el metodo metodo crear()  ");

        LibroResponseRest response = new LibroResponseRest(); //creacion de un objeto de tipo LibroResponseRest
        List<Libro> list = new ArrayList<>();

        try {
            Libro libroGuardado = libroDao.save(libro); //se guarda el libro en la base de datos
            if (libroGuardado != null) {
                list.add(libroGuardado); //se agrega el libro a la lista
                response.getLibroResponse().setLibros(list); //se asigna la lista de libros al objeto de tipo LibroResponseRest
                response.setMetadata("Respuesta exitosa", "200", "Libro creado"); //se asigna el mensaje de respuesta al objeto de tipo LibroResponseRest
            } else {
                log.severe("No se pudo guardar el libro"); //se muestra un mensaje de error en caso de que no se pueda guardar el libro
                response.setMetadata("Error al guardar el libro", "500", "No se pudo guardar el libro"); //se asigna el mensaje de respuesta al objeto de tipo LibroResponseRest
                return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo LibroResponseRest y el codigo de estado 500
            }

        } catch (Exception e) {
            log.severe("Error al guardar el libro: " + e.getMessage()); //se muestra un mensaje de error en caso de que ocurra una excepcion
            e.getStackTrace();
            response.setMetadata("Error al guardar el libro", "500", "Error al guardar el libro"); //se asigna el mensaje de respuesta al objeto de tipo LibroResponseRest
            return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo LibroResponseRest y el codigo de estado 500
        }

        return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo LibroResponseRest y el codigo de estado 200
    }

    @Override
    @Transactional
    //commmit y rollback: si ocurre un error en la transaccion se hace un rollback y si no se hace un commit
    public ResponseEntity<LibroResponseRest> actualizarLibro(Libro libro, Long id) {
        log.info("Iniciando el metodo actualizar()  ");

        LibroResponseRest response = new LibroResponseRest(); //creacion de un objeto de tipo LibroResponseRest
        List<Libro> list = new ArrayList<>();

        try {
            Optional<Libro> libroBuscado = libroDao.findById(id); //se busca un libro por su id

            if (libroBuscado.isPresent()) {
                libroBuscado.get().setNombre(libro.getNombre()); //se asigna el nombre del libro que se quiere actualizar
                libroBuscado.get().setDescripcion(libro.getDescripcion()); //se asigna la descripcion del libro que se quiere actualizar

                Libro libroActualizado = libroDao.save(libroBuscado.get()); //se actualiza el libro en la base de datos

                if (libroActualizado != null) { // si el libro se actualizo correctamente
                    list.add(libroActualizado); //se agrega el libro a la lista
                    response.getLibroResponse().setLibros(list); //se asigna la lista de libros al objeto de tipo LibroResponseRest
                    response.setMetadata("Respuesta exitosa", "200", "Libro actualizado"); //se asigna el mensaje de respuesta al objeto de tipo LibroResponseRes
                } else {
                    log.severe("No se pudo actualizar el libro"); //se muestra un mensaje de error en caso de que no se pueda actualizar el libro
                    response.setMetadata("Error al actualizar el libro", "500", "No se pudo actualizar el libro"); //se asigna el mensaje de respuesta al objeto de tipo LibroResponseRest
                    return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo LibroResponseRest y el codigo de estado 500
                }
            } else {
                log.severe("No se encontro el libro con el Id: " + id); //se muestra un mensaje de error en caso de que no se encuentre el libro
                response.setMetadata("Respuesta no exitosa", "404", "No se encontro el libro con el Id: " + id); //se asigna el mensaje de respuesta al objeto de tipo LibroResponseRest
                return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo LibroResponseRest y el codigo de estado 404
            }
        } catch (Exception e) {
            log.severe("Error al actualizar el libro: " + e.getMessage()); //se muestra un mensaje de error en caso de que ocurra una excepcion
            e.getStackTrace();
            response.setMetadata("Error al actualizar el libro", "500", "Error al actualizar el libro"); //se asigna el mensaje de respuesta al objeto de tipo LibroResponseRest
            return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo LibroResponseRest y el codigo de estado 500
        }
        return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo LibroResponseRest y el codigo de estado 200
    }

    @Override
    @Transactional
    public ResponseEntity<LibroResponseRest> eliminarLibro(Long id) {
        log.info("Iniciando el metodo eliminar()  ");

        LibroResponseRest response = new LibroResponseRest(); //creacion de un objeto de tipo LibroResponseRest

        try {
            Optional<Libro> libroBuscado = libroDao.findById(id); //se busca un libro por su id

            if (libroBuscado.isPresent()) {
                libroDao.delete(libroBuscado.get()); //se elimina el libro de la base de datos
                response.setMetadata("Respuesta exitosa", "200", "Libro eliminado"); //se asigna el mensaje de respuesta al objeto de tipo LibroResponseRest
            } else {
                log.severe("No se encontro el libro con el Id: " + id); //se muestra un mensaje de error en caso de que no se encuentre el libro
                response.setMetadata("Respuesta no exitosa", "404", "No se encontro el libro con el Id: " + id); //se asigna el mensaje de respuesta al objeto de tipo LibroResponseRest
                return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo LibroResponseRest y el codigo de estado 404
            }
        } catch (Exception e) {
            log.severe("Error al eliminar el libro: " + e.getMessage()); //se muestra un mensaje de error en caso de que ocurra una excepcion
            e.getStackTrace();
            response.setMetadata("Error al eliminar el libro", "500", "Error al eliminar el libro"); //se asigna el mensaje de respuesta al objeto de tipo LibroResponseRest
            return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo LibroResponseRest y el codigo de estado 500
        }
        return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK); //se retorna un objeto de tipo ResponseEntity con el objeto de tipo LibroResponseRest y el codigo de estado 200
    }

}
