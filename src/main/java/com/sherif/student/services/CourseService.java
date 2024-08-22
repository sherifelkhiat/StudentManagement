package com.sherif.student.services;

import com.sherif.student.entities.Course;
import com.sherif.student.repositories.CourseRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Cacheable("courses")
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @CacheEvict(value = "courses", allEntries = true)
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }
}

