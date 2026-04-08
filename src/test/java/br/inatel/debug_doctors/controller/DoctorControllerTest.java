package br.inatel.debug_doctors.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DoctorControllerTest {

    @Test
    void shouldReturnHelloDoctorMessage() {
        // Arrange: Instantiate the controller
        DoctorController controller = new DoctorController();

        // Act: Call the function that simulates the endpoint
        String response = controller.getDoctor();

        // Assert: Validate if the response is exactly as expected
        assertEquals("Hello Doctor!", response, "The controller message should be 'Hello Doctor!'");
    }
}