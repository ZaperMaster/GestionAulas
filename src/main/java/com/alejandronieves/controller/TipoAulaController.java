package com.alejandronieves.controller;

import com.alejandronieves.model.TipoAula;
import com.alejandronieves.repository.AulaRepository;
import com.alejandronieves.service.TipoAulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/tipoaulas")
public class TipoAulaController {

    @Autowired
    private TipoAulaService tipoAulaService;
    
    // Inyectamos el repositorio de Aulas para poder contar dependencias
    @Autowired
    private AulaRepository aulaRepository;

    // Listar tipos de aula
    @GetMapping
    public String listarTipoAulas(Model model) {
        List<TipoAula> tipoAulas = tipoAulaService.listAll();
        model.addAttribute("tipoAulas", tipoAulas);
        return "tipoaula_list";
    }

    // Formulario para crear un nuevo tipo de aula
    @GetMapping("/nuevo")
    public String nuevoTipoAula(Model model) {
        model.addAttribute("tipoAula", new TipoAula());
        return "tipoaula_form";
    }

    // Procesar formulario de creación o actualización
    @PostMapping
    public String guardarTipoAula(@ModelAttribute("tipoAula") TipoAula tipoAula, BindingResult result) {
        if (result.hasErrors()) {
            return "tipoaula_form";
        }
        tipoAulaService.save(tipoAula);
        return "redirect:/tipoaulas";
    }

    // Mostrar formulario para editar un tipo de aula existente
    @GetMapping("/editar/{id}")
    public String editarTipoAula(@PathVariable("id") Long id, Model model) {
        TipoAula tipoAula = tipoAulaService.findById(id);
        if (tipoAula == null) {
            return "redirect:/tipoaulas";
        }
        model.addAttribute("tipoAula", tipoAula);
        return "tipoaula_form";
    }

    // Borrar un tipo de aula, verificando dependencias
    @GetMapping("/borrar/{id}")
    public String borrarTipoAula(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        TipoAula tipoAula = tipoAulaService.findById(id);
        if (tipoAula == null) {
            redirectAttributes.addFlashAttribute("error", "Tipo de aula no encontrado.");
            return "redirect:/tipoaulas";
        }
        long dependencias = aulaRepository.countByTipoAula(tipoAula);
        if (dependencias > 0) {
            redirectAttributes.addFlashAttribute("error", "No se puede borrar este Tipo de Aula, ya que está asociado a " + dependencias + " aula(s).");
            return "redirect:/tipoaulas";
        }
        tipoAulaService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Tipo de aula eliminado correctamente.");
        return "redirect:/tipoaulas";
    }
}
