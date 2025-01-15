package Controllers;

import Controllers.dao.FacultyDao;
import entities.Faculty;

import java.util.List;
import java.util.Scanner;

/**
 * Controller class to handle user interactions and manage faculty operations.
 * Provides a command-line interface (CLI) for CRUD operations on faculties.
 */
public class FacultyController implements ControllerStrategy{

    // DAO object to interact with the database for faculty operations
    private final FacultyDao facultyDao;
    
    // Scanner for reading user input
    private final Scanner scanner;

    /**
     * Constructor initializes the DAO and Scanner objects.
     */
    public FacultyController() {
        this.facultyDao = new FacultyDao();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the menu options for the Faculty Management System.
     */
    public void showMenu() {
        System.out.println("==== Faculty Management System ====");
        System.out.println("1. Add Faculty");
        System.out.println("2. Update Faculty");
        System.out.println("3. Delete Faculty");
        System.out.println("4. Get Faculty by ID");
        System.out.println("5. Get All Faculties");
        System.out.println("0. Exit");
    }

    /**
     * Handles user input and executes the corresponding actions based on their choice.
     * Continues prompting until the user selects the exit option.
     */
    public void handleUserSelection() {
        int choice;
        do {
            showMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline to prevent input issues

            // Execute appropriate action based on user's choice
            switch (choice) {
                case 1 -> addFaculty();
                case 2 -> updateFaculty();
                case 3 -> deleteFaculty();
                case 4 -> getFacultyById();
                case 5 -> getAllFaculties();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    /**
     * Prompts the user to enter details for a new faculty and inserts it into the database.
     */
    private void addFaculty() {
        System.out.println("Enter Faculty Details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Contact: ");
        String contact = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        // Create a new Faculty object and populate its fields
        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setContact(contact);
        faculty.setEmail(email);

        // Insert the new faculty into the database
        facultyDao.insertFaculty(faculty);
    }

    /**
     * Prompts the user to update an existing faculty's details by ID.
     * If a field is left blank, the current value is retained.
     */
    private void updateFaculty() {
        System.out.print("Enter Faculty ID to update: ");
        int facultyId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        // Fetch the existing faculty record
        Faculty existingFaculty = facultyDao.getFacultyById(facultyId);
        if (existingFaculty == null) {
            System.out.println("Faculty not found with ID: " + facultyId);
            return;
        }

        // Prompt user for updated details
        System.out.println("Enter Updated Details (leave blank to keep current value):");
        System.out.print("Name (current: " + existingFaculty.getName() + "): ");
        String name = scanner.nextLine();
        System.out.print("Contact (current: " + existingFaculty.getContact() + "): ");
        String contact = scanner.nextLine();
        System.out.print("Email (current: " + existingFaculty.getEmail() + "): ");
        String email = scanner.nextLine();

        // Update only fields that have new values
        if (!name.isBlank()) {
            existingFaculty.setName(name);
        }
        if (!contact.isBlank()) {
            existingFaculty.setContact(contact);
        }
        if (!email.isBlank()) {
            existingFaculty.setEmail(email);
        }

        // Save the updated faculty details in the database
        facultyDao.updateFaculty(existingFaculty);
    }

    /**
     * Prompts the user for a faculty ID and deletes the corresponding record from the database.
     */
    private void deleteFaculty() {
        System.out.print("Enter Faculty ID to delete: ");
        int facultyId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        // Delete the faculty record
        facultyDao.deleteFaculty(facultyId);
    }

    /**
     * Prompts the user for a faculty ID and retrieves the corresponding record from the database.
     * Displays the faculty details if found, otherwise notifies the user.
     */
    private void getFacultyById() {
        System.out.print("Enter Faculty ID to retrieve: ");
        int facultyId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        // Fetch the faculty record by ID
        Faculty faculty = facultyDao.getFacultyById(facultyId);
        if (faculty != null) {
            System.out.println("Faculty Details:");
            System.out.println("ID: " + faculty.getFacultyId());
            System.out.println("Name: " + faculty.getName());
            System.out.println("Contact: " + faculty.getContact());
            System.out.println("Email: " + faculty.getEmail());
        } else {
            System.out.println("Faculty not found with ID: " + facultyId);
        }
    }

    /**
     * Retrieves all faculty records from the database and displays their details.
     * Notifies the user if no records are found.
     */
    private void getAllFaculties() {
        List<Faculty> faculties = facultyDao.getAllFaculties();
        if (faculties.isEmpty()) {
            System.out.println("No faculties found.");
        } else {
            System.out.println("All Faculties:");
            // Display details of each faculty
            for (Faculty faculty : faculties) {
                System.out.println("ID: " + faculty.getFacultyId() + ", Name: " + faculty.getName());
            }
        }
    }
}
