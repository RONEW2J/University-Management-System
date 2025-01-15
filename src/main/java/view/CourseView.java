package view;

import java.util.List;
import java.util.Scanner;
import entities.Course;

public class CourseView {
    public void showCourseDetails(Course course) {
        if (course == null) {
            System.out.println("Course not found.");
        } else {
            System.out.println("Course Details:");
            System.out.println("ID: " + course.getCourseId());
            System.out.println("Name: " + course.getCourseName());
            System.out.println("Credits: " + course.getCourseCredits());
            System.out.println("Duration: " + course.getCourseDuration());
        }
    }

}
