package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;


/**
 * Student is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'student' in the database. A Student object contains fields that represent student
 * login credentials and a join table containing a registered student's email and course(s)
 * data. The Student class can be viewed as the owner of the bi-directional relationship.
 * Implement Lombok annotations to eliminate boilerplate code.
 */
@Entity
@Table(name = "student")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_email"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();
    public Student(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}



