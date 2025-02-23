package com.alejandronieves.controller;

import com.alejandronieves.model.SubUsoAula;
import com.alejandronieves.model.UsoAula;
import com.alejandronieves.service.SubUsoAulaService;
import com.alejandronieves.service.UsoAulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/subUsoAulas")
public class SubUsoAulaController {

    @Autowired
    private SubUsoAulaService subUsoAulaService;
    
    @Autowired
    private UsoAulaService usoAulaService; // para listar los UsoAula disponibles

    // Listar subUsoAulas
    @GetMapping
    public String listarSubUsoAulas(Model model) {
        List<SubUsoAula> subUsoAulas = subUsoAulaService.listAll();
        model.addAttribute("subUsoAulas", subUsoAulas);
        return "subUsoAula_list";
    }

    // Formulario para crear un nuevo subUsoAula
    @GetMapping("/nuevo")
    public String nuevoSubUsoAula(Model model) {
        model.addAttribute("subUsoAula", new SubUsoAula());
        // Cargamos la lista de usoAulas para que el usuario seleccione
        model.addAttribute("usoAulas", usoAulaService.listAll());
        return "subUsoAula_form";
    }

    // Procesar formulario de creación o actualización
    @PostMapping
    public String guardarSubUsoAula(@ModelAttribute("subUsoAula") SubUsoAula subUsoAula, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Si hay errores, recargamos la lista de usoAulas
            model.addAttribute("usoAulas", usoAulaService.listAll());
            return "subUsoAula_form";
        }
        subUsoAulaService.save(subUsoAula);
        return "redirect:/subUsoAulas";
    }

    // Mostrar formulario para editar un subUsoAula existente
    @GetMapping("/editar/{id}")
    public String editarSubUsoAula(@PathVariable("id") Long id, Model model) {
        SubUsoAula subUsoAula = subUsoAulaService.findById(id);
        if (subUsoAula == null) {
            return "redirect:/subUsoAulas";
        }
        model.addAttribute("subUsoAula", subUsoAula);
        // De nuevo, cargamos usoAulas para el select
        model.addAttribute("usoAulas", usoAulaService.listAll());
        return "subUsoAula_form";
    }

    // Borrar un subUsoAula
    @GetMapping("/borrar/{id}")
    public String borrarSubUsoAula(@PathVariable("id") Long id) {
        subUsoAulaService.deleteById(id);
        return "redirect:/subUsoAulas";
    }
}
