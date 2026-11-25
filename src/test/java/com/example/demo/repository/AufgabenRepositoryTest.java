package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.demo.model.Aufgabe;
import com.example.demo.model.Benutzer;
import com.example.demo.model.Prioritaet;

@DataJpaTest
public class AufgabenRepositoryTest {

	
	@Autowired private AufgabenRepository aufgabenRepository;
	@Autowired private BenutzerRepository benutzerRepository;
	
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
	
	@Test
	void sucheAufgaben_sollNurEigeneAufgabenDesBenutzersFinden() {
	    Benutzer userA = benutzerRepository.save(new Benutzer("userA", "geheim123"));
	    Benutzer userB = benutzerRepository.save(new Benutzer("userB", "geheim123"));

	    Aufgabe a1 = new Aufgabe("Wocheneinkauf erledigen", false, Prioritaet.HOCH);
	    a1.setBenutzer(userA);

	    Aufgabe a2 = new Aufgabe("Wocheneinkauf fremd", false, Prioritaet.HOCH);
	    a2.setBenutzer(userB);

	    aufgabenRepository.save(a1);
	    aufgabenRepository.save(a2);

	    Page<Aufgabe> ergebnis = aufgabenRepository.sucheAufgaben(
	        userA, "einkauf", false, Prioritaet.HOCH, PageRequest.of(0, 10)
	    );

	    assertEquals(1, ergebnis.getTotalElements());
	    assertEquals("Wocheneinkauf erledigen", ergebnis.getContent().get(0).getTitel());
	}
	

}
