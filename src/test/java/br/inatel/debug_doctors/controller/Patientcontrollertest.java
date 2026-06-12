package br.inatel.debug_doctors.controller;

import br.inatel.debug_doctors.domain.patient.Patient;
import br.inatel.debug_doctors.dto.PatientDTO;
import br.inatel.debug_doctors.service.PatientService;
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
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

    @InjectMocks
    private PatientController patientController;

    @Mock
    private PatientService patientService;

    // --- TESTE DE GET ALL ---
    @Test
    void shouldReturnAllPatientsAndStatus200() {
        // Simula o serviço retornando uma lista vazia
        Mockito.when(patientService.findAll()).thenReturn(Collections.emptyList());

        // Chama o método diretamente no controller
        ResponseEntity<List<PatientDTO>> response = patientController.getAllPatients();

        // Verifica se o status é 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // --- TESTE DE POST (CREATE ERRO) ---
    @Test
    void shouldReturn400WhenCreatePatientFails() {
        // Criamos um mock do DTO para evitar erros ao chamar o .toEntity()
        PatientDTO mockDto = Mockito.mock(PatientDTO.class);
        Mockito.when(mockDto.toEntity()).thenReturn(new Patient());

        // Simulamos o serviço lançando a exceção
        Mockito.when(patientService.save(any()))
                .thenThrow(new IllegalArgumentException("Dados inválidos"));

        ResponseEntity<?> response = patientController.createPatient(mockDto);

        // Verifica se o catch funcionou e retornou 400 Bad Request
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Dados inválidos", response.getBody());
    }

    // --- TESTE DE GET BY ID (NOT FOUND) ---
    @Test
    void shouldReturn404WhenPatientNotFoundById() {
        Mockito.when(patientService.findById(99L))
                .thenThrow(new IllegalArgumentException("Paciente não encontrado"));

        ResponseEntity<?> response = patientController.getPatientById(99L);

        // Verifica se o catch funcionou e retornou 404 Not Found
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Paciente não encontrado", response.getBody());
    }

    // --- TESTE DE PUT (UPDATE ERRO) ---
    @Test
    void shouldReturn400WhenUpdatePatientFails() {
        PatientDTO mockDto = Mockito.mock(PatientDTO.class);
        Mockito.when(mockDto.toEntity()).thenReturn(new Patient());

        Mockito.when(patientService.update(eq(1L), any()))
                .thenThrow(new IllegalArgumentException("Erro ao atualizar"));

        ResponseEntity<?> response = patientController.updatePatient(1L, mockDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro ao atualizar", response.getBody());
    }

    // --- TESTE DE DELETE ---
    @Test
    void shouldDeletePatientAndReturn204() {
        Mockito.doNothing().when(patientService).delete(1L);

        ResponseEntity<?> response = patientController.deletePatient(1L);

        // Verifica se o status é 204 No Content
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldReturn404WhenDeletePatientFails() {
        Mockito.doThrow(new IllegalArgumentException("Paciente não encontrado"))
                .when(patientService).delete(99L);

        ResponseEntity<?> response = patientController.deletePatient(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Paciente não encontrado", response.getBody());
    }
}