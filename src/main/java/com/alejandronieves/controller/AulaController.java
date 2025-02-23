package com.alejandronieves.controller;

import com.alejandronieves.model.Aula;
import com.alejandronieves.service.AulaService;
import com.alejandronieves.service.EdificioService;
import com.alejandronieves.service.PlantaService;
import com.alejandronieves.service.TipoAulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/aulas")
public class AulaController {

    @Autowired
    private AulaService aulaService;
    
    @Autowired
    private EdificioService edificioService;
    
    @Autowired
    private PlantaService plantaService;
    
    @Autowired
    private TipoAulaService tipoAulaService;
    
    // Método para listar todas las aulas
    @GetMapping
    public String listarAulas(Model model) {
        model.addAttribute("aulas", aulaService.listAll());
        return "aula_list"; // Asegúrate de que exista la plantilla aula_list.html en src/main/resources/templates/
    }
    
    // Mostrar formulario para crear una nueva aula
    @GetMapping("/nuevo")
    public String nuevaAula(Model model) {
        model.addAttribute("aula", new Aula());
        model.addAttribute("edificios", edificioService.listAll());
        // Puedes cargar inicialmente todas las plantas o dejar la lista vacía (se actualizará vía AJAX)
        model.addAttribute("plantas", plantaService.listAll());
        model.addAttribute("tipoAulas", tipoAulaService.listAll());
        return "aula_form"; // Asegúrate de que exista la plantilla aula_form.html
    }
    
    // Método para procesar el formulario de creación/edición de aula (maneja peticiones POST)
    @PostMapping
    public String guardarAula(@ModelAttribute("aula") Aula aula, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("edificios", edificioService.listAll());
            model.addAttribute("plantas", plantaService.listAll());
            model.addAttribute("tipoAulas", tipoAulaService.listAll());
            return "aula_form";
        }
        aulaService.save(aula);
        return "redirect:/aulas";
    }
    
    // Método para mostrar el formulario de edición de una aula existente
    @GetMapping("/editar/{id}")
    public String editarAula(@PathVariable("id") Long id, Model model) {
        Aula aula = aulaService.findById(id);
        if (aula == null) {
            return "redirect:/aulas";
        }
        model.addAttribute("aula", aula);
        model.addAttribute("edificios", edificioService.listAll());
        // En edición, podrías cargar las plantas relacionadas con el edificio del aula
        if (aula.getPlanta() != null && aula.getPlanta().getEdificio() != null) {
            model.addAttribute("plantas", plantaService.findByEdificio(aula.getPlanta().getEdificio().getId()));
        } else {
            model.addAttribute("plantas", plantaService.listAll());
        }
        model.addAttribute("tipoAulas", tipoAulaService.listAll());
        return "aula_form";
    }
    
    // Método para borrar una aula
    @GetMapping("/borrar/{id}")
    public String borrarAula(@PathVariable("id") Long id) {
        aulaService.deleteById(id);
        return "redirect:/aulas";
    }
    
    // Endpoint AJAX para obtener plantas por edificio
    @GetMapping("/plantasPorEdificio")
    @ResponseBody
    public java.util.List<com.alejandronieves.model.Planta> getPlantasPorEdificio(@RequestParam("edificioId") Long edificioId) {
        return plantaService.findByEdificio(edificioId);
    }
}
