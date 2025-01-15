package view;

import entities.Teacher;

public class TeacherView {
    public void showTeacherDetails(Teacher teacher) {
        if (teacher != null) {
            System.out.println("Teacher Details:");
            System.out.println("ID: " + teacher.getTeacherId());
            System.out.println("First Name: " + teacher.getFirstName());
            System.out.println("Last Name: " + teacher.getLastName());
            System.out.println("Email: " + teacher.getEmail());

            if (teacher.getFaculty() != null) {
                System.out.println("Faculty: " + teacher.getFaculty().getName());
            } else {
                System.out.println("Faculty: Not assigned");
            }

            if (teacher.getCourses() != null && !teacher.getCourses().isEmpty()) {
                System.out.println("Courses:");
                teacher.getCourses().forEach(course ->
                        System.out.println("- " + course.getCourseName()));
            } else {
                System.out.println("Courses: No courses assigned");
            }
        } else {
            System.out.println("Teacher not found.");
        }
    }
}
