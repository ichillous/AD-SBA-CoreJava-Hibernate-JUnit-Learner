package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class StudentServiceTest {

    static StudentService studentService;
    static CourseService courseService;

    @BeforeAll
    static void setup() {
        studentService = new StudentService();
        courseService = new CourseService();

        // Setting up courses for testing purposes
        Course course1 = new Course(1, "Math", "Dr. Smith", new LinkedHashSet<>());
        Course course2 = new Course(2, "History", "Dr. Brown", new LinkedHashSet<>());

        courseService.createCourse(course1);
        courseService.createCourse(course2);

        // Setting up students for testing purposes
        Student student1 = new Student("student1@example.com", "John Doe", "password123", new LinkedHashSet<>());
        Student student2 = new Student("student2@example.com", "Jane Smith", "password456", new LinkedHashSet<>());

        studentService.createStudent(student1);
        studentService.createStudent(student2);
    }

    @Test
    void testCreateStudent() {
        Student student = new Student("student3@example.com", "Timmy Turner", "password789", new LinkedHashSet<>());
        studentService.createStudent(student);

        Student fetchedStudent = studentService.getStudentByEmail("student3@example.com");
        assertThat(fetchedStudent).isNotNull();
        assertThat(fetchedStudent.getName()).isEqualTo("Timmy Turner");
        assertThat(fetchedStudent.getPassword()).isEqualTo("password789");
    }

}