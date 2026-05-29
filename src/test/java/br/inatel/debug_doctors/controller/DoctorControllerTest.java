package br.inatel.debug_doctors.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DoctorControllerTest {

    // @InjectMocks instancia o controller e injeta qualquer @Mock declarado
    // automaticamente — sem precisar de "new DoctorController()" manual
    @InjectMocks
    private DoctorController doctorController;

    @Test
    void shouldReturnHelloDoctorMessage() {
        // Act: chama o endpoint diretamente pelo controller injetado
        String response = doctorController.getDoctor();

        // Assert
        assertEquals("Hello Doctor!", response,
                "The controller message should be 'Hello Doctor!'");
    }
}