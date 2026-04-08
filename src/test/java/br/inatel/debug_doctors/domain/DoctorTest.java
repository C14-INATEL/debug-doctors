package br.inatel.debug_doctors.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    void shouldCreateDoctorWithInvalidName() {
        String invalidName = "";
        String validspecialty = "Urologista";
        String validcrm = "validated";

        Doctor doctor = new Doctor();
        doctor.setName(invalidName);
        doctor.setSpecialty(validspecialty);
        doctor.setCrm(validcrm);

        assertAll("Verify if doctor attributes were set correctly",
                () -> assertNotNull(doctor, "Patient should not be null"),
                () -> assertEquals(invalidName, doctor.getName(), "Name should match the assigned value"),
                () -> assertEquals(validspecialty, doctor.getSpecialty(), "specialty should match the assigned value"),
                () -> assertEquals(validcrm, doctor.getCrm(), "Crm should match the assigned value"));
    }

    @Test
    void shouldCreateDoctorWithShiftTimes() {
        String validName = "Dimitri";
        String validspecialty = "Cardiologista";
        String validcrm = "validated";
        LocalTime shiftStart = LocalTime.of(8, 0);
        LocalTime shiftEnd = LocalTime.of(18, 0);

        Doctor doctor = new Doctor();
        doctor.setName(validName);
        doctor.setSpecialty(validspecialty);
        doctor.setCrm(validcrm);
        doctor.setShiftStart(shiftStart);
        doctor.setShiftEnd(shiftEnd);

        assertAll("Verify if doctor attributes were set correctly",
                () -> assertNotNull(doctor, "Patient should not be null"),
                () -> assertEquals(shiftStart, doctor.getShiftStart(), "shiftStart should match the assigned value"),
                () -> assertEquals(shiftEnd, doctor.getShiftEnd(), "shiftEnd should match the assigned value"));
    }

    @Test
    void shouldCreateDoctorWithInvalidShiftTimes() {
        String validName = "Dimitri";
        String validspecialty = "Cardiologista";
        String validcrm = "validated";
        LocalTime shiftStart = LocalTime.of(18, 0);
        LocalTime shiftEnd = LocalTime.of(8, 0);

        Doctor doctor = new Doctor();
        doctor.setName(validName);
        doctor.setSpecialty(validspecialty);
        doctor.setCrm(validcrm);
        doctor.setShiftStart(shiftEnd);
        doctor.setShiftEnd(shiftStart);

        assertAll("Verify if doctor attributes were set correctly",
                () -> assertNotNull(doctor, "Patient should not be null"),
                () -> assertEquals(shiftEnd, doctor.getShiftStart(), "shiftStart should match the assigned value"),
                () -> assertEquals(shiftStart, doctor.getShiftEnd(), "shiftEnd should match the assigned value"));
    }

    @Test
    void shouldCreateDoctorWithInvalidCrm() {
        String validName = "Dimitri";
        String validspecialty = "Cardiologista";
        String invalidCrm = "";

        Doctor doctor = new Doctor();
        doctor.setName(validName);
        doctor.setSpecialty(validspecialty);
        doctor.setCrm(invalidCrm);

        assertAll("Verify if doctor attributes were set correctly",
                () -> assertNotNull(doctor, "Patient should not be null"),
                () -> assertEquals(invalidCrm, doctor.getCrm(), "Crm should match the assigned value"));
    }

    @Test
    void shouldCreateDoctorWithAllArgsConstructor() {
        String validName = "Dimitri";
        String validspecialty = "Cardiologista";
        String validcrm = "validated";
        LocalTime shiftStart = LocalTime.of(8, 0);
        LocalTime shiftEnd = LocalTime.of(18, 0);

        Doctor doctor = new Doctor(null, validName, validspecialty, validcrm, shiftStart, shiftEnd);

        assertAll("Verify if doctor attributes were set correctly",
                () -> assertNotNull(doctor, "Patient should not be null"),
                () -> assertEquals(validName, doctor.getName(), "Name should match the assigned value"),
                () -> assertEquals(validspecialty, doctor.getSpecialty(), "specialty should match the assigned value"),
                () -> assertEquals(validcrm, doctor.getCrm(), "Crm should match the assigned value"),
                () -> assertEquals(shiftStart, doctor.getShiftStart(), "shiftStart should match the assigned value"),
                () -> assertEquals(shiftEnd, doctor.getShiftEnd(), "shiftEnd should match the assigned value"));
    }

    @Test
    void shouldHaveNullShiftTimesByDefault() {
        Doctor doctor = new Doctor();

        assertAll("Shift times should be null by default",
                () -> assertNull(doctor.getShiftStart(), "shiftStart should be null by default"),
                () -> assertNull(doctor.getShiftEnd(), "shiftEnd should be null by default")
        );
    }

    @Test
    void shouldReturnCorrectIdAfterSetting() {

        Doctor doctor = new Doctor();
        Long simulatedDatabaseId = 105L;


        doctor.setId(simulatedDatabaseId);


        assertEquals(simulatedDatabaseId, doctor.getId(),
                "The returned ID should be equal to the defined ID.");
    }

    @Test
    void shouldDetectInvalidShiftWhenEndIsBeforeStart() {
        Doctor doctor = new Doctor();
        LocalTime shiftStart = LocalTime.of(18, 0);
        LocalTime shiftEnd = LocalTime.of(8, 0);

        doctor.setShiftStart(shiftStart);
        doctor.setShiftEnd(shiftEnd);

        assertTrue(
                doctor.getShiftEnd().isBefore(doctor.getShiftStart()),
                "shiftEnd should be before shiftStart, indicating an invalid shift");
    }
}
