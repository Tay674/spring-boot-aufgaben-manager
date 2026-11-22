package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.example.demo.modell.Aufgabe;

@DataJpaTest
public class AufgabenRepositoryTest {

	
	@Autowired private AufgabenRepository aufgabenRepository;
	
	
	@Test
	void findByErledigt_sollNurPassendeAufgabenZurueckgeben() {
		
		Aufgabe aufgabeA = new Aufgabe("Steuer machen", true);
		Aufgabe aufgabeb = new Aufgabe("Einkaufen gehen",false);
		
		aufgabenRepository.save(aufgabeA);
		aufgabenRepository.save(aufgabeb);
		
		List<Aufgabe> offeneAufgaben = aufgabenRepository.findByErledigt(false);
		
		assertEquals("Einkaufen gehen", offeneAufgaben.get(0).getTitel());
		assertEquals(1, offeneAufgaben.size());
		
		
		
	}
}
