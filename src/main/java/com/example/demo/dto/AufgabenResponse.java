package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.Prioritaet;

public record AufgabenResponse(
		int id,
		String titel,
		boolean erledigt,
		Integer kategorieId,
		String kategorieName,
		Prioritaet prioritaet,
		LocalDate faelligAm
		) {}
