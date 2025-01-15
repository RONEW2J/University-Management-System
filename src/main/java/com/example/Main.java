package com.example;

import Controllers.StudentController;
import Controllers.TeacherController;
import DB.DatabaseManager;
import Controllers.FacultyController;
import Controllers.CommonController;
import Controllers.CourseController;
import Controllers.GradeController;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    // Entry point of the program
    public static void main(String[] args) throws SQLException {
        // Scanner to capture user input from the console
        Scanner scanner = new Scanner(System.in);

        // Create a singleton instance of DatabaseManager to manage database connections
        DatabaseManager dbManager = DatabaseManager.getInstance();

        // Get the database connection
        Connection connection = dbManager.getConnection();

        // Infinite loop to keep the application running until the user chooses to exit
        while (true) {
            // Display the welcome message and controller options
            System.out.println("Welcome to the University Management System!");
            System.out.println("Please choose a controller to run:");
            System.out.println("1. Course Controller");
            System.out.println("2. Faculty Controller");
            System.out.println("3. Grade Controller");
            System.out.println("4. Common Controller");
            System.out.println("5. Student Controller");
            System.out.println("6. Teacher Controller");
            System.out.println("0. Exit");
            System.out.print("Enter the number of the controller you want to run: ");

            // Capture the user's choice
            int choice = scanner.nextInt();

            // Use a switch-case statement to call the appropriate controller based on user input
            switch (choice) {
                case 1 -> new CourseController().handleUserSelection();  // Handle Course controller
                case 2 -> new FacultyController().handleUserSelection(); // Handle Faculty controller
                case 3 -> new GradeController().handleUserSelection();   // Handle Grade controller
                case 4 -> new CommonController().handleUserSelection();  // Handle Common controller
                case 5 -> new StudentController().handleUserSelection(); // Handle Student controller
                case 6 -> new TeacherController().handleUserSelection(); // Handle Teacher controller
                case 0 -> { 
                    // Exit the system and close the database connection
                    System.out.println("Exiting University Management System. Goodbye!");
                    dbManager.closeConnection();  // Close the connection when exiting
                    return; // Exit the program
                }
                default -> System.out.println("Invalid choice. Please try again."); // Handle invalid input
            }
        }
    }
}
