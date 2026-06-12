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

    @Test
    void shouldReturnAllPatientsAndStatus200() {
        Mockito.when(patientService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<PatientDTO>> response = patientController.getAllPatients();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldReturn400WhenCreatePatientFails() {
        PatientDTO mockDto = Mockito.mock(PatientDTO.class);
        Mockito.when(mockDto.toEntity()).thenReturn(new Patient());

        Mockito.when(patientService.save(any()))
                .thenThrow(new IllegalArgumentException("Dados inválidos"));

        ResponseEntity<?> response = patientController.createPatient(mockDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Dados inválidos", response.getBody());
    }

    @Test
    void shouldReturn404WhenPatientNotFoundById() {
        Mockito.when(patientService.findById(99L))
                .thenThrow(new IllegalArgumentException("Paciente não encontrado"));

        ResponseEntity<?> response = patientController.getPatientById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Paciente não encontrado", response.getBody());
    }

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

    @Test
    void shouldDeletePatientAndReturn204() {
        Mockito.doNothing().when(patientService).delete(1L);

        ResponseEntity<?> response = patientController.deletePatient(1L);

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

    @Test
    void shouldReturnAllPatientsWithDataAndStatus200() {
        Patient patient = new Patient(1L, "Carlos Silva", "11122233344", "carlos@email.com");
        Mockito.when(patientService.findAll()).thenReturn(List.of(patient));

        ResponseEntity<List<PatientDTO>> response = patientController.getAllPatients();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size()); // Garante que a lista não veio vazia
    }

    @Test
    void shouldCreatePatientSuccessfullyAndReturn201() {
        Patient patient = new Patient(1L, "Carlos Silva", "11122233344", "carlos@email.com");

        PatientDTO mockDto = Mockito.mock(PatientDTO.class);
        Mockito.when(mockDto.toEntity()).thenReturn(patient);

        Mockito.when(patientService.save(any(Patient.class))).thenReturn(patient);

        ResponseEntity<?> response = patientController.createPatient(mockDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldReturnPatientByIdSuccessfullyAndReturn200() {
        Patient patient = new Patient(1L, "Carlos Silva", "11122233344", "carlos@email.com");

        Mockito.when(patientService.findById(1L)).thenReturn(patient);

        ResponseEntity<?> response = patientController.getPatientById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldUpdatePatientSuccessfullyAndReturn200() {
        Patient patient = new Patient(1L, "Carlos Silva", "11122233344", "carlos@email.com");

        PatientDTO mockDto = Mockito.mock(PatientDTO.class);
        Mockito.when(mockDto.toEntity()).thenReturn(patient);

        // Simulamos a atualização dando certo
        Mockito.when(patientService.update(eq(1L), any(Patient.class))).thenReturn(patient);

        ResponseEntity<?> response = patientController.updatePatient(1L, mockDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}