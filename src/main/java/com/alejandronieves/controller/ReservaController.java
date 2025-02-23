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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public String reservarAula(@PathVariable("idAula") Long idAula, Model model) {
        Aula aula = aulaService.findById(idAula);
        if (aula == null) {
            return "redirect:/menuUsuario";
        }

        // Obtén el usuario real desde el contexto de seguridad
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // Se asume que el email es el username
        Usuario usuarioLogueado = usuarioService.buscarPorEmail(email).orElse(null);
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

    // Procesa el formulario de reserva (binding directo al objeto Reserva)
    @PostMapping("/procesar")
    public String procesarReserva(@ModelAttribute("reserva") Reserva reserva,
                                  BindingResult result,
                                  @RequestParam("cursoId") Long cursoId,
                                  Model model) {

        // Asigna el curso a la reserva
        Curso curso = cursoService.findById(cursoId);
        reserva.setCurso(curso);

        // Marca la reserva como activa
        reserva.setActivo(true);

        // (Opcional) Aquí podrías realizar validaciones adicionales

        // Guarda la reserva en la base de datos
        reservaService.save(reserva);

        // Después de reservar, redirige al menú de usuario
        return "redirect:/menuUsuario";
    }
}
