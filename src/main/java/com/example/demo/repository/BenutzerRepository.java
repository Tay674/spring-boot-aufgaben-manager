package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Benutzer;

public interface BenutzerRepository extends JpaRepository<Benutzer, Integer> {

	Optional<Benutzer> findByUsername(String username);
	
	boolean existsByUsername(String username);
	
}
