package br.inatel.debug_doctors.model;

import br.inatel.debug_doctors.domain.patient.Patient;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    @Test
    void shouldCreatePatientWithCorrectData() {
        // Arrange
        Patient patient = new Patient();
        patient.setName("John Silva");
        patient.setCpf("123.456.789-00");
        patient.setEmail("john@email.com");

        // Assert
        assertEquals("John Silva", patient.getName());
        assertEquals("123.456.789-00", patient.getCpf());
        assertEquals("john@email.com", patient.getEmail());
    }

    @Test
    void shouldUpdatePatientName() {
        // Arrange
        Patient patient = new Patient();
        patient.setName("John Silva");

        // Act
        patient.setName("Carlos Souza");

        // Assert
        assertEquals("Carlos Souza", patient.getName());
    }

    @Test
    void shouldUpdatePatientEmail() {
        // Arrange
        Patient patient = new Patient();
        patient.setEmail("john@email.com");

        // Act
        patient.setEmail("carlos@email.com");

        // Assert
        assertEquals("carlos@email.com", patient.getEmail());
    }

    @Test
    void shouldCreateEmptyPatientWithDefaultConstructor() {
        // Arrange + Act
        Patient patient = new Patient();

        // Assert
        assertNull(patient.getName());
        assertNull(patient.getCpf());
        assertNull(patient.getEmail());
    }
}