package com.example.demo.service;


import java.util.List;
import java.util.Optional;
import org.springframework.validation.annotation.Validated;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AufgabenResponse;
import com.example.demo.model.Aufgabe;
import com.example.demo.model.Benutzer;
import com.example.demo.model.Prioritaet;
import com.example.demo.dto.AufgabenRequest;
import com.example.demo.repository.AufgabenRepository;
import com.example.demo.repository.BenutzerRepository;
import com.example.demo.repository.KategorieRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
@Validated
public class AufgabenService {
	

	private final AufgabenRepository aufgabenRepository;
	private final KategorieRepository kategorieRepository;
	private final BenutzerRepository benutzerRepository;
	
	public AufgabenService(AufgabenRepository aufgabenRepository,KategorieRepository kategorieRepository,BenutzerRepository benutzerRepository) {
		this.aufgabenRepository = aufgabenRepository;
		this.kategorieRepository = kategorieRepository;
		this.benutzerRepository = benutzerRepository;
		
	}
	

	public List<AufgabenResponse> liste (){
		return aufgabenRepository.findAll().stream().map(this::toResponse).toList();
	}
	

	
	public AufgabenResponse neueAufgabe( AufgabenRequest request)  {
		Aufgabe neueAufgabe = new Aufgabe(request.titel(), request.erledigt(),request.prioritaet());
		if(request.kategorieId()!= null ) {
		neueAufgabe.setKategorie( kategorieRepository.findById(request.kategorieId()).orElseThrow(() -> new EntityNotFoundException("Kategorie mit ID " + request.kategorieId() + " nicht gefunden")));
		}
		neueAufgabe.setBenutzer(aktuellerBenutzer());
		neueAufgabe.setFaelligAm(request.faelligAm());
		return toResponse(aufgabenRepository.save(neueAufgabe));
	}
	
	
	public Page<AufgabenResponse> sucheAufgaben(String titel,Boolean erledigt,Prioritaet prioritaet,Pageable pageable) {
		return aufgabenRepository.sucheAufgaben(aktuellerBenutzer(),titel, erledigt, prioritaet, pageable).map(this::toResponse);
	}
	
	public Optional<AufgabenResponse> updateAufgabe( int id, @Valid AufgabenRequest request)  {
	return aufgabenRepository.findByIdAndBenutzer(id, aktuellerBenutzer())
			.map(a -> {
				a.setTitel(request.titel());
				a.setErledigt(request.erledigt());
				a.setFaelligAm(request.faelligAm());
				a.setPrioritaet(request.prioritaet());
				if(request.kategorieId() != null) {
						a.setKategorie(kategorieRepository.findById(request.kategorieId()).orElseThrow(() -> new EntityNotFoundException("Kategorie mit ID " + request.kategorieId() + " nicht gefunden")));
					
					}else {
					a.setKategorie(null);
				}
				
				
				
				Aufgabe gespeichert = aufgabenRepository.save(a);
				
				return toResponse(gespeichert);
				
				});
		
	}
	
	public Boolean deleteAufgabe(int id) {
		if( aufgabenRepository.findByIdAndBenutzer(id, aktuellerBenutzer()).isPresent()) {
			aufgabenRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	
	public List<AufgabenResponse>aufgabenNachStatus(boolean erledigt){
		return aufgabenRepository.findByErledigt(erledigt).stream().map(this::toResponse).toList();
	}
	
	
	public List<AufgabenResponse>aufgabenNachPrioritaet(Prioritaet prioritaet){
		return aufgabenRepository.findByPrioritaet(prioritaet).stream().map(this::toResponse).toList();
	}
	
	private  AufgabenResponse toResponse(Aufgabe a) {
		String katName = (a.getKategorie() != null) ? a.getKategorie().getName() : null;
		Integer katId =   ( a.getKategorie() !=null) ?  a.getKategorie().getId() : null;
		return new AufgabenResponse(a.getId(),a.getTitel(),a.isErledigt(),katId,katName,a.getPrioritaet(),a.getFaelligAm());
	}
	
	private Benutzer aktuellerBenutzer() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Benutzer benutzer  = benutzerRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Benutzer nicht gefunden"));
		return benutzer;
	}
	
}
