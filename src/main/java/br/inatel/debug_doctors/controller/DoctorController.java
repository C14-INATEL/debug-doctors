package br.inatel.debug_doctors.controller;

import br.inatel.debug_doctors.domain.doctor.Doctor;
import br.inatel.debug_doctors.dto.DoctorDTO;
import br.inatel.debug_doctors.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        List<DoctorDTO> doctors = doctorService.findAll().stream()
                .map(DoctorDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(doctors);
    }

    @PostMapping
    public ResponseEntity<?> createDoctor(@RequestBody DoctorDTO doctorDTO) {
        try {
            Doctor doctor = doctorDTO.toEntity();
            Doctor savedDoctor = doctorService.save(doctor);
            return ResponseEntity.status(HttpStatus.CREATED).body(DoctorDTO.fromEntity(savedDoctor));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Long id) {
        try {
            Doctor doctor = doctorService.findById(id);
            return ResponseEntity.ok(DoctorDTO.fromEntity(doctor));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO) {
        try {
            Doctor doctorDetails = doctorDTO.toEntity();
            Doctor updatedDoctor = doctorService.update(id, doctorDetails);
            return ResponseEntity.ok(DoctorDTO.fromEntity(updatedDoctor));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        try {
            doctorService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
