package br.inatel.debug_doctors.model;

import lombok.Data;

import java.time.LocalTime;


@Data
public class Medico {
    private long id;
    private String nome;
    private String especialidade;
    private String crm;
    private LocalTime inicioExpediente;
    private LocalTime fimExpediente;
}
