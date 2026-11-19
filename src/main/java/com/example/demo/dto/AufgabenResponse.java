package com.example.demo.dto;

public record AufgabenResponse(
		int id,
		String titel,
		boolean erledigt,
		Integer kategorieId,
		String kategorieName
		) {}
