package com.alejandronieves.controller;

import com.alejandronieves.model.Usuario;
import com.alejandronieves.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    // Muestra el formulario de registro
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro"; // Se renderiza registro.html
    }

    // Procesa el formulario de registro
    @PostMapping("/registro")
    public String registrarUsuario(@Validated @ModelAttribute("usuario") Usuario usuario,
                                   BindingResult bindingResult,
                                   Model model) {
        // Validar que el email no esté ya registrado
        if (usuarioService.buscarPorEmail(usuario.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.usuario", "Ya existe un usuario con este email.");
        }
        
        if (bindingResult.hasErrors()) {
            return "registro";
        }
        
        usuarioService.guardarUsuario(usuario);
        // Redirige a la página de login tras un registro exitoso, opcionalmente puedes enviar un mensaje de éxito
        return "redirect:/login?registrado";
    }
}
