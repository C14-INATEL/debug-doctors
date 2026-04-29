package br.inatel.debug_doctors.repository;

import br.inatel.debug_doctors.domain.patient.Patient;

public interface PatientRepository {
    void save(Patient patient);
}