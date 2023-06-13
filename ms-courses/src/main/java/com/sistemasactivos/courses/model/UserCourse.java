package com.sistemasactivos.courses.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_course")
public class UserCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true)
    private Long userId;

    /*
     * Sobreescribo el metodo equals para que compare por el id del usuario
     * y no por la instancia de la clase usuario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof UserCourse userCourse))
            return false;

        return this.userId != null && this.userId.equals(userCourse.getUserId());
    }
}
