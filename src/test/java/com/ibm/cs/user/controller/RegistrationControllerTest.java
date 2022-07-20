package com.ibm.cs.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void registerUser() throws Exception {

        mvc.perform(post("/registration")
                        .content("{\n" +
                                "  \"userName\": \"testUserName\",\n" +
                                "  \"password\": \"IbmTest$1357\",\n" +
                                "  \"ipAddress\": \"24.67.0.1\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid").isNotEmpty())
                .andExpect(jsonPath("$.welcomeMessage").value("Welcome testUserName from West Kelowna"));
    }

    @Test
    public void registerUser_UserNameFieldErrors() throws Exception {
        mvc.perform(post("/registration")
                        .content("{\n" +
                                "  \"password\": \"IbmTest$1357\",\n" +
                                "  \"ipAddress\": \"24.67.0.1\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].field").value("userName"));
    }


    @Test
    public void registerUser_AllFieldErrors() throws Exception {
        mvc.perform(post("/registration")
                        .content("{\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(6));
    }

    @Test
    public void registerUser_PasswordFieldErrors() throws Exception {
        mvc.perform(post("/registration")
                        .content("{\n" +
                                "  \"userName\": \"testUserName\",\n" +
                                "  \"password\": \"IbmTest@1357\",\n" +
                                "  \"ipAddress\": \"24.67.0.1\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].field").value("password"))
                .andExpect(jsonPath("$[0].errorMessage").value("Password need to be greater than 8 characters, containing at least 1 number, 1 Capitalized letter, 1 special character in this set \"_ # $ % .\""));
    }

    @Test
    public void registerUser_InvalidGeo() throws Exception {

        mvc.perform(post("/registration")
                        .content("{\n" +
                                "  \"userName\": \"testUserName\",\n" +
                                "  \"password\": \"IbmTest$1357\",\n" +
                                "  \"ipAddress\": \"test\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].field").value("ipAddress"))
                .andExpect(jsonPath("$[0].errorMessage").value("User is not eligible for register"));
    }
}
