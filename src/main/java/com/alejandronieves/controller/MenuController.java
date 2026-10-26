package com.alejandronieves.controller;

import com.alejandronieves.model.Reserva;
import com.alejandronieves.service.AulaService;
import com.alejandronieves.service.EdificioService;
import com.alejandronieves.service.ReservaService;
import com.alejandronieves.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MenuController {

    @Autowired
    private EdificioService edificioService;

    @Autowired
    private AulaService aulaService;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UsuarioService usuarioService;

    // Panel del administrador con un resumen y las últimas reservas pendientes
    @GetMapping("/menu")
    public String adminMenu(Model model) {
        List<Reserva> reservas = reservaService.listAll();
        List<Reserva> pendientes = reservas.stream()
                .filter(r -> !Boolean.TRUE.equals(r.getValidar()))
                .sorted(Comparator.comparing(Reserva::getId).reversed())
                .collect(Collectors.toList());

        model.addAttribute("totalEdificios", edificioService.listAll().size());
        model.addAttribute("totalAulas", aulaService.listAll().size());
        model.addAttribute("totalReservas", reservas.size());
        model.addAttribute("totalPendientes", pendientes.size());
        model.addAttribute("totalUsuarios", usuarioService.listAll().size());
        model.addAttribute("pendientes", pendientes.stream().limit(5).collect(Collectors.toList()));
        return "menu";
    }
}
