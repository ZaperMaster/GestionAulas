package com.alejandronieves.controller;

import com.alejandronieves.model.Planta;
import com.alejandronieves.service.PlantaService;
import com.alejandronieves.service.EdificioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/plantas")
public class PlantaController {

    @Autowired
    private PlantaService plantaService;
    
    @Autowired
    private EdificioService edificioService; // Para cargar edificios

    // Listar todas las plantas
    @GetMapping
    public String listarPlantas(Model model) {
        model.addAttribute("plantas", plantaService.listAll());
        return "plantas_list";
    }
    
    // Mostrar formulario para crear una nueva planta
    @GetMapping("/nuevo")
    public String nuevaPlanta(Model model) {
        model.addAttribute("planta", new Planta());
        model.addAttribute("edificios", edificioService.listAll());
        return "planta_form";
    }
    
    // Procesar el formulario (tanto para creación como para edición)
    @PostMapping
    public String guardarPlanta(@ModelAttribute("planta") Planta planta, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("edificios", edificioService.listAll());
            return "planta_form";
        }
        plantaService.save(planta);
        return "redirect:/plantas";
    }
    
    // Mostrar formulario para editar una planta existente
    @GetMapping("/editar/{id}")
    public String editarPlanta(@PathVariable("id") Long id, Model model) {
        Planta planta = plantaService.findById(id);
        if (planta == null) {
            return "redirect:/plantas";
        }
        model.addAttribute("planta", planta);
        model.addAttribute("edificios", edificioService.listAll());
        return "planta_form"; // Reutilizamos el mismo formulario para edición
    }
    
    // Borrar una planta
    @GetMapping("/borrar/{id}")
    public String borrarPlanta(@PathVariable("id") Long id) {
        plantaService.deleteById(id);
        return "redirect:/plantas";
    }
    
    // Endpoint AJAX para obtener plantas por edificio
    @GetMapping("/plantasPorEdificio")
    @ResponseBody
    public List<Planta> getPlantasPorEdificio(@RequestParam("edificioId") Long edificioId) {
        List<Planta> plantas = plantaService.findByEdificio(edificioId);
        System.out.println("Plantas encontradas para edificio " + edificioId + ": " + plantas.size());
        return plantas;
    }
}
