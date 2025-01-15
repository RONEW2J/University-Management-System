package Controllers;

import Controllers.dao.CourseDao;
import Controllers.dao.GradeDao;
import Controllers.dao.StudentDao;
import entities.Grade;
import entities.Student;
import entities.Course;

import java.util.List;
import java.util.Scanner;

public class GradeController implements ControllerStrategy{

    private final GradeDao gradeDao; // Data Access Object for Grade operations
    private final Scanner scanner;  // Scanner for user input

    // Constructor to initialize GradeDao and Scanner
    public GradeController() {
        this.gradeDao = new GradeDao();
        this.scanner = new Scanner(System.in);
    }

    // Display the main menu options to the user
    public void showMenu() {
        System.out.println("==== Grade Management System ====");
        System.out.println("1. Add Grade");
        System.out.println("2. Update Grade");
        System.out.println("3. Delete Grade");
        System.out.println("4. Get Grade by ID");
        System.out.println("5. Get All Grades");
        System.out.println("0. Exit");
    }

    // Handle user selection and call appropriate methods
    public void handleUserSelection() {
        int choice;
        do {
            showMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Execute corresponding method based on user's choice
            switch (choice) {
                case 1 -> addGrade();
                case 2 -> updateGrade();
                case 3 -> deleteGrade();
                case 4 -> getGradeById();
                case 5 -> getAllGrades();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0); // Loop until the user chooses to exit
    }

    // Add a new grade to the database
    private void addGrade() {
        System.out.println("Enter Grade Details:");

        // Input semester year, semester, and GPA
        System.out.print("Semester Year: ");
        String semesterYear = scanner.nextLine();

        System.out.print("Semester: ");
        String semester = scanner.nextLine();

        Double GPA = null;
        while (GPA == null) { // Validate GPA input
            System.out.print("GPA: ");
            try {
                String gpaInput = scanner.nextLine();
                GPA = Double.parseDouble(gpaInput); // Parse GPA
            } catch (NumberFormatException e) {
                System.out.println("Invalid GPA. Please enter a valid number.");
            }
        }

        // Input for student and course IDs
        System.out.print("Student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer

        System.out.print("Course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer

        // Fetch student and course from their respective DAOs
        StudentDao studentDao = new StudentDao();
        CourseDao courseDao = new CourseDao();

        Student student = studentDao.getStudentById(studentId);
        Course course = courseDao.getCourseById(courseId);

        if (student != null && course != null) {
            // Create Grade object if student and course are valid
            Grade grade = new Grade(semesterYear, semester, GPA, student, course);
            gradeDao.insertGrade(grade); // Insert the grade into the database
            System.out.println("Grade added successfully for " + student.getFirstName() + " in course " + course.getCourseName());
        } else {
            // Handle invalid student or course ID
            System.out.println("Invalid Student ID or Course ID.");
        }
    }

    // Update an existing grade
    private void updateGrade() {
        System.out.print("Enter Grade ID to update: ");
        int gradeId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Fetch the existing grade by ID
        Grade existingGrade = gradeDao.getGradeById(gradeId);
        if (existingGrade == null) {
            System.out.println("Grade not found with ID: " + gradeId);
            return;
        }

        System.out.println("Enter Updated Grade Details (leave blank to keep current value):");

        // Prompt for updated values, allowing blank input to retain existing values
        System.out.print("Semester Year (current: " + existingGrade.getSemesterYear() + "): ");
        String semesterYear = scanner.nextLine();
        if (!semesterYear.isBlank()) {
            existingGrade.setSemesterYear(semesterYear);
        }

        System.out.print("Semester (current: " + existingGrade.getSemester() + "): ");
        String semester = scanner.nextLine();
        if (!semester.isBlank()) {
            existingGrade.setSemester(semester);
        }

        System.out.print("GPA (current: " + existingGrade.getGPA() + "): ");
        String gpaInput = scanner.nextLine();
        if (!gpaInput.isBlank()) {
            existingGrade.setGPA(Double.parseDouble(gpaInput));
        }

        // Update the grade in the database
        gradeDao.updateGrade(existingGrade);
    }

    // Delete a grade by its ID
    private void deleteGrade() {
        System.out.print("Enter Grade ID to delete: ");
        int gradeId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        gradeDao.deleteGrade(gradeId); // Delete the grade
    }

    // Retrieve and display a grade by its ID
    private void getGradeById() {
        System.out.print("Enter Grade ID to retrieve: ");
        int gradeId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Grade grade = gradeDao.getGradeById(gradeId); // Fetch the grade
        if (grade != null) {
            System.out.println("Grade Details:");
            System.out.println(grade);
        } else {
            System.out.println("Grade not found with ID: " + gradeId);
        }
    }

    // Retrieve and display all grades
    private void getAllGrades() {
        List<Grade> grades = gradeDao.getAllGrades(); // Fetch all grades
        if (grades.isEmpty()) {
            System.out.println("No grades found.");
        } else {
            System.out.println("All Grades:");
            for (Grade grade : grades) {
                System.out.println(grade); // Print each grade
            }
        }
    }
}
