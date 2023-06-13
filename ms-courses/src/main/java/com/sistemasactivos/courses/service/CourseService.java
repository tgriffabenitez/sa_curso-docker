package com.sistemasactivos.courses.service;

import com.sistemasactivos.courses.model.Course;
import com.sistemasactivos.courses.model.User;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> listAllCourses();
    Optional<Course> getCourseById(Long id);
    Optional<Course> getCourseByIdWithUsers(Long id);
    Course saveCourse(Course course);
    void deleteCourseById(Long id);

    Optional<User> setUserToCourse(User user, Long courseId);
    Optional<User> removeUserFromCourse(User user, Long courseId);
    Optional<User> createUser(User user, Long courseId);

    void deleteUserCoursesById(Long userId);
}
