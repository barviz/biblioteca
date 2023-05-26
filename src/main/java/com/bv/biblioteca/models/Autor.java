package com.bv.biblioteca.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Autor {

    @Id //toda entidad persistente debe tener un identificador unívoco de ese objeto/entidad
    @GeneratedValue(generator = "uuid") //se va a crear/guardar para cada objeto en la base de datos
    @GenericGenerator(name = "uuid", strategy = "uuid2") //genera una cadena de texto que no se va a repetir nunca
    private String id;
    private String nombre;

    public Autor() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
