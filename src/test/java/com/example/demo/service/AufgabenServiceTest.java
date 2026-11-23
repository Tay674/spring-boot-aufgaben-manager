package com.example.demo.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import com.example.demo.dto.AufgabenRequest;
import com.example.demo.dto.AufgabenResponse;
import com.example.demo.modell.Aufgabe;
import com.example.demo.modell.Prioritaet;
import com.example.demo.repository.AufgabenRepository;
import com.example.demo.repository.KategorieRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class AufgabenServiceTest {

	
	@Mock
	private AufgabenRepository aufgabenRepository;
	
	@Mock
	private KategorieRepository kategorieRepositroy;
	
	@InjectMocks
	private AufgabenService aufgabenService;
	
	
	
	@Test
	void neueAufgabe_sollAufgabeSpeichernUndAlsDtoZurueckgeben() {

		when(aufgabenRepository.save(any(Aufgabe.class))).thenReturn(new Aufgabe("Testen lernen", false,null));
		AufgabenResponse ergebnis = aufgabenService.neueAufgabe(new AufgabenRequest("Testen lernen",false,null,null));
		
		assertEquals("Testen lernen", ergebnis.titel());
		assertFalse(ergebnis.erledigt());

	}
	
	@Test
	void neueAufgabe_sollExceptionWerfen_wennKategorieNichtExistiert(){
		 
		 when(kategorieRepositroy.findById(99)).thenReturn(Optional.empty());
		 assertThrows(EntityNotFoundException.class, ()-> aufgabenService.neueAufgabe(new AufgabenRequest("Test", false, 99,null)));
		 verify(aufgabenRepository, never()).save(any());
		 
	}
	
	
	
	@Test
	void deleteAufgabe_deleteGehtDurch() {
		when(aufgabenRepository.existsById(1)).thenReturn(true);
		boolean  geloescht = aufgabenService.deleteAufgabe(1);
		assertTrue(geloescht);
		verify(aufgabenRepository).deleteById(1);
	}
	
	
	
	@Test
	void deleteAufgabe_deleteGehtNichtDurch() {
		when(aufgabenRepository.existsById(99)).thenReturn(false);
		boolean  geloescht = aufgabenService.deleteAufgabe(99);
		assertFalse(geloescht);
		verify(aufgabenRepository,never()).deleteById(anyInt());
	}
	
	
	@Test
	void updateAufgabe_sollAufgabeAktualisieren_wennIdExistiert() {
		Aufgabe aufgabe = new Aufgabe("Alter Titel",false,null);
		AufgabenRequest aRequest = new AufgabenRequest("Neuer Titel", true, null,null);
		
		when(aufgabenRepository.findById(1)).thenReturn(Optional.of(aufgabe));
		when(aufgabenRepository.save(any(Aufgabe.class))).thenReturn(aufgabe);
		Optional<AufgabenResponse> ergebnis = aufgabenService.updateAufgabe(1, aRequest);
		
		assertTrue(ergebnis.isPresent());
		assertEquals("Neuer Titel", ergebnis.get().titel());
		assertTrue(ergebnis.get().erledigt());
		verify(aufgabenRepository).save(any(Aufgabe.class));
		
		
	}
	
	@Test
	void updateAufgabe_sollLeeresOptionalZurueckgeben_wennIdNichtExistiert () {
		
		when(aufgabenRepository.findById(99)).thenReturn(Optional.empty());
		AufgabenRequest aRequest = new AufgabenRequest("Neuer Titel", true, null,null);
		Optional<AufgabenResponse> ergebnis = aufgabenService.updateAufgabe(99, aRequest);
		assertTrue(ergebnis.isEmpty());
		verify(aufgabenRepository,never()).save(any());
		
	}
	
	@Test
	void neueAufgabe_sollStandardPrioritaetNiedrigSetzen_wennPrioritaetNullIst() {
		AufgabenRequest aRequest = new AufgabenRequest("Neuer Titel", true, null,null);
		when(aufgabenRepository.save(any(Aufgabe.class))).thenAnswer(invocation -> invocation.getArgument(0));
		AufgabenResponse ergebnis = aufgabenService.neueAufgabe(aRequest);
		assertEquals(Prioritaet.NIEDRIG, ergebnis.prioritaet());
		verify(aufgabenRepository).save(any(Aufgabe.class));
		
	}
	
	
	
}
