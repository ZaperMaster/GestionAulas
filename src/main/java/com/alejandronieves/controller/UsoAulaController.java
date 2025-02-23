package com.alejandronieves.controller;

import com.alejandronieves.model.UsoAula;
import com.alejandronieves.service.UsoAulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/usoAulas")
public class UsoAulaController {

    @Autowired
    private UsoAulaService usoAulaService;

    // Listar usos de aula
    @GetMapping
    public String listarUsoAulas(Model model) {
        List<UsoAula> usoAulas = usoAulaService.listAll();
        model.addAttribute("usoAulas", usoAulas);
        return "usoAula_list"; // Vista para listar
    }

    // Formulario para crear un nuevo uso de aula
    @GetMapping("/nuevo")
    public String nuevoUsoAula(Model model) {
        model.addAttribute("usoAula", new UsoAula());
        return "usoAula_form"; // Vista para crear
    }

    // Procesar formulario de creación o actualización
    @PostMapping
    public String guardarUsoAula(@ModelAttribute("usoAula") UsoAula usoAula, BindingResult result) {
        if (result.hasErrors()) {
            return "usoAula_form";
        }
        usoAulaService.save(usoAula);
        return "redirect:/usoAulas";
    }

    // Mostrar formulario para editar un uso de aula existente
    @GetMapping("/editar/{id}")
    public String editarUsoAula(@PathVariable("id") Long id, Model model) {
        // Se recibe un Long id para la ruta /usoAulas/editar/{id}, p.ej. /usoAulas/editar/1
        UsoAula usoAula = usoAulaService.findById(id);
        if (usoAula == null) {
            // Si no se encuentra el uso de aula, vuelve a la lista
            return "redirect:/usoAulas";
        }
        model.addAttribute("usoAula", usoAula);
        return "usoAula_form"; // Reutilizamos el mismo formulario para edición
    }

    // Borrar un uso de aula
    @GetMapping("/borrar/{id}")
    public String borrarUsoAula(@PathVariable("id") Long id) {
        usoAulaService.deleteById(id);
        return "redirect:/usoAulas";
    }
}
