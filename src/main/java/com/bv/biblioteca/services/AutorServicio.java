package com.bv.biblioteca.services;

import com.bv.biblioteca.models.Autor;
import com.bv.biblioteca.repositories.AutorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    public void crearAutor(String nombre){

        Autor autor = new Autor();

        autor.setNombre(nombre);

        autorRepositorio.save(autor);

    }
}
