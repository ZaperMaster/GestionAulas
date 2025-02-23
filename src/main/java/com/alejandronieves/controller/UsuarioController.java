package com.alejandronieves.controller;

import com.alejandronieves.model.TipoUsuario;
import com.alejandronieves.model.Usuario;
import com.alejandronieves.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Listar todos los usuarios
    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.listAll();
        model.addAttribute("usuarios", usuarios);
        return "usuarios_list"; // Asegúrate de tener una plantilla "usuarios_list.html"
    }

    // Mostrar formulario para editar el tipo de usuario
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable("id") Long id, Model model) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return "redirect:/usuarios";
        }
        model.addAttribute("usuario", usuario);
        return "usuario_form"; // Vista que definimos más arriba
    }

    // Procesar la actualización del tipo de usuario
    @PostMapping("/actualizar")
    public String actualizarUsuario(@ModelAttribute("usuario") Usuario usuarioForm,
                                    @RequestParam("tipoUsuarioId") Long tipoUsuarioId,
                                    RedirectAttributes redirectAttributes) {
        // Recupera el usuario existente
        Usuario usuarioExistente = usuarioService.findById(usuarioForm.getId());
        if (usuarioExistente == null) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado.");
            return "redirect:/usuarios";
        }
        
        // Valida que el nuevo tipo sea 1 (ADMIN) o 3 (USER)
        if (tipoUsuarioId == null || (tipoUsuarioId != 1 && tipoUsuarioId != 3)) {
            redirectAttributes.addFlashAttribute("error", "Tipo de usuario inválido.");
            return "redirect:/usuarios";
        }
        
        // Crea una instancia de TipoUsuario con el ID recibido
        TipoUsuario nuevoTipo = new TipoUsuario();
        nuevoTipo.setId(tipoUsuarioId);
        
        // Actualiza el tipo de usuario en el objeto existente
        usuarioExistente.setTipoUsuario(nuevoTipo);
        
        // Guarda el usuario actualizado
        usuarioService.save(usuarioExistente);
        redirectAttributes.addFlashAttribute("success", "Tipo de usuario actualizado correctamente.");
        return "redirect:/usuarios";
    }



}

