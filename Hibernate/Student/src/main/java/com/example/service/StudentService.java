package com.example.service;

import com.example.entity.Student;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StudentService {

    // Insert Student
    public void insertStudent(Student student) {

        // Null Validation
        if (student.getName() == null ||
                student.getEmail() == null ||
                student.getDepartment() == null) {

            System.out.println("Null values are not allowed.");
            return;
        }

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            List<Student> students = session
                    .createQuery("from Student where email=:email", Student.class)
                    .setParameter("email", student.getEmail())
                    .list();

            if (!students.isEmpty()) {
                System.out.println("Duplicate email found.");
                transaction.rollback();
                System.out.println("Transaction rolled back.");
                return;
            }

            session.persist(student);

            transaction.commit();

            System.out.println("Student inserted successfully.");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }
    // Update Student
    public void updateStudent(int id, String name, String department) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Student student = session.get(Student.class, id);

            if (student == null) {
                System.out.println("Student not found.");
                transaction.rollback();
                return;
            }

            student.setName(name);
            student.setDepartment(department);

            session.merge(student);

            transaction.commit();

            System.out.println("Student updated successfully.");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    // Delete Student
    public void deleteStudent(int id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Student student = session.get(Student.class, id);

            if (student == null) {
                System.out.println("Student not found.");
                transaction.rollback();
                return;
            }

            session.remove(student);

            transaction.commit();

            System.out.println("Student deleted successfully.");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    // Retrieve by ID
    public void getStudentById(int id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Student student = session.get(Student.class, id);

            if (student == null) {
                System.out.println("Student not found.");
                return;
            }

            System.out.println(student);
        }
    }

    // Retrieve All Students
    public void getAllStudents() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<Student> students =
                    session.createQuery("from Student", Student.class).list();

            if (students.isEmpty()) {
                System.out.println("No students found.");
                return;
            }

            students.forEach(System.out::println);
        }
    }
}