package com.example.postgresdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;  // JUnit 4 annotation
import org.junit.runner.RunWith;  // JUnit 4 runner
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AppUserController.class)
public class AppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Used for making HTTP requests to the controller

    @MockBean
    private AppUserServiceInterface appUserService;  // Mock the service

    @Test
    public void testFindByUsername() throws Exception {
        Long id = 123L;
        String username = "John Doe";
        AppUser user = new AppUser(id, username, "john.doe@example.com");

        // Mock the service method
        Mockito.when(appUserService.findByUsername(username)).thenReturn(user);

        // Perform the request and verify the response
        mockMvc.perform(get("/users/get/{username}", username))
                .andExpect(status().isOk())  // Expect HTTP 200 OK
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    public void testSaveUser() throws Exception {
        Long id = 123L;
        String username = "John Doe";
        AppUser user = new AppUser(id, username, "john.doe@example.com");

        // Mock the service method
        Mockito.when(appUserService.save(Mockito.any(AppUser.class))).thenReturn(user);

        // Perform the request and verify the response
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())  // Expect HTTP 200 OK
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

}
