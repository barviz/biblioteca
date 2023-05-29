package com.bv.biblioteca.controllers;

import com.bv.biblioteca.exceptions.MiExcepcion;
import com.bv.biblioteca.services.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar(){
        return "registro.html";
    }

    @PostMapping("/registro")
    public String Registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap modelo) {

        try {

            usuarioServicio.registrar(nombre, email, password, password2);
            modelo.put("exito", "Usuario registrado correctamente");

            return "index.html";

        } catch (MiExcepcion e) {

            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "registro.html";
        }


    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }
}