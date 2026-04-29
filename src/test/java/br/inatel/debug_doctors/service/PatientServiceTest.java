package br.inatel.debug_doctors.service;

import br.inatel.debug_doctors.domain.patient.Patient;
import br.inatel.debug_doctors.repository.PatientRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {

        PatientRepository repository = mock(PatientRepository.class);
        PatientService service = new PatientService(repository);

        Patient patient = new Patient();
        patient.setEmail("emailinvalido.com");
        patient.setCpf("123.456.789-00");


        assertThrows(IllegalArgumentException.class, () -> {
            service.save(patient);
        });


        verify(repository, never()).save(any());
    }

    @Test
    void shouldSavePatientWhenDataIsValid() {

        PatientRepository repository = mock(PatientRepository.class);
        PatientService service = new PatientService(repository);

        Patient patient = new Patient();
        patient.setEmail("teste@email.com");
        patient.setCpf("123.456.789-00");


        service.save(patient);


        verify(repository, times(1)).save(patient);
    }
}