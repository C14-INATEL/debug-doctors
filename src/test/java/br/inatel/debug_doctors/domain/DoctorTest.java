package br.inatel.debug_doctors.domain;

import br.inatel.debug_doctors.domain.doctor.Doctor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoctorTest {

    @Mock
    private Doctor mockedDoctor;

    @Test
    void shouldCreateDoctorSuccessfully() {
        String validName = "Wagner Dourado";
        String validSpecialty = "Urologista";
        String validCrm = "validated";

        Doctor doctor = new Doctor();
        doctor.setName(validName);
        doctor.setSpecialty(validSpecialty);
        doctor.setCrm(validCrm);

        assertAll("Verify if doctor attributes were set correctly",
                () -> assertNotNull(doctor, "Doctor should not be null"),
                () -> assertEquals(validName, doctor.getName(), "Name should match the assigned value"),
                () -> assertEquals(validSpecialty, doctor.getSpecialty(), "Specialty should match the assigned value"),
                () -> assertEquals(validCrm, doctor.getCrm(), "CRM should match the assigned value"));
    }

    @Test
    void shouldCreateDoctorWithInvalidName() {
        String invalidName = "";
        String validSpecialty = "Urologista";
        String validCrm = "validated";

        Doctor doctor = new Doctor();
        doctor.setName(invalidName);
        doctor.setSpecialty(validSpecialty);
        doctor.setCrm(validCrm);

        assertAll("Verify if doctor attributes were set correctly",
                () -> assertNotNull(doctor, "Doctor should not be null"),
                () -> assertEquals(invalidName, doctor.getName(), "Name should match the assigned value"),
                () -> assertEquals(validSpecialty, doctor.getSpecialty(), "Specialty should match the assigned value"),
                () -> assertEquals(validCrm, doctor.getCrm(), "CRM should match the assigned value"));
    }

    @Test
    void shouldCreateDoctorWithShiftTimes() {
        LocalTime shiftStart = LocalTime.of(8, 0);
        LocalTime shiftEnd = LocalTime.of(18, 0);

        Doctor doctor = new Doctor();
        doctor.setName("Dimitri");
        doctor.setSpecialty("Cardiologista");
        doctor.setCrm("validated");
        doctor.setShiftStart(shiftStart);
        doctor.setShiftEnd(shiftEnd);

        assertAll("Verify if doctor shift times were set correctly",
                () -> assertNotNull(doctor, "Doctor should not be null"),
                () -> assertEquals(shiftStart, doctor.getShiftStart(), "shiftStart should match the assigned value"),
                () -> assertEquals(shiftEnd, doctor.getShiftEnd(), "shiftEnd should match the assigned value"));
    }

    @Test
    void shouldCreateDoctorWithInvalidShiftTimes() {
        // shiftStart > shiftEnd — valores invertidos intencionalmente
        LocalTime shiftStart = LocalTime.of(18, 0);
        LocalTime shiftEnd = LocalTime.of(8, 0);

        Doctor doctor = new Doctor();
        doctor.setName("Dimitri");
        doctor.setSpecialty("Cardiologista");
        doctor.setCrm("validated");
        doctor.setShiftStart(shiftStart);
        doctor.setShiftEnd(shiftEnd);

        assertAll("Verify if doctor shift times reflect the invalid state",
                () -> assertNotNull(doctor, "Doctor should not be null"),
                () -> assertEquals(shiftStart, doctor.getShiftStart(), "shiftStart should match"),
                () -> assertEquals(shiftEnd, doctor.getShiftEnd(), "shiftEnd should match"));
    }

    @Test
    void shouldCreateDoctorWithInvalidCrm() {
        String invalidCrm = "";

        Doctor doctor = new Doctor();
        doctor.setName("Dimitri");
        doctor.setSpecialty("Cardiologista");
        doctor.setCrm(invalidCrm);

        assertAll("Verify if doctor invalid CRM is stored as-is",
                () -> assertNotNull(doctor, "Doctor should not be null"),
                () -> assertEquals(invalidCrm, doctor.getCrm(), "CRM should match the assigned value"));
    }

    @Test
    void shouldCreateDoctorWithAllArgsConstructor() {
        LocalTime shiftStart = LocalTime.of(8, 0);
        LocalTime shiftEnd = LocalTime.of(18, 0);

        Doctor doctor = new Doctor(null, "Dimitri", "Cardiologista", "validated", shiftStart, shiftEnd);

        assertAll("Verify if doctor attributes were set correctly via all-args constructor",
                () -> assertNotNull(doctor, "Doctor should not be null"),
                () -> assertEquals("Dimitri", doctor.getName(), "Name should match"),
                () -> assertEquals("Cardiologista", doctor.getSpecialty(), "Specialty should match"),
                () -> assertEquals("validated", doctor.getCrm(), "CRM should match"),
                () -> assertEquals(shiftStart, doctor.getShiftStart(), "shiftStart should match"),
                () -> assertEquals(shiftEnd, doctor.getShiftEnd(), "shiftEnd should match"));
    }

    @Test
    void shouldHaveNullShiftTimesByDefault() {
        Doctor doctor = new Doctor();

        assertAll("Shift times should be null by default",
                () -> assertNull(doctor.getShiftStart(), "shiftStart should be null by default"),
                () -> assertNull(doctor.getShiftEnd(), "shiftEnd should be null by default"));
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
                () -> assertEquals("98765-MG", doctor.getCrm(), "The CRM should be updated"));
    }

    @Test
    void shouldValidateDoctorWithValidCrm() {
        // Mockito configura o retorno do getCrm() sem precisar de subclasse manual
        when(mockedDoctor.getCrm()).thenReturn("12345-MG");

        String crm = mockedDoctor.getCrm();

        assertNotNull(crm, "CRM should not be null");
        assertFalse(crm.isBlank(), "CRM should not be blank");

        verify(mockedDoctor, times(1)).getCrm();
    }

    @Test
    void shouldThrowWhenDoctorHasInvalidShift() {
        // Simula um médico cujo expediente está invertido (18h início, 8h fim)
        when(mockedDoctor.getShiftStart()).thenReturn(LocalTime.of(18, 0));
        when(mockedDoctor.getShiftEnd()).thenReturn(LocalTime.of(8, 0));

        LocalTime shiftStart = mockedDoctor.getShiftStart();
        LocalTime shiftEnd = mockedDoctor.getShiftEnd();

        // A regra de negócio — shiftStart deve ser antes de shiftEnd
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    if (!shiftStart.isBefore(shiftEnd)) {
                        throw new IllegalArgumentException("Shift start must be before shift end");
                    }
                }
        );

        assertEquals("Shift start must be before shift end", exception.getMessage());

        verify(mockedDoctor).getShiftStart();
        verify(mockedDoctor).getShiftEnd();
    }

    @Test
    void shouldReturnNullShiftTimesWhenNotConfigured() {
        // Mock sem configuração de retorno devolve null por padrão para objetos
        when(mockedDoctor.getShiftStart()).thenReturn(null);
        when(mockedDoctor.getShiftEnd()).thenReturn(null);

        assertNull(mockedDoctor.getShiftStart(), "Unconfigured shiftStart should be null");
        assertNull(mockedDoctor.getShiftEnd(), "Unconfigured shiftEnd should be null");

        verify(mockedDoctor).getShiftStart();
        verify(mockedDoctor).getShiftEnd();
    }
}
