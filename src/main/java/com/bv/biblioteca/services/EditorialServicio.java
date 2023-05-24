package com.bv.biblioteca.services;


import com.bv.biblioteca.models.Editorial;
import com.bv.biblioteca.repositories.EditorialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    public void crearEditorial(String nombre){

        Editorial editorial = new Editorial();

        editorial.setNombre(nombre);

        editorialRepositorio.save(editorial);

    }
}