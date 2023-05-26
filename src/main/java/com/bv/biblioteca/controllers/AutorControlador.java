package com.bv.biblioteca.controllers;

import com.bv.biblioteca.exceptions.MiExcepcion;
import com.bv.biblioteca.models.Autor;
import com.bv.biblioteca.services.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "autor_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {

        try {
            autorServicio.crearAutor(nombre);
            modelo.put("exito", "El autor se guardó correctamente");
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "autor_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {

        List<Autor> autores = autorServicio.listarAutores();
        modelo.addAttribute("autores", autores);

        return "autor_list.html";
    }

    @GetMapping("/actualizar/{id}")
    public String actualizar(@PathVariable String id, ModelMap modelo) {

        modelo.put("autor", autorServicio.getOne(id));

        return "autor_update.html";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable String id, String nombre, ModelMap modelo) {

        try {
            autorServicio.actualizarAutor(id, nombre);
            return "redirect:../lista";
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "autor_update.html";
        }
    }

    @RequestMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {

        try {
            autorServicio.eliminarAutor(id);
            return "redirect:../lista";
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "redirect:../lista";
        }
    }
}
