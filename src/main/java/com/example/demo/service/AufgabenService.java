package com.example.demo.service;

import com.example.demo.controller.AufgabenController;
import com.example.demo.exception.GlobalExceptionHandler;
import java.util.List;
import java.util.Optional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AufgabenResponse;
import com.example.demo.dto.AufgabenRequest;
import com.example.demo.modell.Aufgabe;
import com.example.demo.repository.AufgabenRepository;
import com.example.demo.repository.KategorieRepository;

import jakarta.persistence.EntityNotFoundException;
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
	

	public List<AufgabenResponse> liste (){
		return aufgabenRepository.findAll().stream().map(this::toResponse).toList();
	}
	

	
	public AufgabenResponse neueAufgabe( AufgabenRequest request)  {
		Aufgabe neueAufgabe = new Aufgabe(request.titel(), request.erledigt());
		if(request.kategorieId()!= null) {
		neueAufgabe.setKategorie( kategorieRepository.findById(request.kategorieId()).orElseThrow(() -> new EntityNotFoundException("Kategorie mit ID " + request.kategorieId() + " nicht gefunden")));
		}
		
		return toResponse(aufgabenRepository.save(neueAufgabe));
	}
	
	
	public Optional<AufgabenResponse> updateAufgabe( int id, @Valid AufgabenRequest request)  {
	return aufgabenRepository.findById(id)
			.map(a -> {
				a.setTitel(request.titel());
				a.setErledigt(request.erledigt());
			
				if(request.kategorieId() != null) {
						a.setKategorie(kategorieRepository.findById(request.kategorieId()).orElseThrow(() -> new EntityNotFoundException("Kategorie mit ID " + request.kategorieId() + " nicht gefunden")));
					
					}else {
					a.setKategorie(null);
				}
				
				Aufgabe gespeichert = aufgabenRepository.save(a);
				
				return toResponse(gespeichert);
				
				});
		
	}
	
	public boolean deleteAufgabe(int id) {
		if( aufgabenRepository.existsById(id)) {
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
		Integer katId =   ( a.getKategorie() !=null) ?  a.getKategorie().getId() : null;
		return new AufgabenResponse(a.getId(),a.getTitel(),a.isErledigt(),katId,katName);
	}
	
}
