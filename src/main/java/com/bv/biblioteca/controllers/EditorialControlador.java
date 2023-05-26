package com.bv.biblioteca.controllers;

import com.bv.biblioteca.exceptions.MiExcepcion;
import com.bv.biblioteca.models.Editorial;
import com.bv.biblioteca.services.EditorialServicio;
import org.hibernate.sql.model.ModelMutationLogging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

import java.util.List;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {

        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "La editorial se guardó correctamente");
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "editorial_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(Model modelo) {

        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);

        return "editorial_list.html";
    }

    @GetMapping("/actualizar/{id}")
    public String actualizar(@PathVariable String id, ModelMap modelo) {

        modelo.put("editorial", editorialServicio.getOne(id));

        return "editorial_update.html";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable String id, String nombre, ModelMap modelo) {

        try {
            editorialServicio.actualizarEditorial(id, nombre);
            return "redirect:../lista";
        } catch (MiExcepcion e){
            modelo.put("error", e.getMessage());
            return "editorial_update.html";
        }
    }

    @RequestMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {

        try {
            editorialServicio.eliminarEditorial(id);
            return "redirect:../lista";
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "redirect:../lista";
        }
    }
}
