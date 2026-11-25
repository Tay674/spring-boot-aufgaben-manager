package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.dto.AufgabenRequest;
import com.example.demo.dto.AufgabenResponse;
import com.example.demo.model.Prioritaet;
import com.example.demo.service.AufgabenService;

import jakarta.persistence.EntityNotFoundException;




@WebMvcTest(AufgabenController.class)
@WithMockUser
public class AufgabenControllerTest {

	
	
@Autowired private MockMvc mockMvc;

@MockitoBean private AufgabenService aufgabenService;



@Test
void getAufgaben_sollListeMitStatus200Zurueckgeben() throws Exception {
	
	when(aufgabenService.sucheAufgaben(any(), any(), any(), any())).thenReturn(new PageImpl<>(List.of(new AufgabenResponse(1, "Einkaufen", false, null, null, null,null))));
	
	mockMvc.perform(get("/aufgaben")).andExpect(status().isOk()).andExpect(jsonPath("$.content[0].titel").value("Einkaufen"));
}


@Test
void neueAufgabe_soll400Zurueckgeben_wennTitelLeerIst() throws Exception {
	mockMvc.perform(post("/aufgaben").with(csrf())
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"titel\": \"\", \"erledigt\": false}"))
	.andExpect(status().isBadRequest())
	.andExpect(jsonPath("$.titel").isNotEmpty());
	verify(aufgabenService, never()).neueAufgabe(any());
}

@Test
void neueAufgabe_soll404Zurueckgeben_wennKategorieNichtExistiert() throws Exception {
	when(aufgabenService.neueAufgabe(new AufgabenRequest("Gueltiger Titel", false, 99,Prioritaet.HOCH,null))).thenThrow(new EntityNotFoundException("Kategorie mit ID 99 nicht gefunden"));
	mockMvc.perform(post("/aufgaben").with(csrf())
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"titel\": \"Gueltiger Titel\", \"erledigt\": false, \"kategorieId\": 99, \"prioritaet\": \"HOCH\"}"))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.fehler").value("Kategorie mit ID 99 nicht gefunden"));
	
}



@Test
void neueAufgabe_soll400Zurueckgeben_wennFaelligAmInDerVergangenheitLiegt() throws Exception{
	verify(aufgabenService, never()).neueAufgabe(any());
	mockMvc.perform(post("/aufgaben").with(csrf())
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"titel\": \"Gueltiger Titel\", \"erledigt\": false, \"kategorieId\": 99, \"prioritaet\": \"HOCH\", \"faelligAm\": \"2020-11-24\"}"))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.faelligAm").exists());	
}

}
