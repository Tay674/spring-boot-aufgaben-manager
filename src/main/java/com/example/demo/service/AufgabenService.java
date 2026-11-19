package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import org.springframework.validation.annotation.Validated;

import org.springframework.stereotype.Service;

import com.example.demo.dto.AufgabenResponse;
import com.example.demo.dto.AufgabenRequest;
import com.example.demo.modell.Aufgabe;
import com.example.demo.repository.AufgabenRepository;
import com.example.demo.repository.KategorieRepository;

import jakarta.validation.Valid;

@Service
@Validated
public class AufgabenService {
	
	private final AufgabenRepository aufgabenRepository;
	private final KategorieRepository kategorieRepository;
	
	
	public AufgabenService(AufgabenRepository aufgabenRepository,KategorieRepository kategorieRepository) {
		this.aufgabenRepository = aufgabenRepository;
		this.kategorieRepository = kategorieRepository;

		
		
	}
	
	
	public Aufgabe aufgabe() {
		Aufgabe a = new Aufgabe("Programmieren", true);
		return a;
		
	}
	

	public List<AufgabenResponse> Liste (){
		return aufgabenRepository.findAll().stream().map(this::toResponse).toList();
	}
	

	
	public AufgabenResponse neueAufgabe( AufgabenRequest request) {
		System.out.println("DEBUG ID1: "+request.kategorieId());
		Aufgabe neueAufgabe = new Aufgabe(request.titel(), request.erledigt());
		System.out.println("DEBUG ID2: "+request.kategorieId());
		if(request.kategorieId()!= null) {
			System.out.println("DEBUG ID3: "+request.kategorieId());
		neueAufgabe.setKategorie( kategorieRepository.findById(request.kategorieId()).orElse(null));
		System.out.println("DEBUG ID4: "+request.kategorieId());
		}
		
		System.out.println("DEBUG ID5: "+request.kategorieId());
		return toResponse(aufgabenRepository.save(neueAufgabe));
	}
	
	
	public Optional<AufgabenResponse> updateAufgabe( int id, @Valid AufgabenRequest request) {
	return aufgabenRepository.findById(id)
			.map(a -> {
				a.setTitel(request.titel());
				a.setErledigt(request.erledigt());
				
				if(request.kategorieId() != null) {
					a.setKategorie(kategorieRepository.findById(request.kategorieId()).orElse(null));
				}else {
					a.setKategorie(null);
				}
				
				Aufgabe gespeichert = aufgabenRepository.save(a);
				
				return toResponse(gespeichert);
				
			});
		
	}
	
	public boolean deleteAufgabe(int id) {
		if( aufgabenRepository.existsById(id)==true) {
			aufgabenRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	
	public List<AufgabenResponse>aufgabenNachStatus(boolean erledigt){
		return aufgabenRepository.findByErledigt(erledigt).stream().map(this::toResponse).toList();
	}
	
	
	
	private  AufgabenResponse toResponse(Aufgabe a) {
		String katName = (a.getKategorie() != null) ? a.getKategorie().getName() : null;
		int katId = a.getKategorie().getId() ;
		return new AufgabenResponse(a.getId(),a.getTitel(),a.isErledigt(),katId,katName);
	}
	
}
