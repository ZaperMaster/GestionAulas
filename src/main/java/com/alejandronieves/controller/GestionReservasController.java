package com.alejandronieves.controller;

import com.alejandronieves.model.Reserva;
import com.alejandronieves.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/gestionarReservas")
public class GestionReservasController {

    @Autowired
    private ReservaService reservaService;

    // Lista todas las reservas
    @GetMapping
    public String listarReservas(Model model) {
        model.addAttribute("reservas", reservaService.listAll());
        return "gestionar_reservas"; // Esta vista listará todas las reservas
    }

    // Muestra el formulario para editar (validar) una reserva
    @GetMapping("/editar/{id}")
    public String editarReserva(@PathVariable("id") Long id, Model model) {
        Reserva reserva = reservaService.findById(id);
        if (reserva == null) {
            return "redirect:/gestionarReservas";
        }
        model.addAttribute("reserva", reserva);
        return "editar_reserva"; // Vista para validar (editar) la reserva
    }

    // Procesa el formulario de edición/validación de reserva
    @PostMapping("/editar")
    public String actualizarReserva(@ModelAttribute("reserva") Reserva reserva) {
        // Aquí, se actualiza la reserva (por ejemplo, el campo "activo" que usaremos para validarla)
        reservaService.save(reserva);
        return "redirect:/gestionarReservas";
    }
}
