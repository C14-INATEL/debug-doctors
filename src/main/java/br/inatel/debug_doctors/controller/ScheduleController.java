package br.inatel.debug_doctors.controller;

import br.inatel.debug_doctors.domain.schedule.Schedule;
import br.inatel.debug_doctors.dto.CancelRequestDTO;
import br.inatel.debug_doctors.dto.ScheduleRequestDTO;
import br.inatel.debug_doctors.dto.ScheduleResponseDTO;
import br.inatel.debug_doctors.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDTO>> getAllSchedules() {
        List<ScheduleResponseDTO> schedules = scheduleService.findAll().stream()
                .map(ScheduleResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(schedules);
    }

    @PostMapping
    public ResponseEntity<?> createSchedule(@RequestBody ScheduleRequestDTO request) {
        try {
            Schedule schedule = scheduleService.createSchedule(
                    request.getDoctorId(),
                    request.getPatientId(),
                    request.getDateTime(),
                    request.getDescription()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(ScheduleResponseDTO.fromEntity(schedule));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getScheduleById(@PathVariable Long id) {
        try {
            Schedule schedule = scheduleService.findById(id);
            return ResponseEntity.ok(ScheduleResponseDTO.fromEntity(schedule));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelSchedule(@PathVariable Long id, @RequestBody CancelRequestDTO cancelRequest) {
        try {
            String reason = cancelRequest != null ? cancelRequest.getReason() : "Não informado";
            Schedule schedule = scheduleService.cancelSchedule(id, reason);
            return ResponseEntity.ok(ScheduleResponseDTO.fromEntity(schedule));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<?> confirmSchedule(@PathVariable Long id) {
        try {
            Schedule schedule = scheduleService.confirmSchedule(id);
            return ResponseEntity.ok(ScheduleResponseDTO.fromEntity(schedule));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
