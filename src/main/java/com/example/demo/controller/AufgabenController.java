package com.example.demo.controller;

import com.example.demo.dto.AufgabenResponse;
import com.example.demo.dto.AufgabenRequest;
import com.example.demo.service.AufgabenService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/aufgaben")
public class AufgabenController {

	private final AufgabenService aufgabenService;

	public AufgabenController(AufgabenService aufgabenService) {
		this.aufgabenService = aufgabenService;
	}

	@GetMapping()
	public List<AufgabenResponse> getAufgaben() {
		return aufgabenService.liste();
	}

	@PostMapping()
	public AufgabenResponse neueAufgabe(@Valid @RequestBody AufgabenRequest request){
		return aufgabenService.neueAufgabe(request);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AufgabenResponse> updateAufgabe(@PathVariable int id,@Valid @RequestBody AufgabenRequest geaenderteAufgabe) {
		return aufgabenService.updateAufgabe(id, geaenderteAufgabe).map(aufgabe -> ResponseEntity.ok(aufgabe))
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<AufgabenResponse> deleteAufgabe(@PathVariable int id) {
		if (aufgabenService.deleteAufgabe(id)) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/filter")
	public List<AufgabenResponse> aufgabeFilter(@RequestParam boolean erledigt) {
		return aufgabenService.aufgabenNachStatus(erledigt);
	}

}
