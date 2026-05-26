package br.inatel.debug_doctors.controller;

import br.inatel.debug_doctors.domain.doctor.Doctor;
import br.inatel.debug_doctors.dto.DoctorDTO;
import br.inatel.debug_doctors.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController controller;

    @Test
    void shouldReturnAllDoctors() {
        // Arrange
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Wagner");
        doctor.setSpecialty("Cardiologia");
        
        Mockito.when(doctorService.findAll()).thenReturn(List.of(doctor));

        // Act
        ResponseEntity<List<DoctorDTO>> response = controller.getAllDoctors();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        assertEquals("Wagner", response.getBody().get(0).getName());
    }
}