package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.service.CustomUserDetailsService;

import jakarta.validation.Valid;

@RestController
public class AuthController {
	
	private final CustomUserDetailsService customUserDetailsService ;

	public AuthController(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
	customUserDetailsService.registrieren(request);
	return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
}
