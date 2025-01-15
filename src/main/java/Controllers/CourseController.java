package Controllers;

import Controllers.dao.CourseDao;
import entities.Course;

import java.util.List;
import java.util.Scanner;

public class CourseController implements ControllerStrategy{

    private final CourseDao courseDao; // DAO object to interact with the database
    private final Scanner scanner; // Scanner object for reading user input

    // Constructor initializes the CourseDao and Scanner for user input
    public CourseController() {
        this.courseDao = new CourseDao(); // DAO to handle course-related database operations
        this.scanner = new Scanner(System.in); // For reading input from the user
    }

    // Method to display the main menu of the course management system
    public void showMenu() {
        System.out.println("==== Course Management System ====");
        System.out.println("1. Add Course"); // Option to add a new course
        System.out.println("2. Update Course"); // Option to update an existing course
        System.out.println("3. Delete Course"); // Option to delete a course
        System.out.println("4. Get Course by ID"); // Option to retrieve a course by its ID
        System.out.println("5. Get All Courses"); // Option to list all courses
        System.out.println("0. Exit"); // Option to exit the application
    }

    // Method to handle user input and invoke appropriate methods based on the choice
    public void handleUserSelection() {
        int choice; // Variable to store the user's menu selection
        do {
            showMenu(); // Display the menu
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt(); // Read user's choice
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1 -> addCourse(); // Add a new course
                case 2 -> updateCourse(); // Update an existing course
                case 3 -> deleteCourse(); // Delete a course
                case 4 -> getCourseById(); // Retrieve a course by ID
                case 5 -> getAllCourses(); // Retrieve all courses
                case 0 -> System.out.println("Exiting..."); // Exit the application
                default -> System.out.println("Invalid choice. Please try again."); // Handle invalid input
            }
        } while (choice != 0); // Repeat until the user selects "Exit"
    }

    // Method to add a new course to the database
    private void addCourse() {
        System.out.println("Enter Course Details:");
        System.out.print("Course Name: ");
        String courseName = scanner.nextLine(); // Read course name
        System.out.print("Course Credits: ");
        int courseCredits = scanner.nextInt(); // Read course credits
        scanner.nextLine(); // Consume the newline character
        System.out.print("Course Duration: ");
        String courseDuration = scanner.nextLine(); // Read course duration

        // Create a new Course object and set its properties
        Course course = new Course();
        course.setCourseName(courseName);
        course.setCourseCredits(courseCredits);
        course.setCourseDuration(courseDuration);

        // Use the DAO to insert the course into the database
        courseDao.insertCourse(course);
    }

    // Method to update details of an existing course
    private void updateCourse() {
        System.out.print("Enter Course ID to update: ");
        int courseId = scanner.nextInt(); // Read the course ID to update
        scanner.nextLine(); // Consume the newline character

        // Retrieve the existing course from the database
        Course existingCourse = courseDao.getCourseById(courseId);
        if (existingCourse == null) {
            System.out.println("Course not found with ID: " + courseId); // Handle non-existent course
            return;
        }

        // Prompt user for updated details, allowing blank input to retain current values
        System.out.println("Enter Updated Details (leave blank to keep current value):");
        System.out.print("Course Name (current: " + existingCourse.getCourseName() + "): ");
        String courseName = scanner.nextLine();
        System.out.print("Course Credits (current: " + existingCourse.getCourseCredits() + "): ");
        String creditsInput = scanner.nextLine();
        System.out.print("Course Duration (current: " + existingCourse.getCourseDuration() + "): ");
        String courseDuration = scanner.nextLine();

        // Update the course object with new values if provided
        if (!courseName.isBlank()) {
            existingCourse.setCourseName(courseName);
        }
        if (!creditsInput.isBlank()) {
            int courseCredits = Integer.parseInt(creditsInput);
            existingCourse.setCourseCredits(courseCredits);
        }
        if (!courseDuration.isBlank()) {
            existingCourse.setCourseDuration(courseDuration);
        }

        // Use the DAO to update the course in the database
        courseDao.updateCourse(existingCourse);
    }

    // Method to delete a course by its ID
    private void deleteCourse() {
        System.out.print("Enter Course ID to delete: ");
        int courseId = scanner.nextInt(); // Read the course ID to delete
        scanner.nextLine(); // Consume the newline character

        // Use the DAO to delete the course from the database
        courseDao.deleteCourse(courseId);
    }

    // Method to retrieve and display details of a course by its ID
    private void getCourseById() {
        System.out.print("Enter Course ID to retrieve: ");
        int courseId = scanner.nextInt(); // Read the course ID to retrieve
        scanner.nextLine(); // Consume the newline character

        // Use the DAO to retrieve the course
        Course course = courseDao.getCourseById(courseId);
        if (course != null) {
            // Display the course details
            System.out.println("Course Details:");
            System.out.println("ID: " + course.getCourseId());
            System.out.println("Course Name: " + course.getCourseName());
            System.out.println("Course Credits: " + course.getCourseCredits());
            System.out.println("Course Duration: " + course.getCourseDuration());
        } else {
            System.out.println("Course not found with ID: " + courseId); // Handle non-existent course
        }
    }

    // Method to retrieve and display all courses in the database
    private void getAllCourses() {
        // Use the DAO to get a list of all courses
        List<Course> courses = courseDao.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found."); // Handle empty list
        } else {
            // Display the list of courses
            System.out.println("All Courses:");
            for (Course course : courses) {
                System.out.println("ID: " + course.getCourseId() + ", Name: " + course.getCourseName() + ", Credits: " + course.getCourseCredits());
            }
        }
    }
}
