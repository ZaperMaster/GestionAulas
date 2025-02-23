package com.alejandronieves.controller;

import com.alejandronieves.model.Aula;
import com.alejandronieves.model.Usuario;
import com.alejandronieves.service.AulaService;
import com.alejandronieves.service.UsuarioService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AulasUsuarioController {

    @Autowired
    private AulaService aulaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/aulasUsuario")
    public String aulasUsuario(Model model) {
        // Obtenemos el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(email);
        if (!usuarioOpt.isPresent()) {
            return "redirect:/login";
        }
        Usuario usuario = usuarioOpt.get();

        // Suponiendo que tienes un método en AulaService que recupere las aulas asignadas a ese usuario
        // (ajusta este método según la lógica de tu aplicación)
        List<Aula> aulas = aulaService.findByUsuarioId(usuario.getId());
        model.addAttribute("aulas", aulas);
        model.addAttribute("usuario", usuario);
        return "aulas_usuario"; // Vista que muestra las aulas del usuario
    }
}
