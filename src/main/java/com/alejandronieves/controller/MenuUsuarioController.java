package com.alejandronieves.controller;

import com.alejandronieves.model.Aula;
import com.alejandronieves.model.Edificio;
import com.alejandronieves.model.Planta;
import com.alejandronieves.service.AulaService;
import com.alejandronieves.service.EdificioService;
import com.alejandronieves.service.PlantaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/menuUsuario")
public class MenuUsuarioController {

    @Autowired
    private EdificioService edificioService;

    @Autowired
    private PlantaService plantaService;
    
    @Autowired
    private AulaService aulaService;

    /**
     * Muestra la lista de edificios (la misma plantilla que ya tienes en menuUsuario.html).
     * GET /menuUsuario
     */
    @GetMapping
    public String listaEdificios(Model model) {
        List<Edificio> edificios = edificioService.listAll();
        model.addAttribute("edificios", edificios);
        return "menuUsuario"; // Plantilla con la "Lista de Edificios" y el botón "Ver Plantas"
    }

    /**
     * Muestra las plantas asociadas a un edificio en particular.
     * GET /menuUsuario/edificio/{id}/plantas
     */
    @GetMapping("/edificio/{id}/plantas")
    public String listarPlantasPorEdificio(@PathVariable("id") Long edificioId, Model model) {
        // 1) Buscamos el Edificio
        Edificio edificio = edificioService.findById(edificioId);
        if (edificio == null) {
            return "redirect:/menuUsuario"; // o algún error si no existe
        }

        // 2) Obtenemos las plantas que pertenecen a ese Edificio (por ID)
        // Suponiendo que en PlantaService hay un método findByEdificio(edificioId)
        List<Planta> plantas = plantaService.findByEdificio(edificioId);

        // 3) Enviamos al modelo el edificio y sus plantas
        model.addAttribute("edificio", edificio);
        model.addAttribute("plantas", plantas);

        // 4) Retornamos una plantilla (ej. plantasUsuario.html) que muestre las plantas
        return "plantasUsuario";
    }
    
    @GetMapping("/planta/{id}/aulas")
    public String listarAulasPorPlanta(@PathVariable("id") Long plantaId, Model model) {
        // 1) Buscamos la Planta
        Planta planta = plantaService.findById(plantaId);
        if (planta == null) {
            // Si la planta no existe, redirigimos a la lista de edificios o menú
            return "redirect:/menuUsuario";
        }

        // 2) Obtenemos las aulas de esta planta
        //   Suponiendo que tienes un método en aulaService:
        //   List<Aula> findByPlantaId(Long plantaId);
        List<Aula> aulas = aulaService.findByPlantaId(plantaId);

        // 3) Añadimos la planta y sus aulas al modelo
        model.addAttribute("planta", planta);
        model.addAttribute("aulas", aulas);

        // 4) Devolvemos la plantilla "aulasUsuario.html" (por ejemplo)
        return "aulasUsuario";
    }

}
