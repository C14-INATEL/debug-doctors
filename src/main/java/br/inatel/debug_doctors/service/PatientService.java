package br.inatel.debug_doctors.service;

import br.inatel.debug_doctors.domain.patient.Patient;
import br.inatel.debug_doctors.repository.PatientRepository;

public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public void save(Patient patient) {
        if (patient.getEmail() == null || !patient.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }

        if (patient.getCpf() == null || patient.getCpf().length() != 14) {
            throw new IllegalArgumentException("CPF must be 14 characters long including punctuation");
        }

        repository.save(patient);
    }
}