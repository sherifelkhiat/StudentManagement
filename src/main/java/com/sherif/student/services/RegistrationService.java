package com.sherif.student.services;

import com.sherif.student.dtos.RegistrationRequest;
import com.sherif.student.entities.Course;
import com.sherif.student.entities.Registration;
import com.sherif.student.entities.User;
import com.sherif.student.repositories.CourseRepository;
import com.sherif.student.repositories.RegistrationRepository;
import com.sherif.student.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {
    public static final String REGISTRATION_NOT_FOUND_FOR_THE_GIVEN_USER_AND_COURSE = "Registration not found for the given user and course";
    private final RegistrationRepository registrationRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public RegistrationService(
            CourseRepository courseRepository,
            RegistrationRepository registrationRepository,
            UserRepository userRepository
    ) {
        this.courseRepository = courseRepository;
        this.registrationRepository = registrationRepository;
        this.userRepository = userRepository;
    }

    public Registration registerToCourse(Long userId, RegistrationRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        Course course = courseRepository.findById(request.getCourseId()).orElseThrow();
        Registration registration = Registration.builder()
                .course(course)
                .registrationDate(request.getRegistrationDate())
                .user(user).build();
        return registrationRepository.save(registration);
    }

    public void cancelRegistration(Long userId, RegistrationRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        Course course = courseRepository.findById(request.getCourseId()).orElseThrow();
        Optional<Registration> registration = registrationRepository.findByUserAndCourseAndRegistrationDate(user, course, request.getRegistrationDate());
        if (registration.isPresent()) {
            registrationRepository.delete(registration.get());
        } else {
            throw new IllegalStateException(REGISTRATION_NOT_FOUND_FOR_THE_GIVEN_USER_AND_COURSE);
        }
    }

    public List<Registration> getRegistrationsForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return registrationRepository.findByUser(user);
    }
}

