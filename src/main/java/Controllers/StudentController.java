package Controllers;

import Controllers.dao.FacultyDao;
import Controllers.dao.StudentDao;
import entities.Faculty;
import entities.Student;

import java.util.List;
import java.util.Scanner;

public class StudentController implements ControllerStrategy{
    private FacultyDao facultyDao;  // DAO for Faculty operations
    private final StudentDao studentDao;  // DAO for Student operations
    private final Scanner scanner;  // Scanner object for reading user input

    public StudentController() {
        this.studentDao = new StudentDao();  // Initialize StudentDao
        facultyDao = new FacultyDao();  // Initialize FacultyDao
        this.scanner = new Scanner(System.in);  // Initialize Scanner
    }

    // Display the main menu for the user
    public void showMenu() {
        System.out.println("==== Student Management System ====");  // Header for the menu
        System.out.println("1. Add Student");
        System.out.println("2. Update Student");
        System.out.println("3. Delete Student");
        System.out.println("4. Get Student by ID");
        System.out.println("5. Get All Students");
        System.out.println("0. Exit");  // Option to exit the program
    }

    // Handle user selection from the menu
    public void handleUserSelection() {
        int choice;
        do {
            showMenu();  // Show the menu to the user
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();  // Read the user's choice
            scanner.nextLine();  // Consume the newline character

            // Handle each menu option
            switch (choice) {
                case 1 -> addStudent();  // Call addStudent() method for option 1
                case 2 -> updateStudent();  // Call updateStudent() method for option 2
                case 3 -> deleteStudent();  // Call deleteStudent() method for option 3
                case 4 -> getStudentById();  // Call getStudentById() method for option 4
                case 5 -> getAllStudents();  // Call getAllStudents() method for option 5
                case 0 -> System.out.println("Exiting...");  // Exit the loop for option 0
                default -> System.out.println("Invalid choice. Please try again.");  // Handle invalid input
            }
        } while (choice != 0);  // Repeat the loop until the user chooses to exit
    }

    // Method to add a new student
    public void addStudent() {
        // Get the student details from the user
        Scanner scanner = new Scanner(System.in);  // Create a new scanner object for reading input
        System.out.println("Enter Student Details:");

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Faculty ID: ");
        int facultyId = scanner.nextInt();

        // Retrieve the Faculty object by its ID
        Faculty faculty = facultyDao.getFacultyById(facultyId);

        if (faculty == null) {
            System.out.println("Faculty not found with ID: " + facultyId);  // If faculty is not found, exit the method
            return;
        }

        // Create a new Student object and set its attributes
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setFaculty(faculty);

        // Insert the student into the database using the StudentDao
        studentDao.insertStudent(student);
    }

    // Method to update an existing student
    private void updateStudent() {
        System.out.print("Enter Student ID to update: ");
        int studentId = scanner.nextInt();  // Read the student ID
        scanner.nextLine();  // Consume the newline character

        // Retrieve the existing student by ID
        Student existingStudent = studentDao.getStudentById(studentId);
        if (existingStudent == null) {
            System.out.println("Student not found with ID: " + studentId);  // If student is not found, exit the method
            return;
        }

        // Prompt the user to enter updated details
        System.out.println("Enter Updated Details (leave blank to keep current value):");
        System.out.print("First Name (current: " + existingStudent.getFirstName() + "): ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name (current: " + existingStudent.getLastName() + "): ");
        String lastName = scanner.nextLine();
        System.out.print("Faculty ID (current: " + (existingStudent.getFaculty() != null ? existingStudent.getFaculty().getFacultyId() : "N/A") + "): ");
        String facultyIdInput = scanner.nextLine();

        // Update the student's details if the user entered new values
        if (!firstName.isBlank()) {
            existingStudent.setFirstName(firstName);
        }
        if (!lastName.isBlank()) {
            existingStudent.setLastName(lastName);
        }
        if (!facultyIdInput.isBlank()) {
            int facultyId = Integer.parseInt(facultyIdInput);
            // Assuming a method to retrieve and set the faculty object by ID
            // existingStudent.setFaculty(facultyDao.getFacultyById(facultyId));
        }

        // Update the student record in the database
        studentDao.updateStudent(existingStudent);
    }

    // Method to delete a student by ID
    private void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        int studentId = scanner.nextInt();  // Read the student ID
        scanner.nextLine();  // Consume the newline character

        // Delete the student from the database
        studentDao.deleteStudent(studentId);
    }

    // Method to retrieve a student by ID
    private void getStudentById() {
        System.out.print("Enter Student ID to retrieve: ");
        int studentId = scanner.nextInt();  // Read the student ID
        scanner.nextLine();  // Consume the newline character

        // Retrieve the student from the database by ID
        Student student = studentDao.getStudentById(studentId);
        if (student != null) {
            // If the student exists, display their details
            System.out.println("Student Details:");
            System.out.println("ID: " + student.getStudentId());
            System.out.println("First Name: " + student.getFirstName());
            System.out.println("Last Name: " + student.getLastName());
            if (student.getFaculty() != null) {
                System.out.println("Faculty Name: " + student.getFaculty().getName());
            } else {
                System.out.println("No faculty assigned to this student.");
            }
        } else {
            System.out.println("Student not found with ID: " + studentId);  // If student not found, print a message
        }
    }

    // Method to retrieve and display all students
    private void getAllStudents() {
        List<Student> students = studentDao.getAllStudents();  // Get the list of all students
        if (students.isEmpty()) {
            System.out.println("No students found.");  // If no students exist, print a message
        } else {
            System.out.println("All Students:");
            // Loop through the list of students and display their details
            for (Student student : students) {
                System.out.println("ID: " + student.getStudentId() + ", Name: " + student.getFirstName() + " " + student.getLastName());
            }
        }
    }
}
