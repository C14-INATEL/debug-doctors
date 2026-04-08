package br.inatel.debug_doctors.domain;

import br.inatel.debug_doctors.domain.doctor.Doctor;
import br.inatel.debug_doctors.domain.patient.Patient;
import br.inatel.debug_doctors.domain.schedule.Schedule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.time.LocalDateTime;
import java.util.List;

class ScheduleTest {

    @Test
    void testNewSchedule() {
        Patient patient = new Patient();
        Doctor doctor = new Doctor();
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        String description = "Routine Checkup";

        Schedule schedule = Schedule.createSchedule(patient, doctor, dateTime, description, List.of());

        Assertions.assertNotNull(schedule);
        Assertions.assertEquals(patient, schedule.getPatient());
        Assertions.assertEquals(doctor, schedule.getDoctor());
        Assertions.assertEquals(dateTime, schedule.getDateTime());
        Assertions.assertEquals(description, schedule.getDescription());
        Assertions.assertFalse(schedule.isConfirmed());
    }

    @Test
    void confirmSchedule() {

        Schedule schedule = Schedule.createSchedule(new Patient(), new Doctor(), LocalDateTime.now().plusDays(1),
                "Routine Checkup", List.of());

        schedule.confirmSchedule();

        Assertions.assertTrue(schedule.isConfirmed());
    }

    @Test
    void shouldAllowReschedulingByUpdatingDateTime() {
        // Arrange: Create the schedule with an initial date
        Schedule schedule = new Schedule();
        java.time.LocalDateTime originalTime = java.time.LocalDateTime.of(2026, 4, 15, 14, 0);
        schedule.setDateTime(originalTime);

        // Act: Change to a new date (Rescheduling)
        java.time.LocalDateTime newTime = java.time.LocalDateTime.of(2026, 4, 20, 16, 30);
        schedule.setDateTime(newTime);

        // Assert: Ensure the saved date is the new date
        Assertions.assertEquals(newTime, schedule.getDateTime(),
                "The schedule date and time should be updated to the new time");
    }

    @Test
    void cannotAllowScheduleInThePast() {
        Patient patient = new Patient();
        Doctor doctor = new Doctor();
        LocalDateTime dateTime = LocalDateTime.now().minusDays(1);
        String description = "Routine Checkup";

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Schedule.createSchedule(patient, doctor, dateTime, description, List.of());
        });

    }

    @Test
    void cannotAllowOverlappingSchedules() {
        Patient patient = new Patient();
        Doctor doctor = new Doctor();
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        String description = "Routine Checkup";

        Schedule existingSchedule = Schedule.createSchedule(patient, doctor, dateTime, "Routine Checkup", List.of());
        List<Schedule> doctorSchedules = List.of(existingSchedule);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Schedule.createSchedule(patient, doctor, dateTime, description, doctorSchedules);
        });
    }

}
