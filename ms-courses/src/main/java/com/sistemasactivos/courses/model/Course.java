package com.sistemasactivos.courses.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "course_id")
    private List<UserCourse> userCourses = new ArrayList<>();

    @Transient
    private List<User> users = new ArrayList<>();

    public void addUserCourse(UserCourse userCourse) {
        userCourses.add(userCourse);
    }

    public void removeUserCourse(UserCourse userCourse) {
        userCourses.remove(userCourse);
    }
}

