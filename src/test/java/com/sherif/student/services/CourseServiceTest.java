package com.sherif.student.services;

import com.sherif.student.entities.Course;
import com.sherif.student.repositories.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private Course course;

    @BeforeEach
    void setUp() {
        course = Course.builder()
                .id(1L)
                .name("Math 101")
                .description("Basic Mathematics")
                .build();
    }

    @Test
    void getAllCourses_shouldReturnListOfCourses() {
        List<Course> courses = Arrays.asList(course);
        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseService.getAllCourses();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(course.getId(), result.get(0).getId());
        verify(courseRepository).findAll();
    }

    @Test
    void createCourse_shouldSaveAndReturnCourse() {
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course result = courseService.createCourse(course);

        assertNotNull(result);
        assertEquals(course.getId(), result.getId());
        verify(courseRepository).save(course);
    }
}
