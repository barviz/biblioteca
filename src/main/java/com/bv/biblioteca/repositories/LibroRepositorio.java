package com.bv.biblioteca.repositories;

import com.bv.biblioteca.models.Autor;
import com.bv.biblioteca.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Long> {

    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public Libro buscarPorTitulo(@Param("titulo"), String titulo);

    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :nombre")
    public Autor buscarPorAutor(@Param("nombre"), String nombre);
}
