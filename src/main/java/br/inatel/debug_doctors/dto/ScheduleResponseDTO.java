package br.inatel.debug_doctors.dto;

import br.inatel.debug_doctors.domain.schedule.Schedule;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDTO {
    private Long id;
    private DoctorDTO doctor;
    private PatientDTO patient;
    private LocalDateTime dateTime;
    private String description;
    private boolean confirmed;
    private boolean canceled;
    private String cancellationReason;

    public static ScheduleResponseDTO fromEntity(Schedule schedule) {
        if (schedule == null) return null;
        return new ScheduleResponseDTO(
                schedule.getId(),
                DoctorDTO.fromEntity(schedule.getDoctor()),
                PatientDTO.fromEntity(schedule.getPatient()),
                schedule.getDateTime(),
                schedule.getDescription(),
                schedule.isConfirmed(),
                schedule.isCanceled(),
                schedule.getCancellationReason()
        );
    }
}
