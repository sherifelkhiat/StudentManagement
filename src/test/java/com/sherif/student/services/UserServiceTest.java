package com.sherif.student.services;

import com.sherif.student.entities.User;
import com.sherif.student.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setFullName("Test User");
    }

    @Test
    void allUsers_shouldReturnListOfUsers() {
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.allUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user.getId(), result.get(0).getId());
        verify(userRepository).findAll();
    }

    @Test
    void findByEmail_shouldReturnUserWhenEmailExists() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals(user.getEmail(), result.get().getEmail());
        verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    void findByEmail_shouldReturnEmptyWhenEmailDoesNotExist() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        Optional<User> result = userService.findByEmail("nonexistent@example.com");

        assertFalse(result.isPresent());
        verify(userRepository).findByEmail("nonexistent@example.com");
    }
}
