package br.inatel.debug_doctors.domain.doctor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O banco vai auto-incrementar
    private Long id;

    private String name;
    private String specialty;
    private String crm;
    private LocalTime shiftStart;
    private LocalTime shiftEnd;
}