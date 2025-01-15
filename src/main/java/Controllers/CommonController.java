package Controllers;

import Controllers.dao.CommonDao;
import entities.Common;
import entities.User;

import java.util.List;
import java.util.Scanner;

public class CommonController implements ControllerStrategy{

    private final CommonDao commonDao; // Data Access Object for database interactions
    private final Scanner scanner;    // Scanner for reading user input

    // Constructor to initialize DAO and Scanner
    public CommonController() {
        this.commonDao = new CommonDao();
        this.scanner = new Scanner(System.in);
    }

    // Display the menu for user actions
    public void showMenu() {
        System.out.println("==== User Management System ====");
        System.out.println("1. Register User");
        System.out.println("2. Update User");
        System.out.println("3. Delete User");
        System.out.println("4. Get User by Email");
        System.out.println("5. Get All Users");
        System.out.println("0. Exit");
    }

    // Handle user input and execute corresponding actions
    public void handleUserSelection() {
        int choice;
        do {
            showMenu(); // Show menu options
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt(); // Read user choice
            scanner.nextLine(); // Consume newline character

            // Perform action based on user's choice
            switch (choice) {
                case 1 -> registerUser();
                case 2 -> updateUser();
                case 3 -> deleteUser();
                case 4 -> getUserByEmail();
                case 5 -> getAllUsers();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0); // Exit loop when choice is 0
    }

    // Register a new user
    private void registerUser() {
        System.out.println("Enter User Details:");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Role: ");
        String role = scanner.nextLine();

        System.out.print("User Name: ");
        String user = scanner.nextLine();

        // Read and handle optional fields (Teacher ID, Student ID, Grade)
        System.out.print("Teacher ID (or leave blank): ");
        String teacherIdInput = scanner.nextLine();
        Integer teacherId = teacherIdInput.isBlank() ? null : Integer.parseInt(teacherIdInput);

        System.out.print("Student ID (or leave blank): ");
        String studentIdInput = scanner.nextLine();
        Integer studentId = studentIdInput.isBlank() ? null : Integer.parseInt(studentIdInput);

        System.out.print("Grade (or leave blank): ");
        String gradeInput = scanner.nextLine();
        Integer grade = gradeInput.isBlank() ? null : Integer.parseInt(gradeInput);

        // Create a new user object and save it to the database
        User newUser = new User(email, password, role, user, teacherId, studentId, grade);
        commonDao.insertUser(newUser);
    }

    // Update an existing user
    private void updateUser() {
        System.out.print("Enter User Email to update: ");
        String email = scanner.nextLine();

        // Fetch the existing user details
        User existingUser = (User) commonDao.getUserByEmail(email); 
        if (existingUser == null) {
            System.out.println("User not found with email: " + email);
            return;
        }

        // Prompt for new details and update only provided fields
        System.out.println("Enter Updated Details (leave blank to keep current value):");

        System.out.print("User Name (current: " + existingUser.getUserName() + "): ");
        String userName = scanner.nextLine();
        if (!userName.isBlank()) {
            existingUser.setUserName(userName);
        }

        System.out.print("Password (current: " + existingUser.getPassword() + "): ");
        String password = scanner.nextLine();
        if (!password.isBlank()) {
            existingUser.setPassword(password);
        }

        System.out.print("Role (current: " + existingUser.getRole() + "): ");
        String role = scanner.nextLine();
        if (!role.isBlank()) {
            existingUser.setRole(role);
        }

        System.out.print("Teacher ID (current: " + existingUser.getTeacherId() + "): ");
        String teacherIdInput = scanner.nextLine();
        if (!teacherIdInput.isBlank()) {
            try {
                int teacherId = Integer.parseInt(teacherIdInput);
                existingUser.setTeacherId(teacherId);
            } catch (NumberFormatException e) {
                System.out.println("Invalid Teacher ID. Keeping current value.");
            }
        }

        System.out.print("Student ID (current: " + existingUser.getStudentId() + "): ");
        String studentIdInput = scanner.nextLine();
        if (!studentIdInput.isBlank()) {
            try {
                int studentId = Integer.parseInt(studentIdInput);
                existingUser.setStudentId(studentId);
            } catch (NumberFormatException e) {
                System.out.println("Invalid Student ID. Keeping current value.");
            }
        }

        System.out.print("Grade (current: " + existingUser.getGrade() + "): ");
        String gradeInput = scanner.nextLine();
        if (!gradeInput.isBlank()) {
            try {
                int grade = Integer.parseInt(gradeInput);
                existingUser.setGrade(grade);
            } catch (NumberFormatException e) {
                System.out.println("Invalid Grade. Keeping current value.");
            }
        }

        // Update the user in the database
        commonDao.updateUser(existingUser);
        System.out.println("User updated successfully.");
    }

    // Delete a user by email
    private void deleteUser() {
        System.out.print("Enter Email of the user to delete: ");
        String email = scanner.nextLine();

        commonDao.deleteUser(email);
    }

    // Fetch a user by email
    private void getUserByEmail() {
        System.out.print("Enter Email to retrieve user: ");
        String email = scanner.nextLine();

        Common user = commonDao.getUserByEmail(email);
        if (user != null) {
            System.out.println("User Details:");
            System.out.println(user);
        } else {
            System.out.println("User not found with email: " + email);
        }
    }

    // Fetch all users
    private void getAllUsers() {
        List<User> users = commonDao.getAllUsers(); 
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("All Users:");
            for (User user : users) {
                System.out.println(user);
            }
        }
    }   
}
