package Controllers;

import Controllers.dao.FacultyDao;
import Controllers.dao.TeacherDao;
import entities.Course;
import entities.Faculty;
import entities.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TeacherController implements ControllerStrategy{

    private TeacherDao teacherDao;  // Data access object for handling teacher operations
    private Scanner scanner;  // Scanner for reading user input
    private FacultyDao facultyDao = new FacultyDao();  // DAO for handling faculty-related operations

    // Constructor that initializes the DAO objects and scanner
    public TeacherController() {
        this.teacherDao = new TeacherDao();  // Initialize teacher DAO
        this.scanner = new Scanner(System.in);  // Initialize scanner
    }

    // Method to display the main menu of the Teacher Management System
    public void showMenu() {
        System.out.println("==== Teacher Management System ====");
        System.out.println("1. Register Teacher");
        System.out.println("2. Update Teacher");
        System.out.println("3. Delete Teacher");
        System.out.println("4. Get Teacher by ID");
        System.out.println("5. Get All Teachers");
        System.out.println("0. Exit");
    }

    // Method to handle user selection and invoke corresponding actions
    public void handleUserSelection() {
        int choice;
        do {
            showMenu();  // Show the menu
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();  // Read user's choice
            scanner.nextLine();  // Consume newline

            // Switch-case to handle different options based on user input
            switch (choice) {
                case 1 -> registerTeacher();  // Register a new teacher
                case 2 -> updateTeacher();  // Update an existing teacher
                case 3 -> deleteTeacher();  // Delete a teacher
                case 4 -> getTeacherById();  // Retrieve a teacher by ID
                case 5 -> getAllTeachers();  // Retrieve and display all teachers
                case 0 -> System.out.println("Exiting...");  // Exit the program
                default -> System.out.println("Invalid choice. Please try again.");  // Invalid option
            }
        } while (choice != 0);  // Repeat the loop until user chooses to exit
    }

    // Method to register a new teacher
    private void registerTeacher() {
        System.out.println("Enter Teacher Details:");

        // Prompt the user for teacher details
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Faculty ID: ");
        int facultyId = scanner.nextInt();  // Read Faculty ID
        scanner.nextLine();  // Consume newline

        // Fetch Faculty object based on facultyId
        Faculty faculty = facultyDao.getFacultyById(facultyId);
        if (faculty == null) {
            System.out.println("No faculty found with ID: " + facultyId);
            return;  // Exit the method if no faculty is found
        }

        // Initialize an empty list of courses (this can be expanded later)
        List<Course> courses = new ArrayList<>();

        // Create the Teacher object with the entered details
        Teacher newTeacher = new Teacher(firstName, lastName, email, 0, faculty, courses);

        // Call the DAO method to insert the new teacher into the database
        teacherDao.insertTeacher(newTeacher);
        System.out.println("Teacher registered successfully!");  // Confirmation message
    }

    // Method to update an existing teacher
    private void updateTeacher() {
        System.out.print("Enter Teacher ID to update: ");
        int teacherId = scanner.nextInt();  // Read teacher ID to update
        scanner.nextLine();  // Consume newline

        // Fetch the existing teacher by ID
        Teacher existingTeacher = teacherDao.getTeacherById(teacherId);
        if (existingTeacher == null) {
            System.out.println("Teacher not found with ID: " + teacherId);
            return;  // Exit if teacher is not found
        }

        // Prompt the user to update teacher details, with current values shown
        System.out.println("Enter Updated Teacher Details (leave blank to keep current value):");

        System.out.print("First Name (current: " + existingTeacher.getFirstName() + "): ");
        String firstName = scanner.nextLine();
        if (!firstName.isBlank()) {
            existingTeacher.setFirstName(firstName);  // Update first name if provided
        }

        System.out.print("Last Name (current: " + existingTeacher.getLastName() + "): ");
        String lastName = scanner.nextLine();
        if (!lastName.isBlank()) {
            existingTeacher.setLastName(lastName);  // Update last name if provided
        }

        System.out.print("Email (current: " + existingTeacher.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isBlank()) {
            existingTeacher.setEmail(email);  // Update email if provided
        }

        // Call DAO method to update the teacher in the database
        teacherDao.updateTeacher(existingTeacher);
    }

    // Method to delete a teacher by ID
    private void deleteTeacher() {
        System.out.print("Enter Teacher ID to delete: ");
        int teacherId = scanner.nextInt();  // Read teacher ID to delete
        scanner.nextLine();  // Consume newline

        // Call DAO method to delete the teacher from the database
        teacherDao.deleteTeacher(teacherId);
    }

    // Method to retrieve a teacher by ID and display their details
    private void getTeacherById() {
        System.out.print("Enter Teacher ID to retrieve: ");
        int teacherId = scanner.nextInt();  // Read teacher ID to retrieve
        scanner.nextLine();  // Consume newline

        // Fetch the teacher from the database
        Teacher teacher = teacherDao.getTeacherById(teacherId);
        if (teacher != null) {
            System.out.println("Teacher Details:");
            System.out.println(teacher);  // Display teacher details
        } else {
            System.out.println("Teacher not found with ID: " + teacherId);  // Message if teacher is not found
        }
    }

    // Method to retrieve and display all teachers
    private void getAllTeachers() {
        List<Teacher> teachers = teacherDao.getAllTeachers();  // Fetch all teachers from the database
        if (teachers.isEmpty()) {
            System.out.println("No teachers found.");  // Message if no teachers exist
        } else {
            System.out.println("All Teachers:");
            for (Teacher teacher : teachers) {
                System.out.println(teacher);  // Display each teacher
            }
        }
    }
}
