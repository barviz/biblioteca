/*
Esta clase tiene la responsabilidad de llevar adelante las funcionalidades necesarias
para administrar libros (crear, actualizar, consultar y eliminar). (toda la lógica del negocio)
 */

package com.bv.biblioteca.services;

import com.bv.biblioteca.exceptions.MiExcepcion;
import com.bv.biblioteca.models.Autor;
import com.bv.biblioteca.models.Editorial;
import com.bv.biblioteca.models.Libro;
import com.bv.biblioteca.repositories.AutorRepositorio;
import com.bv.biblioteca.repositories.EditorialRepositorio;
import com.bv.biblioteca.repositories.LibroRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//va a ejecutar las funcionalidades para que la aplicación cumpla las peticiones que se le pida
@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiExcepcion {

        //se va transformar los datos recibidos del formulario en una entidad de nuestro sistema

        //antes de persistir hay que validar que los atributos que estén llegando sean válidos
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        titulo = titulo.toUpperCase();

        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();

        /*cuando creamos este objeto también se generá un id
        porque cuando se mapeó los atributos de la entidad se puso que
        el id del objeto sea generado con una estrategia uuid*/
        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        //el repositorio va a guardar/almacenar en la base de datos
        libroRepositorio.save(libro);
    }

    public List<Libro> listarLibros(){

        List<Libro> libros = new ArrayList<>();

        libros = libroRepositorio.findAll();

        return libros;

    }

    public Libro getOne(Long isbn) {
        return libroRepositorio.getOne(isbn);
    }


    @Transactional
    public void actualizarLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiExcepcion {

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        //la clase optional permite ver si como respuesta al id me trae el objeto buscado, entonces lo modifico sino devuelve una excepción
        Optional<Libro> respuestaLibro = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if (respuestaAutor.isPresent()) {
            autor = respuestaAutor.get();
        }

        if (respuestaEditorial.isPresent()) {
            editorial = respuestaEditorial.get();
        }

        if (respuestaLibro.isPresent()) {

            Libro libro = respuestaLibro.get();

            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);

            //se llama al repositorio para guardar al objeto con sus modificaciiones, lo actualiza
            libroRepositorio.save(libro);
        }

    }

    @Transactional
    public void eliminarLibro(Long isbn) throws MiExcepcion {

        Optional<Libro> respuesta = libroRepositorio.findById(isbn);

        if (respuesta.isPresent()) {
            libroRepositorio.deleteById(isbn);
        } else {
            throw new MiExcepcion("No se encontró el libro");
        }
    }

    //método para no repetir la lógica
    /*si alguno de los datos que se ingresaron no es válido se dispara el error
    y no se crea la entidad, no se setean los atributos y no se persiste en la base de datos*/
    private void validar(Long isbn, String titulo,Integer ejemplares,  String idEditorial, String idAutor) throws MiExcepcion {

        if (isbn == null) {
            throw new MiExcepcion("El isbn no puede ser nulo");
        }

        if (titulo.isEmpty() || titulo == null) {
            throw new MiExcepcion("El titulo no puede ser nulo o estar vacio");
        }

        if (ejemplares == null) {
            throw new MiExcepcion("El numero de ejemplares no puede ser nulo");
        }

        if (idAutor.isEmpty() || idAutor == null) {
            throw new MiExcepcion("El autor no puede ser nulo o estar vacio");
        }

        if (idEditorial.isEmpty() || idEditorial == null) {
            throw new MiExcepcion("La editorial no puede ser nula o estar vacia");
        }
    }


}
