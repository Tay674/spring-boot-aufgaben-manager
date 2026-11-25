package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Aufgabe;
import com.example.demo.model.Benutzer;
import com.example.demo.model.Prioritaet;

public interface AufgabenRepository extends JpaRepository<Aufgabe, Integer>{

	List<Aufgabe> findByErledigt(boolean erledigt);
	
	List<Aufgabe> findByTitelContainingIgnoreCase(String suchwort);

	List<Aufgabe> findByPrioritaet(Prioritaet prioritaet);
	
	List<Aufgabe> findByFaelligAmBeforeAndErledigtFalse(LocalDate datum);
	
	Optional<Aufgabe> findByIdAndBenutzer(int id, Benutzer aktuellerBenutzer);
	
	@Query("""
		    SELECT a FROM Aufgabe a
		    WHERE a.benutzer = :benutzer 
			  AND (:titel IS NULL OR LOWER(a.titel) LIKE LOWER(CONCAT('%', :titel, '%')))
		      AND (:erledigt IS NULL OR a.erledigt = :erledigt)
		      AND (:prioritaet IS NULL OR a.prioritaet = :prioritaet)
		""")
		Page<Aufgabe> sucheAufgaben(
				@Param("benutzer") Benutzer benutzer,
				@Param("titel") String titel,
		        @Param("erledigt") Boolean erledigt,
		        @Param("prioritaet") Prioritaet prioritaet,
		        Pageable pageable
		);

	

	
	
}
