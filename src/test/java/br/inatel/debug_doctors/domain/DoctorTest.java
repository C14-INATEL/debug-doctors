package br.inatel.debug_doctors.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;


import br.inatel.debug_doctors.domain.doctor.Doctor;

public class DoctorTest {
    @Test
    void shouldCreateDoctorSuccessfully() {
        String validName = "Wagner Dourado";
        String validspecialty = "Urologista";
        String validcrm = "validated";

        Doctor doctor = new Doctor();
        doctor.setName(validName);
        doctor.setSpecialty(validspecialty);
        doctor.setCrm(validcrm);

        assertAll("Verify if doctor attributes were set correctly",
                () -> assertNotNull(doctor, "Patient should not be null"),
                () -> assertEquals(validName, doctor.getName(), "Name should match the assigned value"),
                () -> assertEquals(validspecialty, doctor.getSpecialty(), "specialty should match the assigned value"),
                () -> assertEquals(validcrm, doctor.getCrm(), "Crm should match the assigned value"));
    }

    @Test
    void shouldSetShiftHoursCorrectly(){
        Doctor doctor = new Doctor();
        LocalTime start = LocalTime.of(8,0); // 8:00
        LocalTime end =  LocalTime.of(18,0);// 18:00

        doctor.setShiftStart(start);
        doctor.setShiftEnd(end);

        assertEquals(start, doctor.getShiftStart());
        assertEquals(end, doctor.getShiftEnd());
    }

    @Test
    void shouldVerifyIfShiftIsRegistered() {

        Doctor doctor = new Doctor();

        // recording the times
        doctor.setShiftStart(LocalTime.of(10, 0)); // 10:00
        doctor.setShiftEnd(LocalTime.of(16, 0));   // 16:00

        // Check if the system recognizes that you are now registered.
        assertNotNull(doctor.getShiftStart(), "The start time must be recorded.");
        assertNotNull(doctor.getShiftEnd(), "The end time should be recorded.");
    }

    @Test
    void shouldReturnCorrectIdAfterSetting() {
        // Arrange
        Doctor doctor = new Doctor();
        Long simulatedDatabaseId = 105L;

        // Act
        doctor.setId(simulatedDatabaseId);

        // Assert
        assertEquals(simulatedDatabaseId, doctor.getId(),
                "The returned ID should be equal to the defined ID.");
    }

}
