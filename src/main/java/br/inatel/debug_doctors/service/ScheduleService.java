package br.inatel.debug_doctors.service;

import br.inatel.debug_doctors.domain.doctor.Doctor;
import br.inatel.debug_doctors.domain.patient.Patient;
import br.inatel.debug_doctors.domain.schedule.Schedule;
import br.inatel.debug_doctors.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public Schedule findById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + id));
    }

    public Schedule createSchedule(Long doctorId, Long patientId, LocalDateTime dateTime, String description) {
        Doctor doctor = doctorService.findById(doctorId);
        Patient patient = patientService.findById(patientId);

        List<Schedule> doctorSchedules = getSchedulesByDoctor(doctorId);

        Schedule schedule = Schedule.createSchedule(patient, doctor, dateTime, description, doctorSchedules);
        return scheduleRepository.save(schedule);
    }

    public Schedule confirmSchedule(Long id) {
        Schedule schedule = findById(id);
        schedule.confirmSchedule();
        return scheduleRepository.save(schedule);
    }

    public Schedule cancelSchedule(Long id, String reason) {
        Schedule schedule = findById(id);
        schedule.cancelSchedule(reason);
        return scheduleRepository.save(schedule);
    }


    private List<Schedule> getSchedulesByDoctor(Long doctorId) {
        // No futuro, isso pode ser melhorado para uma Query direto no Repository (ex: findByDoctorId)
        return scheduleRepository.findAll().stream()
                .filter(s -> s.getDoctor().getId().equals(doctorId))
                .toList();
    }
}