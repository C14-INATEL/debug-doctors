package br.inatel.debug_doctors.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDTO {
    private Long doctorId;
    private Long patientId;
    private LocalDateTime dateTime;
    private String description;
}
