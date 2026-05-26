package br.inatel.debug_doctors.domain.schedule;

import br.inatel.debug_doctors.domain.doctor.Doctor;
import br.inatel.debug_doctors.domain.patient.Patient;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    private boolean canceled;
    private String cancellationReason;

    private static void validateDateNotInPast(LocalDateTime dateTime) {
        if (dateTime.isBefore(LocalDateTime.now().plusMinutes(30))) {
            throw new IllegalArgumentException("The appointment must be scheduled with at least 30 minutes in advance.");
        }
    }

    private static void validateDoctorShift(Doctor doctor, LocalDateTime dateTime) {
        LocalTime shiftStart = doctor.getShiftStart();
        LocalTime shiftEnd = doctor.getShiftEnd();
        if (shiftStart != null && shiftEnd != null) {
            LocalTime appointmentStart = dateTime.toLocalTime();
            LocalTime appointmentEnd = appointmentStart.plusMinutes(30);
            if (appointmentStart.isBefore(shiftStart) || appointmentEnd.isAfter(shiftEnd)) {
                throw new IllegalArgumentException("The appointment must be scheduled within the doctor's shift hours.");
            }
        }
    }

    public static void hasConflict(List<Schedule> existingSchedules, LocalDateTime dateTime) {
        boolean hasConflict = existingSchedules.stream()
                .filter(s -> !s.isCanceled())
                .anyMatch(s -> {
                    long minutesDiff = java.time.Duration.between(s.getDateTime(), dateTime).abs().toMinutes();
                    return minutesDiff < 30;
                });

        if (hasConflict) {
            throw new IllegalArgumentException("There is already an appointment scheduled for this time.");
        }
    }

    public static Schedule createSchedule(Patient patient, Doctor doctor, LocalDateTime dateTime, String description,
                                          List<Schedule> existingSchedules) {


        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null.");
        }
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null.");
        }

        validateDateNotInPast(dateTime);
        validateDoctorShift(doctor, dateTime);
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

    public void cancelSchedule(String reason) {
        if (this.canceled) {
            throw new IllegalStateException("Schedule is already canceled.");
        }
        if (LocalDateTime.now().plusHours(24).isAfter(this.dateTime)) {
            throw new IllegalStateException("An appointment can only be canceled with more than 24 hours in advance.");
        }
        this.canceled = true;
        this.cancellationReason = reason;
        this.confirmed = false;
    }
}