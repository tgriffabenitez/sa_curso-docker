package com.sistemasactivos.users.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-courses", url = "${msvc.courses.url}")
public interface CourseClientRest {

    @DeleteMapping("/course/{userId}")
    void deleteUserCourses(@PathVariable Long userId);
}
