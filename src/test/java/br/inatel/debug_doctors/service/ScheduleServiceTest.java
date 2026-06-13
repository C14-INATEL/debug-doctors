package br.inatel.debug_doctors.service;

import br.inatel.debug_doctors.domain.doctor.Doctor;
import br.inatel.debug_doctors.domain.patient.Patient;
import br.inatel.debug_doctors.domain.schedule.Schedule;
import br.inatel.debug_doctors.repositories.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private DoctorService doctorService;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private ScheduleService scheduleService;

    @Test
    void shouldFindAll() {
        when(scheduleRepository.findAll()).thenReturn(List.of(new Schedule()));
        List<Schedule> list = scheduleService.findAll();
        assertFalse(list.isEmpty());
    }

    @Test
    void shouldFindById() {
        Schedule schedule = new Schedule();
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        Schedule result = scheduleService.findById(1L);
        assertNotNull(result);
    }

    @Test
    void shouldThrowWhenIdNotFound() {
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> scheduleService.findById(1L));
    }

    @Test
    void shouldCreateSchedule() {

        Doctor mockDoctor = mock(Doctor.class);
        Patient mockPatient = mock(Patient.class);

        when(doctorService.findById(1L)).thenReturn(mockDoctor);
        when(patientService.findById(2L)).thenReturn(mockPatient);
        when(scheduleRepository.findAll()).thenReturn(List.of());
        when(scheduleRepository.save(any(Schedule.class))).thenAnswer(i -> i.getArguments()[0]);

        Schedule result = scheduleService.createSchedule(1L, 2L, LocalDateTime.now().plusDays(2), "Checkup");

        assertNotNull(result);
        verify(scheduleRepository).save(any(Schedule.class));
    }

    @Test
    void shouldConfirmSchedule() {
        Schedule mockSchedule = mock(Schedule.class);
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(mockSchedule));
        when(scheduleRepository.save(mockSchedule)).thenReturn(mockSchedule);

        scheduleService.confirmSchedule(1L);

        verify(mockSchedule).confirmSchedule();
        verify(scheduleRepository).save(mockSchedule);
    }

    @Test
    void shouldCancelSchedule() {
        Schedule mockSchedule = mock(Schedule.class);
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(mockSchedule));
        when(scheduleRepository.save(mockSchedule)).thenReturn(mockSchedule);

        scheduleService.cancelSchedule(1L, "Cancelamento do paciente");

        verify(mockSchedule).cancelSchedule("Cancelamento do paciente");
        verify(scheduleRepository).save(mockSchedule);
    }
}