package br.inatel.debug_doctors.model;

import br.inatel.debug_doctors.domain.patient.Patient;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    @Test
    void deveCriarPacienteComDadosCorretos() {
        // Arrange
        Patient patient = new Patient(1L, "João Silva", "123.456.789-00", "joao@email.com");

        // Assert
        assertEquals("João Silva", patient.getName());
        assertEquals("123.456.789-00", patient.getCpf());
        assertEquals("joao@email.com", patient.getEmail());
    }

    @Test
    void deveAlterarNomeDoPaciente() {
        // Arrange
        Patient patient = new Patient(1L, "João Silva", "123.456.789-00", "joao@email.com");

        // Act
        patient.setName("Carlos Souza");

        // Assert
        assertEquals("Carlos Souza", patient.getName());
    }

    @Test
    void deveAlterarEmailDoPaciente() {
        // Arrange
        Patient patient = new Patient(1L, "João Silva", "123.456.789-00", "joao@email.com");

        // Act
        patient.setEmail("carlos@email.com");

        // Assert
        assertEquals("carlos@email.com", patient.getEmail());
    }

    @Test
    void deveCriarPacienteVazioComConstrutorPadrao() {
        // Arrange + Act
        Patient patient = new Patient();

        // Assert
        assertNull(patient.getName());
        assertNull(patient.getCpf());
        assertNull(patient.getEmail());
    }
}