package com.bv.biblioteca.services;

import com.bv.biblioteca.exceptions.MiExcepcion;
import com.bv.biblioteca.models.Autor;
import com.bv.biblioteca.repositories.AutorRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre)  throws MiExcepcion {

        validar(nombre);

        Autor autor = new Autor();

        autor.setNombre(nombre);

        autorRepositorio.save(autor);

    }

    public List<Autor> listarAutores(){

        List<Autor> autores = new ArrayList<>();

        autores = autorRepositorio.findAll();

        return autores;
    }

    public Autor getOne(String id) {
        return autorRepositorio.getOne(id);
    }

    @Transactional
    public void actualizarAutor(String id, String nombre) throws MiExcepcion{

        validar(nombre);

        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();

            autor.setNombre(nombre);

            autorRepositorio.save(autor);
        }
    }

    public void validar(String nombre) throws MiExcepcion {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiExcepcion("El nombre no puede ser nulo o estar vacio");
        }
    }
}
