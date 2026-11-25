package com.example.demo.service;

import com.example.demo.dto.RegisterRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Benutzer;
import com.example.demo.repository.BenutzerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	
	private BenutzerRepository benutzerRepository;
	private PasswordEncoder passwordEncoder;

	
	public CustomUserDetailsService(BenutzerRepository benutzerRepository,PasswordEncoder passwordEncoder) {
		this.benutzerRepository = benutzerRepository;
		this.passwordEncoder = passwordEncoder;
	
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
	Benutzer benutzer =	benutzerRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Benutzer nicht gefunden: " + username));
	return org.springframework.security.core.userdetails.User.withUsername(benutzer.getUsername()).password(benutzer.getPassword()).roles("USER").build();
	}
	
	
	public void registrieren(RegisterRequest request) {
		
		if(benutzerRepository.existsByUsername(request.username())) {
		throw new IllegalArgumentException("Benutzername ist bereits vergeben");}
		String verschluesseltesPasswort = passwordEncoder.encode(request.password());
		benutzerRepository.save(new Benutzer(request.username(),verschluesseltesPasswort));
	}


	

}
