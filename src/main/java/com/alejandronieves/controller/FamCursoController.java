package com.alejandronieves.controller;

import com.alejandronieves.model.FamCurso;
import com.alejandronieves.model.SubUsoAula;
import com.alejandronieves.service.FamCursoService;
import com.alejandronieves.service.SubUsoAulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/famCursos")
public class FamCursoController {

    @Autowired
    private FamCursoService famCursoService;
    
    @Autowired
    private SubUsoAulaService subUsoAulaService; // Para listar los SubUsoAula disponibles

    // Listar famCursos
    @GetMapping
    public String listarFamCursos(Model model) {
        List<FamCurso> famCursos = famCursoService.listAll();
        model.addAttribute("famCursos", famCursos);
        return "famCurso_list";
    }

    // Formulario para crear un nuevo FamCurso
    @GetMapping("/nuevo")
    public String nuevoFamCurso(Model model) {
        model.addAttribute("famCurso", new FamCurso());
        // Cargamos la lista de SubUsoAula para que el usuario seleccione
        model.addAttribute("subUsoAulas", subUsoAulaService.listAll());
        return "famCurso_form";
    }

    // Procesar formulario de creación o actualización
    @PostMapping
    public String guardarFamCurso(@ModelAttribute("famCurso") FamCurso famCurso, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Si hay errores, recargamos la lista de subUsoAulas
            model.addAttribute("subUsoAulas", subUsoAulaService.listAll());
            return "famCurso_form";
        }
        famCursoService.save(famCurso);
        return "redirect:/famCursos";
    }

    // Mostrar formulario para editar un FamCurso existente
    @GetMapping("/editar/{id}")
    public String editarFamCurso(@PathVariable("id") Long id, Model model) {
        FamCurso famCurso = famCursoService.findById(id);
        if (famCurso == null) {
            return "redirect:/famCursos";
        }
        model.addAttribute("famCurso", famCurso);
        // De nuevo, cargamos subUsoAulas para el select
        model.addAttribute("subUsoAulas", subUsoAulaService.listAll());
        return "famCurso_form";
    }

    // Borrar un FamCurso
    @GetMapping("/borrar/{id}")
    public String borrarFamCurso(@PathVariable("id") Long id) {
        famCursoService.deleteById(id);
        return "redirect:/famCursos";
    }
}
