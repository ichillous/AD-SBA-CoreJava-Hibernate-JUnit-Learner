package sba.sms.services;

import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.mapping.Collection;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * StudentService is a concrete class. This class implements the
 * StudentI interface, overrides all abstract service methods and
 * provides implementation for each method. Lombok @Log used to
 * generate a logger file.
 */

public class StudentService implements StudentI{
    private Session session;
    private CourseService courseService;
    private Course course;

    public StudentService() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    @Override
    public void createStudent(Student student) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(student);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAllStudents() {
        Transaction tx = null;
        List<Student> students = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            students = session.createQuery("from Student", Student.class).list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public Student getStudentByEmail(String email) {
        Transaction tx = null;
        Student student = null;
        try {
            tx = session.beginTransaction();
            student = session.get(Student.class, email);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public boolean validateStudent(String email, String password) {
        Transaction tx = null;
        Student student = null;
        try {
            tx = session.beginTransaction();
            student = session.get(Student.class, email);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return student != null && student.getPassword().equals(password);
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Student student = session.get(Student.class, email);
            Course course = session.get(Course.class, courseId);

            if (student != null && course != null && !student.getCourses().contains(course)) {
                student.getCourses().add(course);
                session.update(student);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        Transaction tx = null;
        List<Course> courses = null;
        try {
            tx = session.beginTransaction();
            Student student = session.get(Student.class, email);
            if (student != null) {
                List<Course> list = new ArrayList<>();
                for (Course course1 : student.getCourses()) {
                    list.add(course1);
                }
                courses = list;
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return courses;
    }
}
