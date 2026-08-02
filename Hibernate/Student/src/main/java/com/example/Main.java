package com.example;

import com.example.entity.Student;
import com.example.service.StudentService;

public class Main {

    public static void main(String[] args) {

        StudentService service = new StudentService();

        // Create Student
        Student student = new Student(
                "Sandhya",
                "sandhya@gmail.com",
                "CSE"
        );

        // Insert Student
        service.insertStudent(student);

        // Retrieve All Students
        service.getAllStudents();

        // Retrieve Student by ID
        service.getStudentById(1);

        // Update Student
        service.updateStudent(1, "Sandhya R", "AI & DS");

        // Delete Student
        service.deleteStudent(1);
    }
}