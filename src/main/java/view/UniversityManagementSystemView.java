package view;

import entities.*;

import java.util.List;
import java.util.Scanner;

class UniversityManagementView {
    private final UniversityManagementSystem managementSystem;
    private final Scanner scanner;

    public UniversityManagementView(UniversityManagementSystem managementSystem) {
        this.managementSystem = managementSystem;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        System.out.println("===== University Management System =====");
        System.out.println("1. Register Student");
        System.out.println("2. Register Teacher");
        System.out.println("3. Create Course");
        System.out.println("4. Assign Course to Teacher");
        System.out.println("5. Add Student to Course");
        System.out.println("6. Add Grade");
        System.out.println("7. Calculate Average Grade for a Course");
        System.out.println("8. Get Students by Faculty");
        System.out.println("0. Exit");
    }

    public void handleInput() {
        int choice;
        do {
            showMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> registerStudent();
                case 2 -> registerTeacher();
                case 3 -> createCourse();
                case 4 -> assignCourseToTeacher();
                case 5 -> addStudentToCourse();
                case 6 -> addGrade();
                case 7 -> calculateAverageGrade();
                case 8 -> getStudentsByFaculty();
                case 0 -> System.out.println("Exiting system.");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private void registerStudent() {
        System.out.println("=== Register Student ===");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        // You would typically select faculty and courses here. We'll simplify.
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        managementSystem.registerStudent(student);
    }

    private void registerTeacher() {
        System.out.println("=== Register Teacher ===");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        Teacher teacher = new Teacher();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setEmail(email);
        managementSystem.registerTeacher(teacher);
    }

    private void createCourse() {
        System.out.println("=== Create Course ===");
        System.out.print("Course Name: ");
        String courseName = scanner.nextLine();
        Course course = new Course();
        course.setCourseName(courseName);
        managementSystem.createCourse(course);
    }

    private void assignCourseToTeacher() {
        System.out.println("=== Assign Course to Teacher ===");
        System.out.print("Teacher ID: ");
        int teacherId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        // Fetch teacher and course objects (omitting fetching logic for simplicity)
        Teacher teacher = new Teacher(); // Replace with actual retrieval logic
        Course course = new Course(); // Replace with actual retrieval logic
        managementSystem.assignCourseToTeacher(course, teacher);
    }

    private void addStudentToCourse() {
        System.out.println("=== Add Student to Course ===");
        System.out.print("Student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        // Fetch student and course objects (omitting fetching logic for simplicity)
        Student student = new Student(); // Replace with actual retrieval logic
        Course course = new Course(); // Replace with actual retrieval logic
        managementSystem.addStudentToCourse(student, course);
    }

    private void addGrade() {
        System.out.println("=== Add Grade ===");
        System.out.print("Student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("GPA: ");
        double gpa = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Semester Year: ");
        String semesterYear = scanner.nextLine();
        System.out.print("Semester: ");
        String semester = scanner.nextLine();
        // Fetch student and course objects (omitting fetching logic for simplicity)
        Student student = new Student(); // Replace with actual retrieval logic
        Course course = new Course(); // Replace with actual retrieval logic
        managementSystem.addGrade(student, course, gpa, semesterYear, semester);
    }

    private void calculateAverageGrade() {
        System.out.println("=== Calculate Average Grade ===");
        System.out.print("Course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        // Fetch course object (omitting fetching logic for simplicity)
        Course course = new Course(); // Replace with actual retrieval logic
        double averageGrade = managementSystem.getAverageGradeByCourse(course);
        System.out.println("Average grade for course: " + averageGrade);
    }

    private void getStudentsByFaculty() {
        System.out.println("=== Get Students by Faculty ===");
        System.out.print("Faculty ID: ");
        int facultyId = scanner.nextInt();
        scanner.nextLine();
        // Fetch faculty object (omitting fetching logic for simplicity)
        Faculty faculty = new Faculty(); // Replace with actual retrieval logic
        List<Student> students = managementSystem.getStudentsByFaculty(faculty);
        if (students.isEmpty()) {
            System.out.println("No students found for the given faculty.");
        } else {
            System.out.println("Students in faculty:");
            for (Student student : students) {
                System.out.println(student.getFirstName() + " " + student.getLastName());
            }
        }
    }
}
