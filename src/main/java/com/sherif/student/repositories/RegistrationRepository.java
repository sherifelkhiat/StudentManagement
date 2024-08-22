package com.sherif.student.repositories;

import com.sherif.student.entities.Course;
import com.sherif.student.entities.Registration;
import com.sherif.student.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    Optional<Registration> findByUserAndCourseAndRegistrationDate(User user, Course course, LocalDate registrationDate);

    List<Registration> findByUser(User user);
}
