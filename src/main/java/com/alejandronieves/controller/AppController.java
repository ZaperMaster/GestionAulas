package com.alejandronieves.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class AppController {
	
	
	@Value("${aplicacion.nombre}")
	private String titulo;
	
	
	@GetMapping("/")
	public String inicio(Model model) {
		System.out.println("index: " + titulo);	
		model.addAttribute("titulo", titulo);
		return "index";
	}
	/*
	@GetMapping("/")
	public String inicio(Model model) {
		model.addAttribute("titulo", "GGG");
		return "index";
	}
	*/
}
