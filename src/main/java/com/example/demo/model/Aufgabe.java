package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;

@Entity
public class Aufgabe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	
	@NotBlank(message = "Der Titel darf nicht leer sein")
	@Size(min=3, max=100, message="Der Titel muss zwischen 3 und 100 Zeichen lang sein")
	private String titel;
	private boolean erledigt;
	
	@Enumerated(EnumType.STRING)
	private Prioritaet prioritaet = Prioritaet.MITTEL; 
	
	@ManyToOne
	private Kategorie kategorie;
	
	private LocalDate faelligAm;
	
	@ManyToOne
	private Benutzer benutzer;
	
	public Aufgabe() {
		
	}
	
	public Aufgabe(String titel , boolean erledigt,Prioritaet prioritaet) {
		this.titel=titel;
		this.erledigt = erledigt;
		setPrioritaet(prioritaet);
	}
	
	
	public int getId() {
		return id;
	}
	
	
	
	public void setTitel(String titel) {
		this.titel = titel;
	}
	
	public String getTitel() {
		return titel;
	}
	
	public void setErledigt(boolean erledigt) {
		this.erledigt = erledigt;
	}
	public boolean isErledigt() {
		return erledigt;
	}
	
	public void setKategorie(Kategorie kategorie) {
		this.kategorie= kategorie;
	}
	
	
	public Kategorie getKategorie() {
		return kategorie;
	}
	
	public void setPrioritaet(Prioritaet prioritaet) {
		this.prioritaet =(prioritaet != null) ? prioritaet : Prioritaet.NIEDRIG;
	}
	
	
	public Prioritaet getPrioritaet() {
		return prioritaet;
	}
	
	public void setFaelligAm(LocalDate faelligAm) {
		this.faelligAm = faelligAm;
	}
	

	public LocalDate getFaelligAm() {
		return faelligAm;
	}

	
	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}
	
	public Benutzer getBenutzer() {
		return benutzer;
	}
	
}
