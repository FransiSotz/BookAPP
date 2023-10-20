package com.company.intecap.apibooks.dao;

import com.company.intecap.apibooks.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaDao extends JpaRepository<Categoria, Long> {
}
