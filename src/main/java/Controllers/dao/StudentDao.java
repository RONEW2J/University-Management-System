package Controllers.dao;

import entities.Faculty;
import entities.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DB.DatabaseManager;

public class StudentDao {

    // Insert a student into the database
    public void insertStudent(Student student) {
        // SQL query for inserting a new student record
        String sql = "INSERT INTO students (first_name, last_name, faculty_id) VALUES (?, ?, ?)";
        
        try (Connection connection = DatabaseManager.getInstance().getConnection();  // Establish connection
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {  // Prepare statement with the SQL query

            // Set parameters for the student details
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getFaculty().getFacultyId());

            // Execute the update and check how many rows were affected
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student added successfully: " + student);
            } else {
                System.out.println("Failed to add the student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print any SQL exceptions for debugging
        }
    }

    // Update an existing student
    public void updateStudent(Student student) {
        // SQL query for updating an existing student record by student ID
        String sql = "UPDATE students SET first_name = ?, last_name = ?, faculty_id = ? WHERE student_id = ?";
        
        try (Connection connection = DatabaseManager.getInstance().getConnection();  // Establish connection
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {  // Prepare statement with the SQL query

            // Set the parameters for the student details
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getFaculty().getFacultyId());
            preparedStatement.setInt(4, student.getStudentId());  // Set the student ID to update the correct record

            // Execute the update and check how many rows were affected
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student updated successfully: " + student);
            } else {
                System.out.println("Failed to update the student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print any SQL exceptions for debugging
        }
    }

    // Delete a student by ID
    public void deleteStudent(int studentId) {
        // SQL query to delete a student based on their ID
        String sql = "DELETE FROM students WHERE student_id = ?";
        
        try (Connection connection = DatabaseManager.getInstance().getConnection();  // Establish connection
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {  // Prepare statement with the SQL query

            // Set the student ID to delete the correct record
            preparedStatement.setInt(1, studentId);

            // Execute the delete and check how many rows were affected
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student deleted successfully: ID = " + studentId);
            } else {
                System.out.println("Student not found with ID = " + studentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print any SQL exceptions for debugging
        }
    }

    // Get a student by ID
    public Student getStudentById(int studentId) {
        // SQL query to retrieve student details along with their faculty information
        String sql = """
            SELECT s.student_id, s.first_name, s.last_name, 
                   f.faculty_id, f.name AS faculty_name, f.contact, f.email
            FROM students s
            LEFT JOIN faculties f ON s.faculty_id = f.faculty_id
            WHERE s.student_id = ?
        """;
        
        try (Connection connection = DatabaseManager.getInstance().getConnection();  // Establish connection
             PreparedStatement statement = connection.prepareStatement(sql)) {  // Prepare statement with the SQL query

            // Set the student ID to fetch the correct record
            statement.setInt(1, studentId);
            
            try (ResultSet resultSet = statement.executeQuery()) {  // Execute the query and get the result
                if (resultSet.next()) {
                    // Create a new Student object and populate it with the result set
                    Student student = new Student();
                    student.setStudentId(resultSet.getInt("student_id"));
                    student.setFirstName(resultSet.getString("first_name"));
                    student.setLastName(resultSet.getString("last_name"));

                    // If a faculty is associated, create and set the Faculty object
                    int facultyId = resultSet.getInt("faculty_id");
                    if (!resultSet.wasNull()) {
                        Faculty faculty = new Faculty();
                        faculty.setFacultyId(facultyId);
                        faculty.setName(resultSet.getString("faculty_name"));
                        faculty.setContact(resultSet.getString("contact"));
                        faculty.setEmail(resultSet.getString("email"));
                        student.setFaculty(faculty);
                    }

                    return student;  // Return the student object
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print any SQL exceptions for debugging
        }
        
        return null;  // Return null if no student is found
    }

    // Get all students
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();  // Create a list to store all students
        String sql = "SELECT * FROM students";  // SQL query to retrieve all students
        try (Connection connection = DatabaseManager.getInstance().getConnection();  // Establish connection
             PreparedStatement preparedStatement = connection.prepareStatement(sql);  // Prepare statement with the SQL query
             ResultSet resultSet = preparedStatement.executeQuery()) {  // Execute the query and get the result

            // Iterate through all the result set and create student objects
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("student_id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                // Assume a method to fetch faculty by ID exists (this part can be modified as needed)
                // student.setFaculty(facultyDao.getFacultyById(resultSet.getInt("faculty_id")));
                students.add(student);  // Add student to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print any SQL exceptions for debugging
        }
        
        return students;  // Return the list of all students
    }
}
