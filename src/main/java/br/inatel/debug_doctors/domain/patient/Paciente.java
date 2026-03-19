package br.inatel.debug_doctors.domain.patient;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "patient")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String cpf;
    private String email;
}