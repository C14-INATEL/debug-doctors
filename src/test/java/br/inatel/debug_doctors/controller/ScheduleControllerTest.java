package br.inatel.debug_doctors.controller;

import br.inatel.debug_doctors.domain.schedule.Schedule;
import br.inatel.debug_doctors.dto.CancelRequestDTO;
import br.inatel.debug_doctors.dto.ScheduleRequestDTO;
import br.inatel.debug_doctors.dto.ScheduleResponseDTO;
import br.inatel.debug_doctors.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ScheduleControllerTest {

    @Mock
    private ScheduleService scheduleService;

    @InjectMocks
    private ScheduleController scheduleController;

    @Test
    void shouldReturnAllSchedulesAndStatus200() {
        // Arrange
        Mockito.when(scheduleService.findAll()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<ScheduleResponseDTO>> response = scheduleController.getAllSchedules();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldCreateScheduleSuccessfullyAndReturn201() {
        // Arrange
        Schedule mockSchedule = Mockito.mock(Schedule.class);
        ScheduleRequestDTO request = new ScheduleRequestDTO(1L, 2L,
                LocalDateTime.now().plusDays(1), "Routine checkup");

        Mockito.when(scheduleService.createSchedule(
                request.getDoctorId(),
                request.getPatientId(),
                request.getDateTime(),
                request.getDescription()
        )).thenReturn(mockSchedule);

        // Act
        ResponseEntity<?> response = scheduleController.createSchedule(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void shouldReturn400WhenCreateScheduleFails() {
        // Arrange
        ScheduleRequestDTO request = new ScheduleRequestDTO(1L, 2L,
                LocalDateTime.now().plusDays(1), "Routine checkup");

        Mockito.when(scheduleService.createSchedule(
                request.getDoctorId(),
                request.getPatientId(),
                request.getDateTime(),
                request.getDescription()
        )).thenThrow(new IllegalArgumentException("Horário inválido"));

        // Act
        ResponseEntity<?> response = scheduleController.createSchedule(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Horário inválido", response.getBody());
    }

    @Test
    void shouldReturnScheduleByIdAndStatus200() {
        // Arrange
        Schedule mockSchedule = Mockito.mock(Schedule.class);
        Mockito.when(scheduleService.findById(1L)).thenReturn(mockSchedule);

        // Act
        ResponseEntity<?> response = scheduleController.getScheduleById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldReturn404WhenScheduleNotFoundById() {
        // Arrange
        Mockito.when(scheduleService.findById(99L))
                .thenThrow(new IllegalArgumentException("Agendamento não encontrado"));

        // Act
        ResponseEntity<?> response = scheduleController.getScheduleById(99L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Agendamento não encontrado", response.getBody());
    }

    @Test
    void shouldCancelScheduleSuccessfullyAndReturn200() {
        // Arrange
        Schedule mockSchedule = Mockito.mock(Schedule.class);
        CancelRequestDTO cancelRequest = new CancelRequestDTO("Paciente adoeceu");

        Mockito.when(scheduleService.cancelSchedule(1L, "Paciente adoeceu"))
                .thenReturn(mockSchedule);

        // Act
        ResponseEntity<?> response = scheduleController.cancelSchedule(1L, cancelRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldReturn404WhenCancelScheduleNotFound() {
        // Arrange
        CancelRequestDTO cancelRequest = new CancelRequestDTO("Paciente adoeceu");

        Mockito.when(scheduleService.cancelSchedule(99L, "Paciente adoeceu"))
                .thenThrow(new IllegalArgumentException("Agendamento não encontrado"));

        // Act
        ResponseEntity<?> response = scheduleController.cancelSchedule(99L, cancelRequest);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Agendamento não encontrado", response.getBody());
    }

    @Test
    void shouldReturn400WhenCancelAlreadyCanceledSchedule() {
        // Arrange
        CancelRequestDTO cancelRequest = new CancelRequestDTO("Motivo 2");

        Mockito.when(scheduleService.cancelSchedule(1L, "Motivo 2"))
                .thenThrow(new IllegalStateException("Schedule is already canceled."));

        // Act
        ResponseEntity<?> response = scheduleController.cancelSchedule(1L, cancelRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Schedule is already canceled.", response.getBody());
    }

    @Test
    void shouldConfirmScheduleSuccessfullyAndReturn200() {
        // Arrange
        Schedule mockSchedule = Mockito.mock(Schedule.class);
        Mockito.when(scheduleService.confirmSchedule(1L)).thenReturn(mockSchedule);

        // Act
        ResponseEntity<?> response = scheduleController.confirmSchedule(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldReturn404WhenConfirmScheduleNotFound() {
        // Arrange
        Mockito.when(scheduleService.confirmSchedule(99L))
                .thenThrow(new IllegalArgumentException("Agendamento não encontrado"));

        // Act
        ResponseEntity<?> response = scheduleController.confirmSchedule(99L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Agendamento não encontrado", response.getBody());
    }
}