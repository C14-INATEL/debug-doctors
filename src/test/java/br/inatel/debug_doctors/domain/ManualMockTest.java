package br.inatel.debug_doctors.domain;

import br.inatel.debug_doctors.domain.doctor.Doctor;
import br.inatel.debug_doctors.domain.patient.Patient;
import br.inatel.debug_doctors.domain.schedule.Schedule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ManualMockTest {

    private static class PatientMock extends Patient {
        @Override
        public String getName() {
            return "Mocked Patient";
        }
    }

    private static class DoctorMock extends Doctor {
        @Override
        public String getName() {
            return "Mocked Doctor";
        }
    }

    @Test
    void shouldReturnMockedPatientName() {
        Patient mockedPatient = new PatientMock();

        assertEquals("Mocked Patient", mockedPatient.getName());
    }

    @Test
    void shouldCreateScheduleWithMockedDependencies() {
        Patient mockedPatient = new PatientMock();
        Doctor mockedDoctor = new DoctorMock();
        LocalDateTime scheduleDate = LocalDateTime.now().plusDays(5);
        String description = "Routine Checkup";

        Schedule createdSchedule = Schedule.createSchedule(mockedPatient, mockedDoctor, scheduleDate, description);

        assertNotNull(createdSchedule);
        assertEquals("Mocked Patient", createdSchedule.getPatient().getName());
        assertEquals("Mocked Doctor", createdSchedule.getDoctor().getName());
    }
}