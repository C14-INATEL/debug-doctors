package br.inatel.debug_doctors.domain;

import br.inatel.debug_doctors.domain.patient.Patient;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PatientTest {

    @Test
    void shouldCreatePatientSuccessfully() {
        String validName = "Wagner Dourado";
        String validCpf = "123.456.789-00";
        String validEmail = "wagner@example.com";

        Patient patient = new Patient();
        patient.setName(validName);
        patient.setCpf(validCpf);
        patient.setEmail(validEmail);

        assertAll("Verify if patient attributes were set correctly",
                () -> assertNotNull(patient, "Patient should not be null"),
                () -> assertEquals(validName, patient.getName(), "Name should match the assigned value"),
                () -> assertEquals(validCpf, patient.getCpf(), "CPF should match the assigned value"),
                () -> assertEquals(validEmail, patient.getEmail(), "Email should match the assigned value"));
    }
}