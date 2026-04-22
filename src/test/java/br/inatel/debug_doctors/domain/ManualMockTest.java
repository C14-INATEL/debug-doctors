package br.inatel.debug_doctors.domain;

import br.inatel.debug_doctors.domain.doctor.Doctor;
import br.inatel.debug_doctors.domain.patient.Patient;
import br.inatel.debug_doctors.domain.schedule.Schedule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SimpleManualMockTest {

    // --- 1. Manual Mocks Setup ---
    // Creating fake inner classes to override behavior for testing.

    private static class PatientMock extends Patient {
        @Override
        public String getName() {
            return "Mocked Patient Name";
        }
    }

    private static class DoctorMock extends Doctor {
        @Override
        public String getName() {
            return "Mocked Doctor Name";
        }
    }

    // --- 2. Tests ---

    @Test
    void testMockNumberOne_shouldValidateFakePatientBehavior() {
        // Arrange & Act: Instantiate the manual mock
        Patient fakePatient = new PatientMock();

        // Assert: Verify if the mock returns the programmed fake data
        assertEquals("Mocked Patient Name", fakePatient.getName(), "The mock should return the fake name");
    }

    @Test
    void testMockNumberTwo_shouldCreateScheduleWithFakeDependencies() {
        // Arrange: Prepare mocks and valid data
        Patient fakePatient = new PatientMock();
        Doctor fakeDoctor = new DoctorMock();
        LocalDateTime validDate = LocalDateTime.now().plusDays(5);

        // Act: Inject manual mocks into Schedule creation
        Schedule schedule = Schedule.createSchedule(fakePatient, fakeDoctor, validDate, "Fake Schedule", List.of());

        // Assert: Verify if the schedule correctly stored the mocked instances
        assertNotNull(schedule, "The schedule should not be null");
        assertEquals("Mocked Patient Name", schedule.getPatient().getName(), "The saved patient must be the mock");
        assertEquals("Mocked Doctor Name", schedule.getDoctor().getName(), "The saved doctor must be the mock");
    }
}