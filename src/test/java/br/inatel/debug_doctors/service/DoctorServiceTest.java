package br.inatel.debug_doctors.service;

import br.inatel.debug_doctors.domain.doctor.Doctor;
import br.inatel.debug_doctors.repositories.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @InjectMocks
    private DoctorService doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @Test
    void shouldFindAllDoctors() {
        Mockito.when(doctorRepository.findAll()).thenReturn(List.of(new Doctor()));

        List<Doctor> result = doctorService.findAll();

        assertFalse(result.isEmpty());
    }

    @Test
    void shouldFindDoctorByIdSuccessfully() {
        Doctor doctor = new Doctor();
        Mockito.when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        Doctor result = doctorService.findById(1L);

        assertNotNull(result);
    }

    @Test
    void shouldThrowExceptionWhenDoctorNotFoundById() {
        Mockito.when(doctorRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            doctorService.findById(99L);
        });
        assertEquals("Doctor not found with id: 99", exception.getMessage());
    }

    @Test
    void shouldSaveDoctorSuccessfully() {
        Doctor doctor = new Doctor();
        Mockito.when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        Doctor result = doctorService.save(doctor);

        assertNotNull(result);
    }

    @Test
    void shouldUpdateDoctorSuccessfully() {
        Doctor existingDoctor = new Doctor();

        Doctor updatedDetails = new Doctor();
        updatedDetails.setName("Dr. Atualizado");

        Mockito.when(doctorRepository.findById(1L)).thenReturn(Optional.of(existingDoctor));
        Mockito.when(doctorRepository.save(any(Doctor.class))).thenReturn(existingDoctor);

        Doctor result = doctorService.update(1L, updatedDetails);

        assertNotNull(result);
        assertEquals("Dr. Atualizado", existingDoctor.getName());
    }

    @Test
    void shouldDeleteDoctorSuccessfully() {
        Doctor doctor = new Doctor();
        Mockito.when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        doctorService.delete(1L);

        verify(doctorRepository).delete(doctor);
    }
}