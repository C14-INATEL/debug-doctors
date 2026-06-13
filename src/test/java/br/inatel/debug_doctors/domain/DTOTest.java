package br.inatel.debug_doctors.domain;

import br.inatel.debug_doctors.domain.doctor.Doctor;
import br.inatel.debug_doctors.dto.DoctorDTO;
import org.junit.jupiter.api.Test;
import br.inatel.debug_doctors.domain.patient.Patient;
import br.inatel.debug_doctors.dto.PatientDTO;
import br.inatel.debug_doctors.dto.ScheduleResponseDTO;
import br.inatel.debug_doctors.dto.ScheduleRequestDTO;
import br.inatel.debug_doctors.domain.schedule.Schedule;
import br.inatel.debug_doctors.dto.CancelRequestDTO;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

class DTOTest {

    @Test
    void shouldConvertPatientToDTO() {
        Patient patient = new Patient(1L, "Carlos Silva", "111.222.333-44", "carlos@email.com");

        PatientDTO dto = PatientDTO.fromEntity(patient);

        assertEquals(1L, dto.getId());
        assertEquals("Carlos Silva", dto.getName());
        assertEquals("111.222.333-44", dto.getCpf());
        assertEquals("carlos@email.com", dto.getEmail());
    }

    @Test
    void shouldConvertPatientDTOToEntity() {
        PatientDTO dto = new PatientDTO(1L, "Carlos Silva", "111.222.333-44", "carlos@email.com");

        Patient patient = dto.toEntity();

        assertEquals("Carlos Silva", patient.getName());
        assertEquals("111.222.333-44", patient.getCpf());
        assertEquals("carlos@email.com", patient.getEmail());
    }

    @Test
    void shouldReturnNullWhenPatientIsNull() {
        PatientDTO dto = PatientDTO.fromEntity(null);
        assertNull(dto);
    }

    @Test
    void shouldConvertDoctorToDTO() {
        Doctor doctor = new Doctor(1L, "Dr. House", "Cardiologia", "12345-MG",
                LocalTime.of(8, 0), LocalTime.of(18, 0));

        DoctorDTO dto = DoctorDTO.fromEntity(doctor);

        assertEquals(1L, dto.getId());
        assertEquals("Dr. House", dto.getName());
        assertEquals("Cardiologia", dto.getSpecialty());
        assertEquals("12345-MG", dto.getCrm());
        assertEquals(LocalTime.of(8, 0), dto.getShiftStart());
        assertEquals(LocalTime.of(18, 0), dto.getShiftEnd());
    }

    @Test
    void shouldConvertDoctorDTOToEntity() {
        DoctorDTO dto = new DoctorDTO(1L, "Dr. House", "Cardiologia", "12345-MG",
                LocalTime.of(8, 0), LocalTime.of(18, 0));

        Doctor doctor = dto.toEntity();

        assertEquals("Dr. House", doctor.getName());
        assertEquals("Cardiologia", doctor.getSpecialty());
        assertEquals("12345-MG", doctor.getCrm());
    }

    @Test
    void shouldReturnNullWhenDoctorIsNull() {
        DoctorDTO dto = DoctorDTO.fromEntity(null);
        assertNull(dto);
    }

    @Test
    void shouldCreateCancelRequestDTOWithReason() {
        CancelRequestDTO dto = new CancelRequestDTO("Paciente adoeceu");

        assertEquals("Paciente adoeceu", dto.getReason());
    }

    @Test
    void shouldCreateEmptyCancelRequestDTO() {
        CancelRequestDTO dto = new CancelRequestDTO();

        assertNull(dto.getReason());
    }

    @Test
    void shouldCreateScheduleRequestDTOWithCorrectData() {
        LocalDateTime dateTime = LocalDateTime.of(2026, 6, 10, 9, 0);
        ScheduleRequestDTO dto = new ScheduleRequestDTO(1L, 2L, dateTime, "Routine checkup");

        assertEquals(1L, dto.getDoctorId());
        assertEquals(2L, dto.getPatientId());
        assertEquals(dateTime, dto.getDateTime());
        assertEquals("Routine checkup", dto.getDescription());
    }

    @Test
    void shouldConvertScheduleToResponseDTO() {
        Patient patient = new Patient(1L, "Carlos Silva", "111.222.333-44", "carlos@email.com");
        Doctor doctor = new Doctor(1L, "Dr. House", "Cardiologia", "12345-MG",
                LocalTime.of(8, 0), LocalTime.of(18, 0));
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1).with(LocalTime.of(10, 0));

        Schedule schedule = Schedule.createSchedule(patient, doctor, dateTime, "Checkup", List.of());

        ScheduleResponseDTO dto = ScheduleResponseDTO.fromEntity(schedule);

        assertNotNull(dto);
        assertEquals("Carlos Silva", dto.getPatient().getName());
        assertEquals("Dr. House", dto.getDoctor().getName());
        assertFalse(dto.isConfirmed());
        assertFalse(dto.isCanceled());
    }

    @Test
    void shouldReturnNullWhenScheduleIsNull() {
        ScheduleResponseDTO dto = ScheduleResponseDTO.fromEntity(null);
        assertNull(dto);
    }
    @Test
    void shouldCreateEmptyPatientDTO() {

        PatientDTO dto = new PatientDTO();

        assertNull(dto.getName());
        assertNull(dto.getCpf());
        assertNull(dto.getEmail());
        assertNull(dto.getId());
    }

    @Test
    void shouldCreateEmptyDoctorDTO() {
        DoctorDTO dto = new DoctorDTO();

        assertNull(dto.getName());
        assertNull(dto.getSpecialty());
        assertNull(dto.getCrm());
        assertNull(dto.getShiftStart());
        assertNull(dto.getShiftEnd());
    }

    @Test
    void shouldCreateScheduleRequestDTOEmpty() {

        ScheduleRequestDTO dto = new ScheduleRequestDTO();

        assertNull(dto.getDoctorId());
        assertNull(dto.getPatientId());
        assertNull(dto.getDateTime());
        assertNull(dto.getDescription());
    }

    @Test
    void shouldCreateScheduleResponseDTOWithAllFields() {
        PatientDTO patientDTO = new PatientDTO(1L, "Carlos Silva", "111.222.333-44", "carlos@email.com");
        DoctorDTO doctorDTO = new DoctorDTO(1L, "Dr. House", "Cardiologia", "12345-MG",
                LocalTime.of(8, 0), LocalTime.of(18, 0));
        LocalDateTime dateTime = LocalDateTime.of(2026, 6, 10, 9, 0);

        ScheduleResponseDTO dto = new ScheduleResponseDTO(
                1L, doctorDTO, patientDTO, dateTime,
                "Routine checkup", false, false, null
        );

        assertEquals(1L, dto.getId());
        assertEquals("Carlos Silva", dto.getPatient().getName());
        assertEquals("Dr. House", dto.getDoctor().getName());
        assertEquals(dateTime, dto.getDateTime());
        assertEquals("Routine checkup", dto.getDescription());
        assertFalse(dto.isConfirmed());
        assertFalse(dto.isCanceled());
        assertNull(dto.getCancellationReason());
    }

}