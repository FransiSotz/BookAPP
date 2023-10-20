package com.company.intecap.apibooks.service;

import com.company.intecap.apibooks.model.Categoria;
import com.company.intecap.apibooks.dao.ICategoriaDao;
import com.company.intecap.apibooks.respose.CategoriaResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Service
public class CategoriaServiceImpl implements ICategoriaService {

    private static final Logger log = Logger.getLogger(CategoriaServiceImpl.class.getName());

    @Autowired //Inyeccion de dependencias
    private ICategoriaDao categoriaDao; //Instancia de la interface ICategoriaDao Jpa

    @Override
    @Transactional(readOnly = true) //Metodo para buscar todas las categorias de la base de datos
    public ResponseEntity<CategoriaResponseRest> listarCategorias() {
        log.info("Inicio metodo buscarCategorias()");

        CategoriaResponseRest response = new CategoriaResponseRest();

        try {
            List<Categoria> listaCategorias = (List<Categoria>) categoriaDao.findAll();
            response.getCategoriaResponse().setCategorias(listaCategorias);
            response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");

        } catch (Exception e) {
            log.info("Error al consultar categorias: " + e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "500", "Error al consultar categorias");
            return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //Retorna un error 500
        }

        return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); //Retorna un error 200
    }

    @Override
    @Transactional(readOnly = true) //Metodo para buscar una categoria por su id
    public ResponseEntity<CategoriaResponseRest> buscarCategoriaId(Long id) {
        log.info("Inicio metodo buscarCategoriaId()");

        CategoriaResponseRest response = new CategoriaResponseRest();
        List<Categoria> listaCategorias = new ArrayList<>();

        try {
            Optional<Categoria> categoria = categoriaDao.findById(id);

            if (categoria.isPresent()) {
                listaCategorias.add(categoria.get());
                response.getCategoriaResponse().setCategorias(listaCategorias);
                response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
            } else {
                response.setMetadata("Respuesta no ok", "404", "Categoria no encontrada");
                return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND); //Retorna un error 404
            }
        } catch (Exception e) {
            log.info("Error al consultar categoria: " + e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "500", "Error al consultar categoria");
            return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //Retorna un error 500
        }
        return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); //Retorna un error 200
    }

    @Override
    @Transactional
    public ResponseEntity<CategoriaResponseRest> crearCategoria(Categoria categoria) {
        log.info("Ejecutando crearCategoria()");
        CategoriaResponseRest responseCategoria = new CategoriaResponseRest();
        List<Categoria> listCategoria = new ArrayList<>();
        try {
            Categoria categoriaGuardada = categoriaDao.save(categoria);
            if (categoriaGuardada != null) {
                listCategoria.add(categoriaGuardada);
                responseCategoria.getCategoriaResponse().setCategorias(listCategoria);
                responseCategoria.setMetadata("Insercion Exitosa", "200", "Categoria Creada");

            } else {
                log.severe("No se pudo guardar la categoria");
                responseCategoria.setMetadata("Error al guardar Categoria", "500", "No se guardo la Categoria");
                return new ResponseEntity<>(responseCategoria, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.severe("Error al guardar la Categoria" + e.getMessage());
            e.getStackTrace();
            responseCategoria.setMetadata("Error al guardar la Categoria", "500", "Error al guardar la Categoria");
            return new ResponseEntity<CategoriaResponseRest>(responseCategoria, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoriaResponseRest>(responseCategoria, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoriaResponseRest> actualizarCategoria(Categoria categoria, Long id) {
        log.info("Iniciando metodo ActualizarCategoria()");
        CategoriaResponseRest responseCategoria = new CategoriaResponseRest();
        List<Categoria> listCategoria = new ArrayList<>();
        try {
            Optional<Categoria> categoriaBuscada = categoriaDao.findById(id);
            if (categoriaBuscada.isPresent()) {
                categoriaBuscada.get().setNombre(categoria.getNombre());
                categoriaBuscada.get().setDescripcion(categoria.getDescripcion());
                Categoria categoriaActualizada = categoriaDao.save(categoriaBuscada.get());
                if (categoriaActualizada != null) {
                    listCategoria.add(categoriaActualizada);
                    responseCategoria.getCategoriaResponse().setCategorias(listCategoria);
                    responseCategoria.setMetadata("Respuesta Exitosa", "200", "Categoria Actualizada");
                } else {
                    log.severe("No se pudo Actualizar la categoria");
                    responseCategoria.setMetadata("Error al Actualizar Categoria", "500", "No se Actualizar la Categoria");
                    return new ResponseEntity<>(responseCategoria, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                log.severe("No se encontro categoria con Id: " + id);
                responseCategoria.setMetadata("Respuesta No Exitosa", "404", "No se encontro la Categoria");
                return new ResponseEntity<>(responseCategoria, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            log.severe("Error al Actualizar la Categoria" + e.getMessage());
            e.getStackTrace();
            responseCategoria.setMetadata("Error al Actualizar la Categoria", "500", "Error al Actualizar la Categoria");
            return new ResponseEntity<CategoriaResponseRest>(responseCategoria, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoriaResponseRest>(responseCategoria, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoriaResponseRest> eliminarCategoria(Long id) {
        log.info("Ejecutando metodo eliminarCategoria(Long id)");
        CategoriaResponseRest categoriaResponseRest = new CategoriaResponseRest();
        try {
            Optional<Categoria> categoriaBuscada = categoriaDao.findById(id);
            if (categoriaBuscada.isPresent()) {
                categoriaDao.delete(categoriaBuscada.get());
                categoriaResponseRest.setMetadata("Respuesta Exitosa", "200", "Categoria Eliminada");
            } else {
                log.severe("No se encontro categoria con Id: " + id);
                categoriaResponseRest.setMetadata("Respuesta No Exitosa", "404", "No se encontro la Categoria");
                return new ResponseEntity<>(categoriaResponseRest, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.severe("Error al Eliminar la Categoria" + e.getMessage());
            e.getStackTrace();
            categoriaResponseRest.setMetadata("Error al Eliminar la Categoria", "500", "Error al Eliminar la Categoria");
            return new ResponseEntity<CategoriaResponseRest>(categoriaResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoriaResponseRest>(categoriaResponseRest, HttpStatus.OK);
    }
}
