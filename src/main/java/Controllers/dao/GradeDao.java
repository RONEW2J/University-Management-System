package Controllers.dao;

import entities.Grade;
import entities.Student;
import entities.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DB.DatabaseManager;

public class GradeDao {

    private final Connection connection;

    // Constructor initializes the database connection using a singleton DatabaseManager
    public GradeDao() {
        this.connection = DatabaseManager.getInstance().getConnection();  // Ensure this returns a valid, open connection
    }

    // Insert a grade into the database
    public void insertGrade(Grade grade) {
        String sql = "INSERT INTO grades (semester_year, semester, GPA, student_id, course_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, grade.getSemesterYear());
            preparedStatement.setString(2, grade.getSemester());
            preparedStatement.setDouble(3, grade.getGPA());
            preparedStatement.setInt(4, grade.getStudent().getStudentId());
            preparedStatement.setInt(5, grade.getCourse().getCourseId());

            // Execute the SQL query and check if rows were affected
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Grade added successfully.");
            } else {
                System.out.println("Failed to add the grade.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle any SQL exceptions
        }
    }

    // Update an existing grade in the database
    public void updateGrade(Grade grade) {
        String sql = "UPDATE grades SET semester_year = ?, semester = ?, gpa = ? WHERE grade_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set updated values in the prepared statement
            statement.setString(1, grade.getSemesterYear());
            statement.setString(2, grade.getSemester());
            statement.setDouble(3, grade.getGPA());
            statement.setInt(4, grade.getGradeId());
            statement.executeUpdate(); // Execute the update
            System.out.println("Grade updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle any SQL exceptions
        }
    }

    // Delete a grade from the database using its ID
    public void deleteGrade(int gradeId) {
        String sql = "DELETE FROM grades WHERE grade_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, gradeId); // Set the grade ID to delete
            statement.executeUpdate(); // Execute the delete query
            System.out.println("Grade deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle any SQL exceptions
        }
    }

    // Fetch a grade from the database using its ID
    public Grade getGradeById(int gradeId) {
        String sql = "SELECT * FROM grades WHERE grade_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, gradeId); // Set the grade ID to fetch
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToGrade(resultSet); // Map the result set to a Grade object
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle any SQL exceptions
        }
        return null; // Return null if no grade is found
    }

    // Fetch all grades from the database
    public List<Grade> getAllGrades() {
        List<Grade> grades = new ArrayList<>(); // List to store all grades
        String sql = """
            SELECT g.grade_id, g.semester_year, g.semester, g.GPA,
                   s.student_id, s.first_name AS student_first_name, s.last_name AS student_last_name,
                   c.course_id, c.course_name
            FROM grades g
            LEFT JOIN students s ON g.student_id = s.student_id
            LEFT JOIN courses c ON g.course_id = c.course_id
        """;

        try (Connection connection = DatabaseManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Grade grade = new Grade(); // Create a new Grade object
                grade.setGradeId(resultSet.getInt("grade_id"));
                grade.setSemesterYear(resultSet.getString("semester_year"));
                grade.setSemester(resultSet.getString("semester"));
                grade.setGPA(resultSet.getDouble("GPA"));

                // Set student details if available
                int studentId = resultSet.getInt("student_id");
                if (!resultSet.wasNull()) {
                    Student student = new Student();
                    student.setStudentId(studentId);
                    student.setFirstName(resultSet.getString("student_first_name"));
                    student.setLastName(resultSet.getString("student_last_name"));
                    grade.setStudent(student);
                }

                // Set course details if available
                int courseId = resultSet.getInt("course_id");
                if (!resultSet.wasNull()) {
                    Course course = new Course();
                    course.setCourseId(courseId);
                    course.setCourseName(resultSet.getString("course_name"));
                    grade.setCourse(course);
                }

                grades.add(grade); // Add the grade to the list
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle any SQL exceptions
            System.out.println("Error fetching all grades.");
        }
        return grades; // Return the list of grades
    }

    // Map a row from the ResultSet to a Grade object
    private Grade mapRowToGrade(ResultSet resultSet) throws SQLException {
        int gradeId = resultSet.getInt("grade_id");
        String semesterYear = resultSet.getString("semester_year");
        String semester = resultSet.getString("semester");
        double gpa = resultSet.getDouble("gpa");

        // Create a Student object with the fetched details
        int studentId = resultSet.getInt("student_id");
        Student student = new Student(); // You need to implement this logic
        student.setStudentId(studentId);

        // Create a Course object with the fetched details
        int courseId = resultSet.getInt("course_id");
        Course course = new Course(); // Implement course fetching logic here
        course.setCourseId(courseId);

        // Return a new Grade object
        return new Grade(semesterYear, semester, gpa, student, course);
    }
}
