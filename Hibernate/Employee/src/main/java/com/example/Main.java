package com.example;

import com.example.entity.Employee;
import com.example.entity.Passport;
import com.example.service.EmployeeService;

public class Main {

    public static void main(String[] args) {

        EmployeeService service = new EmployeeService();

        // Create Passport
        Passport passport = new Passport(
                "P123456",
                "India"
        );

        // Create Employee
        Employee employee = new Employee(
                "Sandhya",
                "CSE"
        );

        // Assign Passport to Employee
        employee.setPassport(passport);

        // Create Employee with Passport
        service.createEmployee(employee);

        // Retrieve All Employees
        service.getAllEmployees();

        // Retrieve Employee with Passport
        service.getEmployee(1);

        // Delete Employee (Cascade deletes Passport)
        service.deleteEmployee(1);
    }
}