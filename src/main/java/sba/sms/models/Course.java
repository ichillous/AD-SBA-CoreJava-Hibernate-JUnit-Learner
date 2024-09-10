package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Course is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'course' in the database. A Course object contains fields that represent course
 * information and a mapping of 'courses' that indicate an inverse or referencing side
 * of the relationship. Implement Lombok annotations to eliminate boilerplate code.
 */
@Entity
@Table(name = "course")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "instructor", nullable = false, length = 50)
    private String instructor;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Student> students = new HashSet<>();

    public Course(String name, String instructor) {
        this.name = name;
        this.instructor = instructor;
    }
}
