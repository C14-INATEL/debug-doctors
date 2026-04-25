package br.inatel.debug_doctors.model;

import br.inatel.debug_doctors.domain.patient.Patient;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatientMockTest {

    // Fake subclass that overrides Patient behavior for testing

    private static class PatientMock extends Patient {
        @Override
        public String getName() {
            return "Mocked Patient Name";
        }
        @Override
        public String getEmail() {
            return "mocked@email.com";
        }
        @Override
        public String getCpf() {
            return "000.000.000-00";
        }
    }
    @Test
    void shouldReturnMockedPatientName() {
        Patient fakePatient = new PatientMock();

        assertEquals("Mocked Patient Name", fakePatient.getName(),
                "The mock should return the fake name");
    }

    @Test
    void shouldReturnMockedPatientEmailAndCpf() {
        Patient fakePatient = new PatientMock();

        assertEquals("mocked@email.com", fakePatient.getEmail(),
                "The mock should return the fake email");
        assertEquals("000.000.000-00", fakePatient.getCpf(),
                "The mock should return the fake cpf");
    }
}