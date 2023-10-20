package com.company.intecap.apibooks.service;

import com.company.intecap.apibooks.model.Categoria;
import com.company.intecap.apibooks.respose.CategoriaResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICategoriaService {

    public ResponseEntity<CategoriaResponseRest> listarCategorias();

    public ResponseEntity<CategoriaResponseRest> buscarCategoriaId(Long id);

    public ResponseEntity<CategoriaResponseRest> crearCategoria(Categoria categoria);

    public ResponseEntity<CategoriaResponseRest> actualizarCategoria(Categoria categoria, Long id);

    public ResponseEntity<CategoriaResponseRest> eliminarCategoria(Long id);
}
