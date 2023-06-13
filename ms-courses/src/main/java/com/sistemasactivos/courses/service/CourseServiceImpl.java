package com.sistemasactivos.courses.service;

import com.sistemasactivos.courses.client.UserClientRest;
import com.sistemasactivos.courses.model.Course;
import com.sistemasactivos.courses.model.User;
import com.sistemasactivos.courses.model.UserCourse;
import com.sistemasactivos.courses.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserClientRest userClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Course> listAllCourses() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> getCourseByIdWithUsers(Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()){
            Course course = courseOptional.get();
            if (!course.getUserCourses().isEmpty()){
                List<Long> ids = course
                        .getUserCourses()
                        .stream()
                        .map(cu -> cu.getUserId())
                        .toList();

                List<User> users = userClientRest.findUserByCourseId(ids);
                course.setUsers(users);
            }
            return Optional.of(course);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<User> setUserToCourse(User user, Long courseId) {
        Optional<Course> userOptional = courseRepository.findById(courseId);
        if (userOptional.isPresent()){
            User userMsvc = userClientRest.details(user.getId());

            Course course = userOptional.get();

            UserCourse userCourse = new UserCourse();
            userCourse.setUserId(userMsvc.getId());
            course.addUserCourse(userCourse);
            courseRepository.save(course);

            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> removeUserFromCourse(User user, Long courseId) {
        Optional<Course> userOptional = courseRepository.findById(courseId);
        if (userOptional.isPresent()){
            User userMsvc = userClientRest.details(user.getId());

            Course course = userOptional.get();

            UserCourse userCourse = new UserCourse();
            userCourse.setUserId(userMsvc.getId());
            course.removeUserCourse(userCourse);
            courseRepository.save(course);

            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> createUser(User user, Long courseId) {
        Optional<Course> userOptional = courseRepository.findById(courseId);
        if (userOptional.isPresent()){
            User newUserMsvc = userClientRest.create(user);

            Course course = userOptional.get();

            UserCourse userCourse = new UserCourse();
            userCourse.setUserId(newUserMsvc.getId());
            course.addUserCourse(userCourse);
            courseRepository.save(course);

            return Optional.of(newUserMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteUserCoursesById(Long userId) {
        courseRepository.deleteUserCoursesById(userId);
    }
}
