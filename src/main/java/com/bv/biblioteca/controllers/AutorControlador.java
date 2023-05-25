package com.bv.biblioteca.controllers;

import com.bv.biblioteca.exceptions.MiExcepcion;
import com.bv.biblioteca.services.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String registro(@RequestParam String nombre) {

        try {
            autorServicio.crearAutor(nombre);
        } catch (MiExcepcion e) {
            throw new RuntimeException(e);
        }
        return "index.html";
    }
}
