package br.inatel.debug_doctors.controller;

import br.inatel.debug_doctors.model.Medico;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @GetMapping("/get")
    public String getDoctor() {
      return "Hello Doctor!";
    }

    @GetMapping
    public List<Medico> listarMedicos(){
            // vamos simulando um médico
        Medico medico1 = new Medico();
        medico1.setId(1L);
        medico1.setNome("Dr. Gregory House");
        medico1.setEspecialidade("Ortopedista");
        medico1.setCrm("12345-MG");
        medico1.setInicioExpediente(LocalTime.of(8, 0)); // 08:00
        medico1.setFimExpediente(LocalTime.of(18, 0));   // 18:00

        return Arrays.asList(medico1);
    }}
