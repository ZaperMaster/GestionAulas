package com.alejandronieves.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

	// La raíz lleva a cada usuario a su menú según su rol
	@GetMapping({"/", "/home"})
	public String inicio(Authentication auth) {
		boolean isAdmin = auth.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
		return isAdmin ? "redirect:/menu" : "redirect:/menuUsuario";
	}
}
