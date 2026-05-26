package br.inatel.debug_doctors.dto;

import br.inatel.debug_doctors.domain.patient.Patient;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private Long id;
    private String name;
    private String cpf;
    private String email;

    public static PatientDTO fromEntity(Patient patient) {
        if (patient == null) return null;
        return new PatientDTO(
                patient.getId(),
                patient.getName(),
                patient.getCpf(),
                patient.getEmail()
        );
    }

    public Patient toEntity() {
        Patient patient = new Patient();
        patient.setId(this.id);
        patient.setName(this.name);
        patient.setCpf(this.cpf);
        patient.setEmail(this.email);
        return patient;
    }
}
