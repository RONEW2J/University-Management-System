package view;

import java.util.List;
import java.util.Scanner;
import entities.Student;
import entities.Faculty;
import entities.Course;

public class StudentView {
    public void showStudentDetails(Student student) {
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("Student Details:");
        System.out.println("ID: " + student.getStudentId());
        System.out.println("Name: " + student.getFirstName() + " " + student.getLastName());
        System.out.println("Faculty: " + student.getFaculty().getName());
        System.out.println("Enrolled Courses: " + student.getEnrolledCourses());
    }
}