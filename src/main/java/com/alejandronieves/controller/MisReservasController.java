package com.alejandronieves.controller;

import com.alejandronieves.model.Reserva;
import com.alejandronieves.model.Usuario;
import com.alejandronieves.service.ReservaService;
import com.alejandronieves.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MisReservasController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/misreservas")
    public String misReservas(Model model) {
        // Obtén el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();  // Se asume que el username es el email
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(email);
        
        if (!usuarioOpt.isPresent()) {
            // Si no se encuentra el usuario, redirige al login
            return "redirect:/login";
        }
        
        Usuario usuario = usuarioOpt.get();
        Long usuarioId = usuario.getId();
        
        // Obtén las reservas asociadas al usuario
        List<Reserva> reservas = reservaService.listByUserId(usuarioId);
        if (reservas == null) {
            reservas = new ArrayList<>();
        }
        
        model.addAttribute("reservas", reservas);
        return "mis_reservas"; // Vista Thymeleaf: mis_reservas.html
    }
}
