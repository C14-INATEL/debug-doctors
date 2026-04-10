package br.inatel.debug_doctors.domain.schedule;

import br.inatel.debug_doctors.domain.doctor.Doctor;
import br.inatel.debug_doctors.domain.patient.Patient;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private LocalDateTime dateTime;
    private String description;
    private boolean confirmed;

    // --- Novos atributos para o Cancelamento ---
    private boolean canceled;
    private String cancellationReason;

    private static void validateDateNotInPast(LocalDateTime dateTime) {
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("The appointment date cannot be in the past.");
        }
    }

    public static void hasConflict(List<Schedule> existingSchedules, LocalDateTime dateTime) {
        boolean hasConflict = existingSchedules.stream().anyMatch(s -> s.getDateTime().equals(dateTime));

        if (hasConflict) {
            throw new IllegalArgumentException("There is already an appointment scheduled for this time.");
        }
    }

    public static Schedule createSchedule(Patient patient, Doctor doctor, LocalDateTime dateTime, String description,
                                          List<Schedule> existingSchedules) {

        // --- Novas regras: Não aceitar nulos ---
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null.");
        }
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null.");
        }

        validateDateNotInPast(dateTime);
        hasConflict(existingSchedules, dateTime);

        Schedule schedule = new Schedule();
        schedule.setPatient(patient);
        schedule.setDoctor(doctor);
        schedule.setDateTime(dateTime);
        schedule.setDescription(description);
        schedule.setConfirmed(false);
        schedule.setCanceled(false);
        return schedule;
    }

    public void confirmSchedule() {
        this.confirmed = true;
    }

    // --- Novo Método: Cancelar Consulta ---
    public void cancelSchedule(String reason) {
        if (this.canceled) {
            throw new IllegalStateException("Schedule is already canceled.");
        }
        this.canceled = true;
        this.cancellationReason = reason;
        this.confirmed = false;
    }
}