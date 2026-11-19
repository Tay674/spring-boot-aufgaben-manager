package com.example.demo.modell;

import jakarta.persistence.Entity;
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
	
	@ManyToOne
	private Kategorie kategorie;
	
	public Aufgabe() {
		
	}
	
	public Aufgabe(String titel , boolean erledigt) {
		this.titel=titel;
		this.erledigt = erledigt;
	}
	
	
	public void setTitel(String titel) {
		this.titel = titel;
	}
	
	public void setErledigt(boolean erledigt) {
		this.erledigt = erledigt;
	}
	
	public void setKategorie(Kategorie kategorie) {
		this.kategorie= kategorie;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitel() {
		return titel;
	}
	
	public boolean isErledigt() {
		return erledigt;
	}
	
	public Kategorie getKategorie() {
		return kategorie;
	}
	
	
}
