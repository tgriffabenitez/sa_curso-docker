package com.sistemasactivos.courses.repository;

import com.sistemasactivos.courses.model.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface CourseRepository extends CrudRepository<Course, Long> {

    @Modifying
    @Query("DELETE FROM UserCourse uc WHERE uc.userId = ?1")
    void deleteUserCoursesById(Long userId);
}
