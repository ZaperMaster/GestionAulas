package com.alejandronieves.controller;

import com.alejandronieves.model.Edificio;
import com.alejandronieves.service.EdificioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/edificios")
public class EdificioController {

    @Autowired
    private EdificioService edificioService;

    // Lista todos los edificios
    @GetMapping
    public String listarEdificios(Model model) {
        model.addAttribute("edificios", edificioService.listAll());
        return "edificios_list";
    }

    // Muestra el formulario para crear un nuevo edificio
    @GetMapping("/nuevo")
    public String nuevoEdificio(Model model) {
        model.addAttribute("edificio", new Edificio());
        return "edificio_form";
    }

    // Procesa el formulario para guardar un edificio (creación o actualización)
    @PostMapping
    public String guardarEdificio(@ModelAttribute("edificio") Edificio edificio, BindingResult result) {
        if (result.hasErrors()) {
            return "edificio_form";
        }
        edificioService.save(edificio);
        return "redirect:/edificios";
    }

    // Muestra el formulario para editar un edificio existente
    @GetMapping("/editar/{id}")
    public String editarEdificio(@PathVariable("id") Long id, Model model) {
        Edificio edificio = edificioService.findById(id);
        if (edificio == null) {
            return "redirect:/edificios";
        }
        model.addAttribute("edificio", edificio);
        return "edificio_form"; // Reutiliza el mismo formulario para edición
    }

    // Borrar un edificio
    @GetMapping("/borrar/{id}")
    public String borrarEdificio(@PathVariable("id") Long id) {
        edificioService.deleteById(id);
        return "redirect:/edificios";
    }
}
