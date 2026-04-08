package br.inatel.debug_doctors.controller;

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
}
