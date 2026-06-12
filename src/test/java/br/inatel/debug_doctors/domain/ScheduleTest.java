package br.inatel.debug_doctors.domain;

import br.inatel.debug_doctors.domain.doctor.Doctor;
import br.inatel.debug_doctors.domain.patient.Patient;
import br.inatel.debug_doctors.domain.schedule.Schedule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleTest {

    // Mockito cria os mocks automaticamente via @Mock
    @Mock
    private Patient mockedPatient;

    @Mock
    private Doctor mockedDoctor;

    // -------------------------------------------------------------------------
    // Testes com objetos reais — validam a lógica interna do Schedule
    // -------------------------------------------------------------------------

    @Test
    void testNewSchedule() {
        // Arrange: usa mocks no lugar de instâncias reais de Patient e Doctor
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        String description = "Routine Checkup";

        // Act
        Schedule schedule = Schedule.createSchedule(mockedPatient, mockedDoctor, dateTime, description, List.of());

        // Assert
        assertNotNull(schedule);
        assertEquals(mockedPatient, schedule.getPatient());
        assertEquals(mockedDoctor, schedule.getDoctor());
        assertEquals(dateTime, schedule.getDateTime());
        assertEquals(description, schedule.getDescription());
        assertFalse(schedule.isConfirmed());
    }

    @Test
    void confirmSchedule() {
        // Arrange
        Schedule schedule = Schedule.createSchedule(
                mockedPatient, mockedDoctor,
                LocalDateTime.now().plusDays(1),
                "Routine Checkup", List.of()
        );

        // Act
        schedule.confirmSchedule();

        // Assert
        assertTrue(schedule.isConfirmed());
    }

    @Test
    void shouldAllowReschedulingByUpdatingDateTime() {
        // Arrange
        Schedule schedule = new Schedule();
        LocalDateTime originalTime = LocalDateTime.of(2026, 4, 15, 14, 0);
        schedule.setDateTime(originalTime);

        // Act
        LocalDateTime newTime = LocalDateTime.of(2026, 4, 20, 16, 30);
        schedule.setDateTime(newTime);

        // Assert
        assertEquals(newTime, schedule.getDateTime(),
                "The schedule date and time should be updated to the new time");
    }

    @Test
    void cannotAllowScheduleInThePast() {
        // Arrange
        LocalDateTime pastDateTime = LocalDateTime.now().minusDays(1);

        // Act & Assert: a lógica de validação de data fica no Schedule.createSchedule
        assertThrows(IllegalArgumentException.class, () ->
                Schedule.createSchedule(mockedPatient, mockedDoctor, pastDateTime, "Routine Checkup", List.of())
        );
    }

    @Test
    void cannotAllowOverlappingSchedules() {
        // Arrange: cria um agendamento existente e tenta criar outro no mesmo horário
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);

        Schedule existingSchedule = Schedule.createSchedule(
                mockedPatient, mockedDoctor, dateTime, "Routine Checkup", List.of()
        );
        List<Schedule> doctorSchedules = List.of(existingSchedule);

        // Act & Assert: deve lançar exceção por conflito de horário
        assertThrows(IllegalArgumentException.class, () ->
                Schedule.createSchedule(mockedPatient, mockedDoctor, dateTime, "Routine Checkup", doctorSchedules)
        );
    }

    @Test
    void shouldCancelScheduleSuccessfully() {
        // Arrange
        Schedule schedule = Schedule.createSchedule(
                mockedPatient, mockedDoctor,
                LocalDateTime.now().plusDays(2),
                "Routine", List.of()
        );

        // Act
        schedule.cancelSchedule("Paciente adoeceu");

        // Assert
        assertTrue(schedule.isCanceled());
        assertEquals("Paciente adoeceu", schedule.getCancellationReason());
        assertFalse(schedule.isConfirmed());
    }

    @Test
    void cannotCancelAlreadyCanceledSchedule() {
        Schedule schedule = Schedule.createSchedule(
                mockedPatient, mockedDoctor,
                LocalDateTime.now().plusDays(2),
                "Routine", List.of()
        );
        schedule.cancelSchedule("Primeiro cancelamento");

        // Act & Assert: segundo cancelamento deve lançar exceção
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                schedule.cancelSchedule("Motivo 2")
        );
        assertEquals("Schedule is already canceled.", exception.getMessage());
    }

    @Test
    void cannotCreateScheduleWithoutPatient() {
        // Arrange
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Schedule.createSchedule(null, mockedDoctor, dateTime, "Routine", List.of())
        );
        assertEquals("Patient cannot be null.", exception.getMessage());
    }

    @Test
    void cannotCreateScheduleWithoutDoctor() {
        // Arrange
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Schedule.createSchedule(mockedPatient, null, dateTime, "Routine", List.of())
        );
        assertEquals("Doctor cannot be null.", exception.getMessage());
    }

    // -------------------------------------------------------------------------
    // Testes extras com Mockito — simulam comportamento de dependências
    // -------------------------------------------------------------------------

    @Test
    void shouldVerifyDoctorAndPatientInteractionOnScheduleCreation() {
        // Arrange: configura retornos nos mocks para simular dados reais
        when(mockedPatient.getId()).thenReturn(1L);
        when(mockedDoctor.getId()).thenReturn(2L);

        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);

        // Act
        Schedule schedule = Schedule.createSchedule(
                mockedPatient, mockedDoctor, dateTime, "Consulta de rotina", List.of()
        );

        // Assert: verifica que o schedule recebeu os objetos mockados corretamente
        assertNotNull(schedule);
        assertEquals(1L, schedule.getPatient().getId());
        assertEquals(2L, schedule.getDoctor().getId());

        // Verifica que os IDs foram consultados
        verify(mockedPatient, times(1)).getId();
        verify(mockedDoctor, times(1)).getId();
    }

    @Test
    void shouldNotInteractWithDoctorOrPatientWhenScheduleIsInThePast() {
        // Arrange
        LocalDateTime pastDate = LocalDateTime.now().minusDays(1);

        // Act & Assert: exceção lançada antes de qualquer interação com Patient/Doctor
        assertThrows(IllegalArgumentException.class, () ->
                Schedule.createSchedule(mockedPatient, mockedDoctor, pastDate, "Rotina", List.of())
        );

        // Garante que nenhum getter foi chamado nos mocks — a validação falhou cedo
        verifyNoInteractions(mockedPatient);
        verifyNoInteractions(mockedDoctor);
    }
}
