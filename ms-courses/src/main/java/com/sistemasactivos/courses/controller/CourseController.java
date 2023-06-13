package com.sistemasactivos.courses.controller;

import com.sistemasactivos.courses.model.Course;
import com.sistemasactivos.courses.model.User;
import com.sistemasactivos.courses.service.CourseService;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("")
    public ResponseEntity<List<Course>> findAllCourses() {
        return new ResponseEntity<>(courseService.listAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCourseById(@PathVariable Long id) {
        Optional<Course> course = courseService.getCourseByIdWithUsers(id);
        return course.isPresent() ? ResponseEntity.ok(course) : ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<?> saveCourse(@Valid @RequestBody Course course, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return result(bindingResult);
        }
        return new ResponseEntity<>(courseService.saveCourse(course), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyCourse(@RequestBody Course course, @PathVariable Long id) {
        Optional<Course> courseOptional = courseService.getCourseByIdWithUsers(id);
        if (courseOptional.isPresent()) {
            Course courseUpdated = courseOptional.get();
            courseUpdated.setName(course.getName());
            return new ResponseEntity<>(courseService.saveCourse(courseUpdated), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isPresent()) {
            courseService.deleteCourseById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> result(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/user/{courseId}")
    public ResponseEntity<?> assignUser(@RequestBody User user, @PathVariable Long courseId) {
        Optional<User> o;

        try {
            o = courseService.setUserToCourse(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user/{courseId}")
    public ResponseEntity<?> createUser(@RequestBody User user, @PathVariable Long courseId) {
        Optional<User> o;

        try {
            o = courseService.createUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        }

        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/user/{courseId}")
    public ResponseEntity<?> removeUser(@RequestBody User user, @PathVariable Long courseId) {
        Optional<User> o;

        try {
            o = courseService.removeUserFromCourse(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        }

        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/course/{userId}")
    public ResponseEntity<?> deleteUserCourses(@PathVariable Long userId) {
        courseService.deleteUserCoursesById(userId);
        return ResponseEntity.noContent().build();
    }

}
