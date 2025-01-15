package Controllers.dao;

import entities.Common;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DB.DatabaseManager;

/**
 * Data Access Object (DAO) for performing CRUD operations on User and Common entities.
 * Handles database interactions such as inserting, updating, deleting, and retrieving user records.
 */
public class CommonDao {

    // Connection object for interacting with the database
    private final Connection connection;

    /**
     * Constructor initializes the connection using the DatabaseManager.
     * Ensures that the database connection is available for use in methods.
     */
    public CommonDao() {
        this.connection = DatabaseManager.getInstance().getConnection();  // Ensure DatabaseManager is set up for DB connection
    }

    /**
     * Inserts a new user record into the 'users' table in the database.
     *
     * @param user The User object containing the details to be inserted.
     */
    public void insertUser(User user) {
        String sql = "INSERT INTO users (email, password, role, user_name, teacher_id, student_id, grade) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set parameters for the SQL statement
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.setString(4, user.getUserName());
            statement.setObject(5, user.getTeacherId()); // Use setObject for nullable fields
            statement.setObject(6, user.getStudentId());
            statement.setObject(7, user.getGrade());

            // Execute the statement
            statement.executeUpdate();
            System.out.println("User added successfully: " + user.getEmail());
        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
        }
    }

    /**
     * Updates an existing user record in the 'users' table.
     *
     * @param user The User object containing the updated details.
     */
    public void updateUser(User user) {
        String sql = "UPDATE users SET password = ?, role = ?, user_name = ?, teacher_id = ?, student_id = ?, grade = ? WHERE email = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set parameters for the SQL statement
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getRole());
            statement.setString(3, user.getUserName());
            statement.setObject(4, user.getTeacherId());
            statement.setObject(5, user.getStudentId());
            statement.setObject(6, user.getGrade());
            statement.setString(7, user.getEmail());

            // Execute the statement
            statement.executeUpdate();
            System.out.println("User updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
        }
    }

    /**
     * Deletes a user record from the 'users' table by email.
     *
     * @param email The email of the user to be deleted.
     */
    public void deleteUser(String email) {
        String sql = "DELETE FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set the email parameter for the SQL statement
            statement.setString(1, email);

            // Execute the statement
            statement.executeUpdate();
            System.out.println("User deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
        }
    }

    /**
     * Retrieves a user record from the 'users' table by email.
     *
     * @param email The email of the user to be retrieved.
     * @return A User object containing the details of the retrieved user or null if not found.
     */
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = getConnection();  // Ensure connection is open for query execution
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set the email parameter for the SQL statement
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            // Check if a result is available
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setEmail(resultSet.getString("email"));
                user.setUserName(resultSet.getString("user_name"));
                // Set other fields as needed
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
        }
        return null;  // Return null if no user found
    }

    /**
     * Retrieves all user records from the 'users' table.
     *
     * @return A list of User objects representing all users in the database.
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT email, password, role, user_name, teacher_id, student_id, grade FROM users";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            // Iterate over the result set and create User objects
            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                String userName = resultSet.getString("user_name");
                Integer teacherId = resultSet.getInt("teacher_id");
                Integer studentId = resultSet.getInt("student_id");
                Integer grade = resultSet.getInt("grade");

                // Create a User object and add it to the list
                User user = new User(email, password, role, userName, teacherId, studentId, grade);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
        }

        return users;
    }

    // Database connection parameters
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/db_name";
    private static final String DB_USER = "db";
    private static final String DB_PASSWORD = "db_password";

    /**
     * Establishes a connection to the database.
     *
     * @return A Connection object for the database.
     * @throws SQLException if there is an error establishing the connection.
     */
    public Connection getConnection() throws SQLException {
        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("JDBC Driver not found.");
        }
    }

    /**
     * Maps a result set row to a Common object.
     *
     * @param resultSet The ResultSet object containing the row data.
     * @return A Common object with fields populated from the result set.
     * @throws SQLException if there is an error accessing the result set data.
     */
    private Common mapRowToUser(ResultSet resultSet) throws SQLException {
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String role = resultSet.getString("role");
        String user = resultSet.getString("user");
        Integer teacherId = (Integer) resultSet.getObject("teacher_id");
        Integer studentId = (Integer) resultSet.getObject("student_id");
        Integer grade = resultSet.getInt("grade");

        // Return a Common object created with the retrieved data
        return new Common(email, password, role, user, teacherId, studentId, grade);
    }
}
