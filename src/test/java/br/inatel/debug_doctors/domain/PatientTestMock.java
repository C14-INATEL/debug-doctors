package br.inatel.debug_doctors.model;

import br.inatel.debug_doctors.domain.patient.Patient;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatientMockTest {

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
    void ReturnMockedPatientName() {
        Patient fakePatient = new PatientMock();

        assertEquals("Mocked Patient Name", fakePatient.getName(),
                "The mock should return the fake name");
    }

    @Test
    void ReturnMockedPatientInfo() {
        Patient fakePatient = new PatientMock();

        assertEquals("mocked@email.com", fakePatient.getEmail(),
                "The mock should return the fake email");
        assertEquals("000.000.000-00", fakePatient.getCpf(),
                "The mock should return the fake cpf");
    }
}