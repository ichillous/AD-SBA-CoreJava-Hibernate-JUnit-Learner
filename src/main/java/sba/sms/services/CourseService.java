package sba.sms.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sba.sms.dao.CourseI;
import sba.sms.models.Course;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * CourseService is a concrete class. This class implements the
 * CourseI interface, overrides all abstract service methods and
 * provides implementation for each method.
 */

public class CourseService implements CourseI {
    private HibernateUtil hibernateUtil;

    private Session session;
    public CourseService() {
        this.session = HibernateUtil.getSessionFactory().openSession();
    }

    public void createCourse(Course course) {
        Transaction tx = session.beginTransaction();
        session.save(course);
        tx.commit();
    }

    @Override
    public Course getCourseById(int courseId) {
        return session.get(Course.class, courseId);
    }

    public List<Course> getAllCourses() {
        Transaction tx = session.beginTransaction();
        List<Course> courses = (List<Course>) session.createQuery("from Course").list();
        tx.commit();
        return courses;
    }
}
