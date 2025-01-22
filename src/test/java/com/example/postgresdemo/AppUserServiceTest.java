package com.example.postgresdemo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class AppUserServiceTest {

    @Mock
    private AppUserRepository appUserRepository; // Mock the repository

    @InjectMocks
    private AppUserService appUserService; // Inject the mock into the service

    private AppUser testUser;
    private List<AppUser> userList;

    @Before
    public void setUp() {
        // Set up the test data
        testUser = new AppUser(1L, "john.doe", "john.doe@example.com");
        userList = Arrays.asList(testUser, new AppUser(2L, "jane.doe", "jane.doe@example.com"));
    }

    @Test
    public void testFindByUsername() {
        // Mock the repository call
        Mockito.when(appUserRepository.findByUsername("john.doe")).thenReturn(testUser);

        // Call the service method
        AppUser foundUser = appUserService.findByUsername("john.doe");

        // Verify the interaction with the repository
        Mockito.verify(appUserRepository).findByUsername("john.doe");

        // Assert the result
        assert(foundUser != null);
        assert(foundUser.getUsername().equals("john.doe"));
        assert(foundUser.getEmail().equals("john.doe@example.com"));
    }

    @Test
    public void testSaveUser() {
        // Mock the repository save method
        Mockito.when(appUserRepository.save(testUser)).thenReturn(testUser);

        // Call the service method
        AppUser savedUser = appUserService.save(testUser);

        // Verify the repository method
        Mockito.verify(appUserRepository).save(testUser);

        // Assert the result
        assert(savedUser != null);
        assert(savedUser.getUsername().equals("john.doe"));
        assert(savedUser.getEmail().equals("john.doe@example.com"));
    }

    @Test
    public void testFindAllUsers() {
        // Mock the repository call
        Mockito.when(appUserRepository.findAll()).thenReturn(userList);

        // Call the service method
        List<AppUser> foundUsers = appUserService.findAll();

        // Verify the interaction with the repository
        Mockito.verify(appUserRepository).findAll();

        // Assert the result
        assert(foundUsers.size() == 2);
    }

    @Test
    public void testDeleteByUsername_UserExists() {
        // Mock the repository methods
        Mockito.when(appUserRepository.findByUsername("john.doe")).thenReturn(testUser);

        // Call the delete service method
        appUserService.deleteByUsername("john.doe");

        // Verify repository interaction
        Mockito.verify(appUserRepository).findByUsername("john.doe");
        Mockito.verify(appUserRepository).delete(testUser);
    }

    @Test
    public void testDeleteByUsername_UserNotFound() {
        // Mock the repository to return null for the non-existing user
        Mockito.when(appUserRepository.findByUsername("non.existent")).thenReturn(null);

        // Call the delete service method
        appUserService.deleteByUsername("non.existent");

        // Verify repository methods
        Mockito.verify(appUserRepository).findByUsername("non.existent");
        Mockito.verify(appUserRepository, never()).delete(any()); // Should not call delete
    }
}
