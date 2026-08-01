package com.example.student.service;

import com.example.student.entity.Student;
import com.example.student.exception.DuplicateEmailException;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // Add Student
    public Student addStudent(Student student) {

        if (repository.findByEmail(student.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email already exists");
        }


        return repository.save(student);
    }

    // Get All Students
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    // Get Student By Id
    public Student getStudentById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with id " + id));
    }

    // Update Student
    public Student updateStudent(Long id, Student student) {

        Student existingStudent = repository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with id " + id));

        // Check duplicate email
        if (!existingStudent.getEmail().equals(student.getEmail())
                && repository.findByEmail(student.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email already exists");
        }

        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setPhoneNumber(student.getPhoneNumber());
        existingStudent.setDepartment(student.getDepartment());
        existingStudent.setYearOfStudy(student.getYearOfStudy());
        existingStudent.setCgpa(student.getCgpa());

        return repository.save(existingStudent);
    }

    // Delete Student
    public void deleteStudent(Long id) {

        Student student = repository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with id " + id));

        repository.delete(student);
    }

    // Search By Department
    public List<Student> getStudentsByDepartment(String department) {

        return repository.findByDepartment(department);
    }

    // Search By CGPA
    public List<Student> getStudentsByCgpa(double cgpa) {

        return repository.findByCgpaGreaterThan(cgpa);
    }

}