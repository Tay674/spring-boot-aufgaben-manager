package com.example.demo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String>fehlerMeldung(MethodArgumentNotValidException ex){
		Map<String, String> error = new HashMap<>();
		
		ex.getBindingResult().getFieldErrors().forEach((e)->{
			error.put(e.getField(), e.getDefaultMessage());
		});
		
		return error;
	}

	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String>notFoundfehlerMeldung(EntityNotFoundException ex){
		return Map.of("fehler",ex.getMessage());
	}
	
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String>doppelterNameFehler(IllegalArgumentException ex){
		return Map.of("fehler",ex.getMessage());
	}
	
}
