package com.learning.blogplatformapi.service;

import com.learning.blogplatformapi.model.User;
import com.learning.blogplatformapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        testUser = new User("testUser","test@example.com","rawpassword");
        when(passwordEncoder.encode("rawpassword")).thenReturn("hashedPassword");
    }

    @Test
    public void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        User createdUser = userService.createUser(testUser, "rawpassword");
        assertNotNull(createdUser);
        assertEquals("hashedPassword",createdUser.getPasswordHash());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    public void testFindById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        Optional<User> foundUser = userService.findById(1L);
        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getUsername(),foundUser.get().getUsername());
    }
}
