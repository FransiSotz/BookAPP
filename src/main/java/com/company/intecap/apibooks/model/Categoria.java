package com.company.intecap.apibooks.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity  // indica que es una entidad
@Table(name = "categoria") // nombre de la tabla en la base de datos de JPA
public class Categoria implements Serializable { // Serializable: sera una clase bean, para que pueda ser convertida a JSON
    private static final long serialVersionUID = 1L; // identificador unico de la clase autogenerado

    @Id // jakarta.persistence java persistence api (JPA) primera opcion
    @GeneratedValue(strategy = GenerationType.IDENTITY) // // GeneratedValue: indica que el id es autoincrementable
    private Long id;
    private String nombre;
    private String descripcion;
}
