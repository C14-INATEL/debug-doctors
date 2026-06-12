package br.inatel.debug_doctors.controller;

import br.inatel.debug_doctors.domain.doctor.Doctor;
import br.inatel.debug_doctors.dto.DoctorDTO;
import br.inatel.debug_doctors.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class DoctorControllerTest {

    @InjectMocks
    private DoctorController doctorController;

    @Mock
    private DoctorService doctorService;

    // --- TESTE DE GET ALL ---
    @Test
    void shouldReturnAllDoctorsAndStatus200() {
        // Simula o serviço retornando uma lista vazia
        Mockito.when(doctorService.findAll()).thenReturn(Collections.emptyList());

        // Chama o método diretamente no controller
        ResponseEntity<List<DoctorDTO>> response = doctorController.getAllDoctors();

        // Verifica se o status é 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // --- TESTE DE POST (CREATE ERRO) ---
    @Test
    void shouldReturn400WhenCreateDoctorFails() {
        // Criamos um mock do DTO para evitar erros ao chamar o .toEntity()
        DoctorDTO mockDto = Mockito.mock(DoctorDTO.class);
        Mockito.when(mockDto.toEntity()).thenReturn(new Doctor());

        // Simulamos o serviço lançando a exceção
        Mockito.when(doctorService.save(any()))
                .thenThrow(new IllegalArgumentException("Dados inválidos"));

        ResponseEntity<?> response = doctorController.createDoctor(mockDto);

        // Verifica se o catch funcionou e retornou 400 Bad Request
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Dados inválidos", response.getBody());
    }

    // --- TESTE DE GET BY ID (NOT FOUND) ---
    @Test
    void shouldReturn404WhenDoctorNotFoundById() {
        Mockito.when(doctorService.findById(99L))
                .thenThrow(new IllegalArgumentException("Médico não encontrado"));

        ResponseEntity<?> response = doctorController.getDoctorById(99L);

        // Verifica se o catch funcionou e retornou 404 Not Found
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Médico não encontrado", response.getBody());
    }

    // --- TESTE DE DELETE ---
    @Test
    void shouldDeleteDoctorAndReturn204() {
        Mockito.doNothing().when(doctorService).delete(1L);

        ResponseEntity<?> response = doctorController.deleteDoctor(1L);

        // Verifica se o status é 204 No Content
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldReturn404WhenDeleteDoctorFails() {
        Mockito.doThrow(new IllegalArgumentException("Médico não encontrado"))
                .when(doctorService).delete(99L);

        ResponseEntity<?> response = doctorController.deleteDoctor(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Médico não encontrado", response.getBody());
    }
}