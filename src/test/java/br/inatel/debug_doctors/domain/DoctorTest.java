package br.inatel.debug_doctors.domain;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import br.inatel.debug_doctors.domain.doctor.Doctor;



public class DoctorTest {

    private static class DoctorMock extends Doctor {
        private final String crm;
        private final LocalTime shiftStart;
        private final LocalTime shiftEnd;

        DoctorMock(String crm, LocalTime shiftStart, LocalTime shiftEnd) {
            this.crm = crm;
            this.shiftStart = shiftStart;
            this.shiftEnd = shiftEnd;
        }

        @Override
        public String getCrm() {
            return crm;
        }

        @Override
        public LocalTime getShiftStart() {
            return shiftStart;
        }

        @Override
        public LocalTime getShiftEnd() {
            return shiftEnd;
        }
    }

    private static class DoctorBusinessValidator {
        void validateCrm(Doctor doctor) {
            String crm = doctor.getCrm();
            if (crm == null || crm.isBlank()) {
                throw new IllegalArgumentException("CRM must be informed");
            }
        }

        void validateShift(Doctor doctor) {
            LocalTime shiftStart = doctor.getShiftStart();
            LocalTime shiftEnd = doctor.getShiftEnd();

            if (shiftStart == null || shiftEnd == null) {
                throw new IllegalArgumentException("Shift start/end must be informed");
            }
            if (!shiftStart.isBefore(shiftEnd)) {
                throw new IllegalArgumentException("Shift start must be before shift end");
            }
        }
    }

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
    void shouldValidateDoctorWithValidCrm() {
        Doctor mockedDoctor = new DoctorMock("12345-MG", null, null);

        DoctorBusinessValidator validator = new DoctorBusinessValidator();

        validator.validateCrm(mockedDoctor);
    }

    @Test
    void shouldThrowWhenDoctorHasInvalidShift() {
        Doctor mockedDoctor = new DoctorMock(null, LocalTime.of(18, 0), LocalTime.of(8, 0));

        DoctorBusinessValidator validator = new DoctorBusinessValidator();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validateShift(mockedDoctor)
        );

        assertEquals("Shift start must be before shift end", exception.getMessage());
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
    void shouldUpdateDoctorSpecialtyAndCrm() {

        Doctor doctor = new Doctor();
        doctor.setSpecialty("Clínico Geral");
        doctor.setCrm("12345-MG");


        doctor.setSpecialty("Cardiologista");
        doctor.setCrm("98765-MG");


        assertAll("Verify if doctor attributes were updated correctly",
                () -> assertEquals("Cardiologista", doctor.getSpecialty(), "The specialty should be updated"),
                () -> assertEquals("98765-MG", doctor.getCrm(), "The CRM should be updated")
        );
    }
}
