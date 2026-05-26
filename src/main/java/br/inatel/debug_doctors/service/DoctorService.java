package br.inatel.debug_doctors.service;

import br.inatel.debug_doctors.domain.doctor.Doctor;
import br.inatel.debug_doctors.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public Doctor findById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + id));
    }

    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor update(Long id, Doctor doctorDetails) {
        Doctor doctor = findById(id);
        doctor.setName(doctorDetails.getName());
        doctor.setSpecialty(doctorDetails.getSpecialty());
        doctor.setCrm(doctorDetails.getCrm());
        doctor.setShiftStart(doctorDetails.getShiftStart());
        doctor.setShiftEnd(doctorDetails.getShiftEnd());
        return doctorRepository.save(doctor);
    }

    public void delete(Long id) {
        Doctor doctor = findById(id);
        doctorRepository.delete(doctor);
    }
}
