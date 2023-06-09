package com.bv.biblioteca.controllers;

import com.bv.biblioteca.exceptions.MiExcepcion;
import com.bv.biblioteca.models.Autor;
import com.bv.biblioteca.models.Editorial;
import com.bv.biblioteca.models.Libro;
import com.bv.biblioteca.services.AutorServicio;
import com.bv.biblioteca.services.EditorialServicio;
import com.bv.biblioteca.services.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {

        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo, @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor, @RequestParam String idEditorial, ModelMap modelo) {

        try {

            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            modelo.put("exito", "El libro se guardó correctamente");

        } catch (MiExcepcion e) {

            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);

            modelo.put("error", e.getMessage());

            return "libro_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {

        List<Libro> libros = libroServicio.listarLibros();
        modelo.addAttribute("libros", libros);

        return "libro_list.html";
    }

    @GetMapping("/actualizar/{isbn}")
    public String actualizar(@PathVariable Long isbn, ModelMap modelo) {

        modelo.put("libro", libroServicio.getOne(isbn));

        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_update.html";
    }

    @PostMapping("/actualizar/{isbn}")
    public String actualizar(@PathVariable Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial, ModelMap modelo) {

        try {

            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);

            libroServicio.actualizarLibro(isbn, titulo, ejemplares, idAutor, idEditorial);

            return "redirect:../lista";

        } catch (MiExcepcion e) {

            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();

            modelo.put("error", e.getMessage());

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);

            return "libro_update.html";
        }
    }

    @RequestMapping("/eliminar/{isbn}")
    public String eliminar(@PathVariable Long isbn, ModelMap modelo) {

        try {
            libroServicio.eliminarLibro(isbn);
            return "redirect:../lista";
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "redirect:../lista";
        }
    }
}
