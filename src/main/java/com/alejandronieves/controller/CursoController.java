package com.alejandronieves.controller;

import com.alejandronieves.model.Curso;
import com.alejandronieves.model.FamCurso;
import com.alejandronieves.service.CursoService;
import com.alejandronieves.service.FamCursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;
    
    @Autowired
    private FamCursoService famCursoService; // Para listar los FamCurso disponibles

    // Listar cursos
    @GetMapping
    public String listarCursos(Model model) {
        List<Curso> cursos = cursoService.listAll();
        model.addAttribute("cursos", cursos);
        return "curso_list";
    }

    // Formulario para crear un nuevo Curso
    @GetMapping("/nuevo")
    public String nuevoCurso(Model model) {
        model.addAttribute("curso", new Curso());
        // Cargamos la lista de FamCurso para que el usuario seleccione
        model.addAttribute("famCursos", famCursoService.listAll());
        return "curso_form";
    }

    // Procesar formulario de creación o actualización
    @PostMapping
    public String guardarCurso(@ModelAttribute("curso") Curso curso, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Si hay errores, recargamos la lista de famCursos
            model.addAttribute("famCursos", famCursoService.listAll());
            return "curso_form";
        }
        cursoService.save(curso);
        return "redirect:/cursos";
    }

    // Mostrar formulario para editar un Curso existente
    @GetMapping("/editar/{id}")
    public String editarCurso(@PathVariable("id") Long id, Model model) {
        Curso curso = cursoService.findById(id);
        if (curso == null) {
            return "redirect:/cursos";
        }
        model.addAttribute("curso", curso);
        // De nuevo, cargamos famCursos para el select
        model.addAttribute("famCursos", famCursoService.listAll());
        return "curso_form";
    }

    // Borrar un Curso
    @GetMapping("/borrar/{id}")
    public String borrarCurso(@PathVariable("id") Long id) {
        cursoService.deleteById(id);
        return "redirect:/cursos";
    }
}
