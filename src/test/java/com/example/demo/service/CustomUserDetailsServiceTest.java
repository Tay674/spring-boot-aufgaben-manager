package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Benutzer;
import com.example.demo.repository.BenutzerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private BenutzerRepository benutzerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void registrieren_sollPasswortVerschluesselnUndBenutzerSpeichern() {
        RegisterRequest request = new RegisterRequest("neuerUser", "geheim123");
        when(benutzerRepository.existsByUsername("neuerUser")).thenReturn(false);
        when(passwordEncoder.encode("geheim123")).thenReturn("verschluesselterHash");

        customUserDetailsService.registrieren(request);

        verify(passwordEncoder).encode("geheim123");
        verify(benutzerRepository).save(any(Benutzer.class));
    }

    @Test
    void registrieren_sollExceptionWerfen_wennUsernameBereitsExistiert() {
        RegisterRequest request = new RegisterRequest("vergeben", "geheim123");
        when(benutzerRepository.existsByUsername("vergeben")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> customUserDetailsService.registrieren(request));
        verify(benutzerRepository, never()).save(any());
    }
}
