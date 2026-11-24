package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Aufgabe;
import com.example.demo.model.Prioritaet;

public interface AufgabenRepository extends JpaRepository<Aufgabe, Integer>{

	List<Aufgabe> findByErledigt(boolean erledigt);
	
	List<Aufgabe> findByTitelContainingIgnoreCase(String suchwort);

	List<Aufgabe> findByPrioritaet(Prioritaet prioritaet);
	
	List<Aufgabe> findByFaelligAmBeforeAndErledigtFalse(LocalDate datum);
	
}
