package com.bv.biblioteca.services;

import com.bv.biblioteca.models.Autor;
import com.bv.biblioteca.models.Editorial;
import com.bv.biblioteca.models.Libro;
import com.bv.biblioteca.repositories.AutorRepositorio;
import com.bv.biblioteca.repositories.EditorialRepositorio;
import com.bv.biblioteca.repositories.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) {

        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();

        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }
}
