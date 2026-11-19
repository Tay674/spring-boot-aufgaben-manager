package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modell.Kategorie;
import com.example.demo.repository.KategorieRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/kategorien")
public class KategorieController {
	
	
	private final KategorieRepository kategorieRepository;

	public KategorieController(KategorieRepository kategorieRepository) {
		this.kategorieRepository = kategorieRepository; 
	}

	
	@GetMapping()
	public List<Kategorie> alleKategorien() {
		return kategorieRepository.findAll();
	}
	
	@PostMapping()
	public Kategorie saveKategorie(@Valid @RequestBody Kategorie kategorie){
		return kategorieRepository.save(kategorie);
	}
}
