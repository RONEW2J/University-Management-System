package view;

import java.util.List;
import java.util.Scanner;
import entities.Grade;
import entities.Student;
import entities.Course;

public class GradeView {
  public void showGradeDetails(Grade grade) {
    if (grade != null) {
      System.out.println("Grade Details:");
      System.out.println("ID: " + grade.getGradeId());
      System.out.println("GPA: " + grade.getGPA());
      System.out.println("Semester: " + grade.getSemester());
      System.out.println("Semester Year: " + grade.getSemesterYear());
      System.out.println("Student: " + grade.getStudent().getFirstName());
      System.out.println("Course: " + grade.getCourse().getCourseName());
    } else {
      System.out.println("Grade not found.");
    }
  }
}