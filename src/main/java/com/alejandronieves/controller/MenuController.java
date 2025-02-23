package com.alejandronieves.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping("/menu")
    public String adminMenu(Model model) {
        // Agrega atributos para el menú de administradores
        return "menu"; // Asegúrate de tener src/main/resources/templates/menu.html
    }
}

