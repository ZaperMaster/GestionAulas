package com.alejandronieves.controller;

import com.alejandronieves.model.Aula;
import com.alejandronieves.model.Curso;
import com.alejandronieves.model.Reserva;
import com.alejandronieves.model.Usuario;
import com.alejandronieves.service.AulaService;
import com.alejandronieves.service.CursoService;
import com.alejandronieves.service.ReservaService;
import com.alejandronieves.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private AulaService aulaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CursoService cursoService;

    // Muestra el formulario de reserva para un aula específico
    @GetMapping("/aula/{idAula}")
    public String reservarAula(@PathVariable("idAula") Long idAula, Model model, Authentication auth) {
        Aula aula = aulaService.findById(idAula);
        if (aula == null) {
            return "redirect:/menuUsuario";
        }
        Usuario usuarioLogueado = usuarioService.buscarPorEmail(auth.getName()).orElse(null);
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        // Crea una nueva reserva y asigna el aula y el usuario
        Reserva reserva = new Reserva();
        reserva.setAula(aula);
        reserva.setUsuario(usuarioLogueado);

        // Lista de cursos para el combo
        List<Curso> cursos = cursoService.listAll();

        model.addAttribute("reserva", reserva);
        model.addAttribute("cursos", cursos);
        model.addAttribute("aula", aula);
        return "reserva_form"; // Vista Thymeleaf: reserva_form.html
    }

    // Procesa el formulario de reserva
    @PostMapping("/procesar")
    public String procesarReserva(@ModelAttribute("reserva") Reserva reserva,
                                  @RequestParam("cursoId") Long cursoId,
                                  Authentication auth) {
        // El usuario siempre es el que ha iniciado sesión, nunca el que llegue en el formulario
        Usuario usuario = usuarioService.buscarPorEmail(auth.getName()).orElse(null);
        Aula aula = reserva.getAula() != null ? aulaService.findById(reserva.getAula().getId()) : null;
        Curso curso = cursoService.findById(cursoId);
        if (usuario == null || aula == null || curso == null) {
            return "redirect:/menuUsuario";
        }
        reserva.setId(null);
        reserva.setUsuario(usuario);
        reserva.setAula(aula);
        reserva.setCurso(curso);
        reserva.setActivo(true);
        reservaService.save(reserva);
        return "redirect:/misreservas";
    }

    // Cancela una reserva propia
    @PostMapping("/borrar/{id}")
    public String cancelarReserva(@PathVariable("id") Long id, Authentication auth) {
        Reserva reserva = reservaService.findById(id);
        if (reserva != null && reserva.getUsuario().getEmail().equals(auth.getName())) {
            reservaService.deleteById(id);
        }
        return "redirect:/misreservas";
    }
}
