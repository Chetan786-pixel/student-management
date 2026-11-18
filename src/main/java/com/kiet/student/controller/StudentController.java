
package com.kiet.student.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.kiet.student.model.Student;

@RestController
@RequestMapping("/students")
public class StudentController {

    List<Student> students = new ArrayList<>();

    public StudentController() {

        students.add(new Student(1, "Aman", "aman@gmail.com", "CSE", 2, 8.5));
        students.add(new Student(2, "Rahul", "rahul@gmail.com", "IT", 2, 8.2));
        students.add(new Student(3, "Priya", "priya@gmail.com", "CSE", 3, 9.0));
        students.add(new Student(4, "Neha", "neha@gmail.com", "ECE", 2, 8.7));
        students.add(new Student(5, "Rohit", "rohit@gmail.com", "CSE", 3, 7.9));
    }

    // Get All Students
    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    // Get Student By ID
    @GetMapping("/{id}")
    public Object getStudentById(@PathVariable int id) {

        for (Student s : students) {
            if (s.getStudentId() == id) {
                return s;
            }
        }

        return Map.of("message", "Student not found");
    }

    // Add New Student
    @PostMapping
    public Student addStudent(@RequestBody Student student) {

        students.add(student);
        return student;
    }

    // Update Student
    @PutMapping("/{id}")
    public Object updateStudent(
            @PathVariable int id,
            @RequestBody Student updatedStudent) {

        for (Student s : students) {

            if (s.getStudentId() == id) {

                s.setName(updatedStudent.getName());
                s.setEmail(updatedStudent.getEmail());
                s.setBranch(updatedStudent.getBranch());
                s.setYear(updatedStudent.getYear());
                s.setCgpa(updatedStudent.getCgpa());

                return s;
            }
        }

        return Map.of("message", "Student not found");
    }

    // Delete Student
    @DeleteMapping("/{id}")
    public Map<String, String> deleteStudent(@PathVariable int id) {

        boolean removed = students.removeIf(
                s -> s.getStudentId() == id
        );

        if (removed) {
            return Map.of("message", "Student deleted successfully");
        }

        return Map.of("message", "Student not found");
    }

    // Search Students By Branch and Year
    @GetMapping("/search")
    public List<Student> searchStudents(
            @RequestParam String branch,
            @RequestParam int year) {

        List<Student> result = new ArrayList<>();

        for (Student s : students) {

            if (s.getBranch().equalsIgnoreCase(branch)
                    && s.getYear() == year) {

                result.add(s);
            }
        }

        return result;
    }

    // Find Students Based on CGPA
    @GetMapping("/topper")
    public List<Student> getToppers(@RequestParam double cgpa) {

        List<Student> result = new ArrayList<>();

        for (Student s : students) {

            if (s.getCgpa() >= cgpa) {
                result.add(s);
            }
        }

        return result;
    }
}
