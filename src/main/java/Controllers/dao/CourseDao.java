package Controllers.dao;

import DB.DatabaseManager;
import entities.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {

    private final Connection connection; // Connection object for database interaction

    // Constructor initializes the connection using the DatabaseManager
    public CourseDao() {
        this.connection = DatabaseManager.getInstance().getConnection();
    }

    // Method to get a valid database connection, ensuring it is not null or closed
    private Connection getConnection() {
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Connection is not available.");
            }
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get a valid database connection.");
        }
    }

    // Method to insert a new course into the database
    public void insertCourse(Course course) {
        String sql = "INSERT INTO courses (course_name, course_credits, course_duration) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set parameters for the prepared statement
            statement.setString(1, course.getCourseName());
            statement.setInt(2, course.getCourseCredits());
            statement.setString(3, course.getCourseDuration());
            statement.executeUpdate(); // Execute the update
            System.out.println("Course added successfully.");
        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
        }
    }

    // Method to update an existing course in the database
    public void updateCourse(Course course) {
        String sql = "UPDATE courses SET course_name = ?, course_credits = ?, course_duration = ? WHERE course_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set parameters for the prepared statement
            statement.setString(1, course.getCourseName());
            statement.setInt(2, course.getCourseCredits());
            statement.setString(3, course.getCourseDuration());
            statement.setInt(4, course.getCourseId());
            statement.executeUpdate(); // Execute the update
            System.out.println("Course updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
        }
    }

    // Method to delete a course from the database using its ID
    public void deleteCourse(int courseId) {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, courseId); // Set the course ID parameter
            statement.executeUpdate(); // Execute the update
            System.out.println("Course deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
        }
    }

    // Method to retrieve a single course by its ID
    public Course getCourseById(int courseId) {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, courseId); // Set the course ID parameter
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToCourse(resultSet); // Map the result set to a Course object
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
        }
        return null; // Return null if no course is found
    }

    // Method to retrieve all courses from the database
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            // Iterate over the result set and map each row to a Course object
            while (resultSet.next()) {
                courses.add(mapRowToCourse(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
        }
        return courses; // Return the list of courses
    }

    // Helper method to map a result set row to a Course object
    private Course mapRowToCourse(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setCourseId(resultSet.getInt("course_id")); // Map course_id column
        course.setCourseName(resultSet.getString("course_name")); // Map course_name column
        course.setCourseCredits(resultSet.getInt("course_credits")); // Map course_credits column
        course.setCourseDuration(resultSet.getString("course_duration")); // Map course_duration column
        return course; // Return the mapped Course object
    }
}
