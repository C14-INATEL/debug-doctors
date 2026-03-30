package br.inatel.debug_doctors.model;

import br.inatel.debug_doctors.domain.doctor.Doctor;
import br.inatel.debug_doctors.domain.patient.Patient;
import br.inatel.debug_doctors.domain.schedule.Schedule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.time.LocalDateTime;

class ScheduleTest {

    @Test
    void testNewSchedule() {
        Patient patient = new Patient();
        Doctor doctor = new Doctor();
        LocalDateTime dateTime = LocalDateTime.now();
        String description = "Routine Checkup";

        Schedule schedule = Schedule.create(patient, doctor, dateTime, description);

        Assertions.assertNotNull(schedule);
        Assertions.assertEquals(patient, schedule.getPatient());
        Assertions.assertEquals(doctor, schedule.getDoctor());
        Assertions.assertEquals(dateTime, schedule.getDateTime());
        Assertions.assertEquals(description, schedule.getDescription());
        Assertions.assertFalse(schedule.isConfirmed());
    }

    @Test
    void confirmSchedule() {

        Schedule schedule = Schedule.create(new Patient(), new Doctor(), LocalDateTime.now(), "Routine Checkup");

        schedule.confirmSchedule();

        Assertions.assertTrue(schedule.isConfirmed());
    }

}
