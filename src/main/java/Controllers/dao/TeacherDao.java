package Controllers.dao;

import entities.Teacher;
import entities.Faculty;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DB.DatabaseManager;

public class TeacherDao {

    private FacultyDao facultyDao = new FacultyDao();  // DAO for Faculty operations, used to fetch faculty data
    
    // Insert a teacher into the database
    public void insertTeacher(Teacher teacher) {
        String sql = "INSERT INTO teachers (first_name, last_name, email, faculty_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseManager.getInstance().getConnection();  // Get database connection
             PreparedStatement statement = connection.prepareStatement(sql)) {
             
            // Set parameters for the SQL query
            statement.setString(1, teacher.getFirstName());
            statement.setString(2, teacher.getLastName());
            statement.setString(3, teacher.getEmail());
            statement.setInt(4, teacher.getFaculty().getFacultyId());  // Store only the faculty ID
            
            // Execute the update and check if a row is affected
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Teacher added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print the stack trace if an exception occurs
        }
    }
    
    // Update an existing teacher
    public void updateTeacher(Teacher teacher) {
        String sql = "UPDATE teachers SET first_name = ?, last_name = ?, email = ?, faculty_id = ? WHERE teacher_id = ?";
        try (Connection connection = DatabaseManager.getInstance().getConnection();  // Get database connection
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
             
            // Set parameters for the SQL query
            preparedStatement.setString(1, teacher.getFirstName());
            preparedStatement.setString(2, teacher.getLastName());
            preparedStatement.setString(3, teacher.getEmail());
            preparedStatement.setInt(4, teacher.getFaculty().getFacultyId());
            preparedStatement.setInt(5, teacher.getTeacherId());  // Use the teacher ID for the WHERE clause

            // Execute the update and check if a row is affected
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Teacher updated successfully: " + teacher);
            } else {
                System.out.println("Failed to update the teacher.");
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print the stack trace if an exception occurs
        }
    }

    // Delete a teacher by ID
    public void deleteTeacher(int teacherId) {
        String sql = "DELETE FROM teachers WHERE teacher_id = ?";
        try (Connection connection = DatabaseManager.getInstance().getConnection();  // Get database connection
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, teacherId);  // Set the teacher ID parameter

            // Execute the delete and check if a row is affected
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Teacher deleted successfully: ID = " + teacherId);
            } else {
                System.out.println("Teacher not found with ID = " + teacherId);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print the stack trace if an exception occurs
        }
    }

    // Get a teacher by ID
    public Teacher getTeacherById(int teacherId) {
        String sql = """
            SELECT t.teacher_id, t.first_name, t.last_name, t.email,
                   f.faculty_id, f.name AS faculty_name, f.contact, f.email AS faculty_email
            FROM teachers t
            LEFT JOIN faculties f ON t.faculty_id = f.faculty_id
            WHERE t.teacher_id = ?
        """;
        try (Connection connection = DatabaseManager.getInstance().getConnection();  // Get database connection
             PreparedStatement statement = connection.prepareStatement(sql)) {
             
            statement.setInt(1, teacherId);  // Set the teacher ID parameter
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Teacher teacher = new Teacher();  // Create a Teacher object

                    // Set teacher attributes from the result set
                    teacher.setTeacherId(resultSet.getInt("teacher_id"));
                    teacher.setFirstName(resultSet.getString("first_name"));
                    teacher.setLastName(resultSet.getString("last_name"));
                    teacher.setEmail(resultSet.getString("email"));

                    // Check if the teacher has an associated faculty and set it
                    int facultyId = resultSet.getInt("faculty_id");
                    if (!resultSet.wasNull()) {
                        Faculty faculty = new Faculty();
                        faculty.setFacultyId(facultyId);
                        faculty.setName(resultSet.getString("faculty_name"));
                        faculty.setContact(resultSet.getString("contact"));
                        faculty.setEmail(resultSet.getString("faculty_email"));
                        teacher.setFaculty(faculty);  // Set the Faculty object to the teacher
                    }

                    return teacher;  // Return the teacher object
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print the stack trace if an exception occurs
        }
        return null;  // Return null if no teacher was found
    }

    // Get all teachers
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();  // Create a list to store teachers
        String sql = "SELECT teacher_id, first_name, last_name, email, faculty_id FROM teachers";
        try (Connection connection = DatabaseManager.getInstance().getConnection();  // Get database connection
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            // Iterate over the result set and create Teacher objects
            while (resultSet.next()) {
                int teacherId = resultSet.getInt("teacher_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                int facultyId = resultSet.getInt("faculty_id");

                // Fetch the Faculty associated with the teacher
                Faculty faculty = facultyDao.getFacultyById(facultyId);

                Teacher teacher = new Teacher();
                teacher.setTeacherId(teacherId);
                teacher.setFirstName(firstName);
                teacher.setLastName(lastName);
                teacher.setEmail(email);
                teacher.setFaculty(faculty);  // Set the faculty for this teacher

                teachers.add(teacher);  // Add the teacher to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print the stack trace if an exception occurs
        }
        return teachers;  // Return the list of teachers
    }
}
