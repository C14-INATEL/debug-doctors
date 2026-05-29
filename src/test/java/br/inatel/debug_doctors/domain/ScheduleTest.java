package br.inatel.debug_doctors.domain;

import br.inatel.debug_doctors.domain.doctor.Doctor;
import br.inatel.debug_doctors.domain.patient.Patient;
import br.inatel.debug_doctors.domain.schedule.Schedule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @Test
    void shouldCancelScheduleSuccessfully() {

        Schedule schedule = Schedule.createSchedule(new Patient(), new Doctor(), LocalDateTime.now().plusDays(2),
                "Routine", List.of());

        schedule.cancelSchedule("Paciente adoeceu");

        Assertions.assertTrue(schedule.isCanceled());
        Assertions.assertEquals("Paciente adoeceu", schedule.getCancellationReason());
        Assertions.assertFalse(schedule.isConfirmed());
    }

    @Test
    void cannotCancelAlreadyCanceledSchedule() {

        Schedule schedule = Schedule.createSchedule(new Patient(), new Doctor(), LocalDateTime.now().plusDays(2),
                "Routine", List.of());
        schedule.cancelSchedule("Motivo 1");

        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            schedule.cancelSchedule("Motivo 2");
        });
        Assertions.assertEquals("Schedule is already canceled.", exception.getMessage());
    }

    @Test
    void cannotCreateScheduleWithoutPatient() {

        Doctor doctor = new Doctor();
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Schedule.createSchedule(null, doctor, dateTime, "Routine", List.of());
        });
        Assertions.assertEquals("Patient cannot be null.", exception.getMessage());
    }

    @Test
    void cannotCreateScheduleWithoutDoctor() {

        Patient patient = new Patient();
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Schedule.createSchedule(patient, null, dateTime, "Routine", List.of());
        });
        Assertions.assertEquals("Doctor cannot be null.", exception.getMessage());
    }

    @Test
<<<<<<< HEAD
    void shouldThrowWhenScheduleIsOutsideDoctorShift() {
        Patient patient = new Patient();
        Doctor doctor = new Doctor();
        // Shift is 08:00 to 12:00
        doctor.setShiftStart(java.time.LocalTime.of(8, 0));
        doctor.setShiftEnd(java.time.LocalTime.of(12, 0));

        // Appointment at 13:00 (outside shift)
        LocalDateTime dateTime = LocalDateTime.now().plusDays(2).withHour(13).withMinute(0);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Schedule.createSchedule(patient, doctor, dateTime, "Outside Shift", List.of());
        });
        Assertions.assertEquals("The appointment must be scheduled within the doctor's shift hours.", exception.getMessage());
    }

    @Test
    void shouldThrowWhenScheduleOverlapsWithin30Minutes() {
        Patient patient = new Patient();
        Doctor doctor = new Doctor();
        LocalDateTime firstTime = LocalDateTime.now().plusDays(2).withHour(10).withMinute(0);
        LocalDateTime secondTime = firstTime.plusMinutes(15); // Overlaps as it is within 30 min

        Schedule firstSchedule = Schedule.createSchedule(patient, doctor, firstTime, "First", List.of());
        List<Schedule> existingSchedules = List.of(firstSchedule);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Schedule.createSchedule(patient, doctor, secondTime, "Second", existingSchedules);
        });
        Assertions.assertEquals("There is already an appointment scheduled for this time.", exception.getMessage());
    }

    @Test
    void shouldThrowWhenCancellingWithLessThan24Hours() {
        Schedule schedule = Schedule.createSchedule(new Patient(), new Doctor(), LocalDateTime.now().plusHours(12),
                "Routine", List.of());

        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            schedule.cancelSchedule("Too late");
        });
        Assertions.assertEquals("An appointment can only be canceled with more than 24 hours in advance.", exception.getMessage());
=======
    void shouldCreateScheduleSuccessfullyWithMockedDoctorAndPatient() {
        Doctor doctor = mock(Doctor.class);
        Patient patient = mock(Patient.class);
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);

        Schedule result = Schedule.createSchedule(patient, doctor, dateTime, "Routine Checkup", List.of());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(doctor, result.getDoctor());
        Assertions.assertEquals(patient, result.getPatient());
    }

    @Test
    void shouldThrowWhenConflictDetectedWithMockedExistingSchedule() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);

        Schedule existingSchedule = mock(Schedule.class);
        when(existingSchedule.getDateTime()).thenReturn(dateTime);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                Schedule.hasConflict(List.of(existingSchedule), dateTime)
        );
>>>>>>> 5d81575e2e0a2160c5a1d2907467c55b370185a6
    }

}
