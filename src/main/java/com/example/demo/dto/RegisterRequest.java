package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(	
		@NotBlank
		@Size(min = 3, max = 50)
		String username,
		@NotBlank
		@Size(min = 6, message = "Das Passwort muss mindestens 6 Zeichen lang sein")
		String password
		){}
