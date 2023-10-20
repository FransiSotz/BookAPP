package com.company.intecap.apibooks.respose;

import com.company.intecap.apibooks.model.Categoria;

import java.util.List;

public class CategoriaResponse {

    private List<Categoria> categorias;

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}
