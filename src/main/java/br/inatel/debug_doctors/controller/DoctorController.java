package br.inatel.debug_doctors.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @GetMapping("/get")
    public String getDoctor() {
      return "Hello Doctor!";
    }
}
