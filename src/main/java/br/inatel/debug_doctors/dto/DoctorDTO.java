package br.inatel.debug_doctors.dto;

import br.inatel.debug_doctors.domain.doctor.Doctor;
import lombok.*;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    private Long id;
    private String name;
    private String specialty;
    private String crm;
    private LocalTime shiftStart;
    private LocalTime shiftEnd;

    public static DoctorDTO fromEntity(Doctor doctor) {
        if (doctor == null) return null;
        return new DoctorDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getSpecialty(),
                doctor.getCrm(),
                doctor.getShiftStart(),
                doctor.getShiftEnd()
        );
    }

    public Doctor toEntity() {
        Doctor doctor = new Doctor();
        doctor.setId(this.id);
        doctor.setName(this.name);
        doctor.setSpecialty(this.specialty);
        doctor.setCrm(this.crm);
        doctor.setShiftStart(this.shiftStart);
        doctor.setShiftEnd(this.shiftEnd);
        return doctor;
    }
}
