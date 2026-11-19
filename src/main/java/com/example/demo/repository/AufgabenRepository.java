package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modell.Aufgabe;

public interface AufgabenRepository extends JpaRepository<Aufgabe, Integer>{

	List<Aufgabe> findByErledigt(boolean erledigt);
	
	List<Aufgabe> findByTitelContainingIgnoreCase(String suchwort);


	
	
}
