package com.sistemasactivos.users.service;

import com.sistemasactivos.users.client.CourseClientRest;
import com.sistemasactivos.users.model.User;
import com.sistemasactivos.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseClientRest courseClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
        courseClientRest.deleteUserCourses(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findUsersById(Iterable<Long> ids) {
        return (List<User>) userRepository.findAllById(ids);
    }
}
