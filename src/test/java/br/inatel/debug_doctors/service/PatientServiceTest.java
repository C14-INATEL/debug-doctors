package br.inatel.debug_doctors.service;

import br.inatel.debug_doctors.domain.patient.Patient;
import br.inatel.debug_doctors.repositories.PatientRepository;
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
class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Test
    void shouldFindAllPatients() {
        // Simula o banco de dados retornando uma lista com um paciente
        Mockito.when(patientRepository.findAll()).thenReturn(List.of(new Patient()));

        List<Patient> result = patientService.findAll();

        assertFalse(result.isEmpty());
    }

    @Test
    void shouldFindPatientByIdSuccessfully() {
        Patient patient = new Patient();
        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        Patient result = patientService.findById(1L);

        assertNotNull(result);
    }

    @Test
    void shouldThrowExceptionWhenPatientNotFoundById() {
        // Simula o banco não encontrando o paciente (Optional vazio)
        Mockito.when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        // Verifica se a exceção com a mensagem exata foi lançada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            patientService.findById(99L);
        });
        assertEquals("Patient not found with id: 99", exception.getMessage());
    }

    @Test
    void shouldSavePatientSuccessfully() {
        Patient patient = new Patient();
        Mockito.when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient result = patientService.save(patient);

        assertNotNull(result);
    }

    @Test
    void shouldUpdatePatientSuccessfully() {
        Patient existingPatient = new Patient();

        Patient updatedDetails = new Patient();
        updatedDetails.setName("Nome Atualizado");

        // Simula a busca do paciente e depois o salvamento
        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.of(existingPatient));
        Mockito.when(patientRepository.save(any(Patient.class))).thenReturn(existingPatient);

        Patient result = patientService.update(1L, updatedDetails);

        assertNotNull(result);
        assertEquals("Nome Atualizado", existingPatient.getName()); // Confirma que o dado foi alterado
    }

    @Test
    void shouldDeletePatientSuccessfully() {
        Patient patient = new Patient();
        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        patientService.delete(1L);

        // Verifica se o método delete do repositório foi realmente chamado
        verify(patientRepository).delete(patient);
    }
}