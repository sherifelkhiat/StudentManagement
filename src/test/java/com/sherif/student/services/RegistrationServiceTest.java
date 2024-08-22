package com.sherif.student.services;

import com.sherif.student.dtos.RegistrationRequest;
import com.sherif.student.entities.Course;
import com.sherif.student.entities.Registration;
import com.sherif.student.entities.User;
import com.sherif.student.repositories.CourseRepository;
import com.sherif.student.repositories.RegistrationRepository;
import com.sherif.student.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegistrationService registrationService;

    private User user;
    private Course course;
    private RegistrationRequest request;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);

        course = new Course();
        course.setId(1L);

        request = new RegistrationRequest();
        request.setCourseId(1L);
        request.setRegistrationDate(LocalDate.now());
    }

    @Test
    void registerToCourse_shouldRegisterUserToCourse() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(registrationRepository.save(any(Registration.class))).thenReturn(new Registration());

        Registration registration = registrationService.registerToCourse(1L, request);

        assertNotNull(registration);
        verify(userRepository).findById(1L);
        verify(courseRepository).findById(1L);
        verify(registrationRepository).save(any(Registration.class));
    }

    @Test
    void cancelRegistration_shouldDeleteRegistration() {
        Registration registration = new Registration();
        registration.setUser(user);
        registration.setCourse(course);
        registration.setRegistrationDate(LocalDate.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(registrationRepository.findByUserAndCourseAndRegistrationDate(user, course, request.getRegistrationDate())).thenReturn(Optional.of(registration));

        registrationService.cancelRegistration(1L, request);

        verify(registrationRepository).delete(registration);
    }

    @Test
    void cancelRegistration_shouldThrowExceptionWhenRegistrationNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(registrationRepository.findByUserAndCourseAndRegistrationDate(user, course, request.getRegistrationDate())).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                registrationService.cancelRegistration(1L, request));

        assertEquals(RegistrationService.REGISTRATION_NOT_FOUND_FOR_THE_GIVEN_USER_AND_COURSE, exception.getMessage());
    }

    @Test
    void getRegistrationsForUser_shouldReturnListOfRegistrations() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        registrationService.getRegistrationsForUser(1L);

        verify(registrationRepository).findByUser(user);
    }
}
