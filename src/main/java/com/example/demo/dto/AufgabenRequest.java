package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.Prioritaet;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AufgabenRequest(
		@NotBlank(message = "Der Titel darf nicht leer sein")
		@Size(min = 3 , max = 100,message = "Der Titel muss zwischen 3 und 100 Zeichen lang sein")
		String titel,
		boolean erledigt,
		Integer kategorieId,
		Prioritaet prioritaet,
		@FutureOrPresent(message="Das Fälligkeitsdatum darf nicht in der Vergangenheit liegen")
		LocalDate faelligAm
		) {}
