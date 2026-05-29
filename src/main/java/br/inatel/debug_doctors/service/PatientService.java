package br.inatel.debug_doctors.service;

import br.inatel.debug_doctors.domain.patient.Patient;
import br.inatel.debug_doctors.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + id));
    }

    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient update(Long id, Patient patientDetails) {
        Patient patient = findById(id);
        patient.setName(patientDetails.getName());
        patient.setCpf(patientDetails.getCpf());
        patient.setEmail(patientDetails.getEmail());
        return patientRepository.save(patient);
    }

    public void delete(Long id) {
        Patient patient = findById(id);
        patientRepository.delete(patient);
    }
}
