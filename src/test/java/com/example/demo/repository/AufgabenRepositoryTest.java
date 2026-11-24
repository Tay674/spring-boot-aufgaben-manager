package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.example.demo.model.Aufgabe;
import com.example.demo.model.Prioritaet;

@DataJpaTest
public class AufgabenRepositoryTest {

	
	@Autowired private AufgabenRepository aufgabenRepository;
	
	
	@Test
	void findByErledigt_sollNurPassendeAufgabenZurueckgeben() {
		
		Aufgabe aufgabeA = new Aufgabe("Steuer machen", true,null);
		Aufgabe aufgabeb = new Aufgabe("Einkaufen gehen",false,null);
		
		aufgabenRepository.save(aufgabeA);
		aufgabenRepository.save(aufgabeb);
		
		List<Aufgabe> offeneAufgaben = aufgabenRepository.findByErledigt(false);
		
		assertEquals(1, offeneAufgaben.size());
		assertEquals("Einkaufen gehen", offeneAufgaben.get(0).getTitel());
		
		
		
		
	}
	
	@Test
	void findByPrioritaet_sollNurAufgabenMitPassenderPrioritaetZurueckgeben(){
		Aufgabe aufgabeA = new Aufgabe("Steuer machen", true,Prioritaet.HOCH);
		Aufgabe aufgabeb = new Aufgabe("Einkaufen gehen",false,Prioritaet.MITTEL);
		aufgabenRepository.save(aufgabeA);
		aufgabenRepository.save(aufgabeb);
		
		List<Aufgabe>aufgabenNachPrio = aufgabenRepository.findByPrioritaet(Prioritaet.HOCH);
		
		assertEquals(1, aufgabenNachPrio.size());
		assertEquals(Prioritaet.HOCH, aufgabenNachPrio.get(0).getPrioritaet());
		
	}
}
