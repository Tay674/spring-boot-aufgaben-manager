package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.modell.Aufgabe;
import com.example.demo.repository.AufgabenRepository;

@Service
public class AufgabenService {
	
	private final AufgabenRepository aufgabenRepository;
	
	
	public AufgabenService(AufgabenRepository aufgabenRepository) {
		this.aufgabenRepository = aufgabenRepository;

		
		
	}
	
	
	public Aufgabe aufgabe() {
		Aufgabe a = new Aufgabe("Programmieren", true);
		return a;
		
	}
	

	public List<Aufgabe> Liste (){
		return aufgabenRepository.findAll();
	}
	

	
	public Aufgabe neueAufgabe( Aufgabe aufgabe) {
		return aufgabenRepository.save(aufgabe);
	}
	
	
	public Optional<Aufgabe> updateAufgabe( int id, Aufgabe geanderteAufgabe) {
	return aufgabenRepository.findById(id)
			.map(a -> {
				a.setTitel(geanderteAufgabe.getTitel());
				a.setErledigt(geanderteAufgabe.isErledigt());
				return aufgabenRepository.save(a);
			});
		
	}
	
	public boolean deleteAufgabe(int id) {
		if( aufgabenRepository.existsById(id)==true) {
			aufgabenRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	
	public List<Aufgabe>aufgabenNachStatus(boolean erledigt){
		return aufgabenRepository.findByErledigt(erledigt);
	}
	
	
	
}
