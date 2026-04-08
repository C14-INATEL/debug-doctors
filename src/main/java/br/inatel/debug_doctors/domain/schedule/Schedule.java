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

    public static Schedule createSchedule(Patient patient, Doctor doctor, LocalDateTime dateTime, String description,
            List<Schedule> existingSchedules) {
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("The appointment date cannot be in the past.");
        }

        boolean hasConflict = existingSchedules.stream()
                .anyMatch(s -> s.getDateTime().equals(dateTime));

        if (hasConflict) {
            throw new IllegalArgumentException("There is already an appointment scheduled for this time.");
        }
        Schedule schedule = new Schedule();
        schedule.setPatient(patient);
        schedule.setDoctor(doctor);
        schedule.setDateTime(dateTime);
        schedule.setDescription(description);
        schedule.setConfirmed(false);
        return schedule;
    }

    public void confirmSchedule() {
        this.confirmed = true;
    }
}
