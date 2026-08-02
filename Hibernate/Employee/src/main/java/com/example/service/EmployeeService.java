package com.example.service;

import com.example.entity.Employee;
import com.example.entity.Passport;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeeService {

    // Create Employee
    public void createEmployee(Employee employee) {

        if (employee.getName() == null || employee.getDepartment() == null) {

            System.out.println("Employee details cannot be null.");
            return;
        }

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Passport passport = employee.getPassport();

            if (passport != null) {

                List<Passport> passports = session.createQuery(
                                "from Passport where passportNumber=:number", Passport.class)
                        .setParameter("number", passport.getPassportNumber())
                        .list();

                if (!passports.isEmpty()) {

                    System.out.println("Duplicate passport number.");
                    transaction.rollback();
                    System.out.println("Transaction rolled back.");
                    return;
                }
            }

            session.persist(employee);

            transaction.commit();

            System.out.println("Employee created successfully.");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive())
                transaction.rollback();

            e.printStackTrace();
        }
    }

    // Retrieve Employee

    public void getEmployee(int id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Employee employee = session.get(Employee.class, id);

            if (employee == null) {

                System.out.println("Employee not found.");
                return;
            }

            System.out.println(employee);

        }

    }

    // Delete Employee

    public void deleteEmployee(int id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Employee employee = session.get(Employee.class, id);

            if (employee == null) {

                System.out.println("Employee not found.");
                transaction.rollback();
                return;
            }

            session.remove(employee);

            transaction.commit();

            System.out.println("Employee deleted successfully.");

        } catch (Exception e) {

            if (transaction != null && transaction.isActive())
                transaction.rollback();

            e.printStackTrace();
        }

    }

    // Retrieve All Employees

    public void getAllEmployees() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<Employee> employees =
                    session.createQuery("from Employee", Employee.class).list();

            if (employees.isEmpty()) {

                System.out.println("No employees found.");
                return;
            }

            employees.forEach(System.out::println);

        }

    }

}