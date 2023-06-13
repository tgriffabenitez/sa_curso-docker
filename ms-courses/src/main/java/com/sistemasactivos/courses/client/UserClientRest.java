package com.sistemasactivos.courses.client;

import com.sistemasactivos.courses.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "msvc-users", url = "${msvc.users.url}")
public interface UserClientRest {

    @GetMapping("/{id}")
    User details(@PathVariable Long id);

    @GetMapping("/ids")
    List<User> findUserByCourseId(@RequestParam Iterable<Long> ids);

    @PostMapping("")
    User create(User user);
}
