package com.proyectoprogra3.proyectoprogra3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProyectController.class)
public class ProyectoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddMemberToList() throws Exception {
        // Define the request body with the member data to add
        String memberToAdd = "4 Eve Johnson";

        // Perform a POST request to the /getinfo/list/add endpoint
        mockMvc.perform(post("/getinfo/list/add")
                .content(memberToAdd)
                .contentType("text/plain"))
                .andExpect(status().isOk());
    }

}
