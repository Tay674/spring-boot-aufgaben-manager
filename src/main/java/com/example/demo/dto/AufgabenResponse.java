package com.example.demo.dto;

import com.example.demo.modell.Prioritaet;

public record AufgabenResponse(
		int id,
		String titel,
		boolean erledigt,
		Integer kategorieId,
		String kategorieName,
		Prioritaet prioritaet
		) {}
