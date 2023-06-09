package com.bv.biblioteca.services;


import com.bv.biblioteca.exceptions.MiExcepcion;
import com.bv.biblioteca.models.Editorial;
import com.bv.biblioteca.repositories.EditorialRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws MiExcepcion {

        validar(nombre);

        nombre = nombre.toUpperCase();

        Editorial editorial = new Editorial();

        editorial.setNombre(nombre);

        editorialRepositorio.save(editorial);

    }

    public List<Editorial> listarEditoriales(){

        List<Editorial> editoriales = new ArrayList<>();

        editoriales = editorialRepositorio.findAll();

        return editoriales;
    }

    public Editorial getOne(String id) {
        return editorialRepositorio.getOne(id);
    }

    @Transactional
    public void actualizarEditorial(String id, String nombre) throws MiExcepcion {

        validar(nombre);

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Editorial editorial = respuesta.get();

            editorial.setNombre(nombre);

            editorialRepositorio.save(editorial);

        }

    }

    @Transactional
    public void eliminarEditorial(String id) throws MiExcepcion {

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if (respuesta.isPresent()) {
            editorialRepositorio.deleteById(id);
        } else {
            throw new MiExcepcion("No se encontró la editorial");
        }
    }

    public void validar(String nombre) throws MiExcepcion {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiExcepcion("El nombre no puede ser nulo o estar vacio");
        }
    }
}