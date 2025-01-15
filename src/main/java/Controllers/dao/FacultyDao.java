package Controllers.dao;

import DB.DatabaseManager;
import entities.Faculty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for the Faculty entity.
 * Provides methods to perform CRUD (Create, Read, Update, Delete) operations
 * on the faculties table in the database.
 */
public class FacultyDao {

    // Connection to the database, obtained from the DatabaseManager Singleton
    private final Connection connection;

    /**
     * Constructor initializes the connection by utilizing the Singleton DatabaseManager.
     */
    public FacultyDao() {
        this.connection = DatabaseManager.getInstance().getConnection();
    }

    /**
     * Inserts a new faculty record into the database.
     * 
     * @param faculty The Faculty object containing details to be inserted.
     */
    public void insertFaculty(Faculty faculty) {
        String sql = "INSERT INTO faculties (name, contact, email) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, faculty.getName());
            statement.setString(2, faculty.getContact());
            statement.setString(3, faculty.getEmail());
            statement.executeUpdate();
            System.out.println("Faculty added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing faculty record in the database based on faculty_id.
     * 
     * @param faculty The Faculty object containing updated details.
     */
    public void updateFaculty(Faculty faculty) {
        String sql = "UPDATE faculties SET name = ?, contact = ?, email = ? WHERE faculty_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, faculty.getName());
            statement.setString(2, faculty.getContact());
            statement.setString(3, faculty.getEmail());
            statement.setInt(4, faculty.getFacultyId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Faculty updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a faculty record from the database based on faculty_id.
     * 
     * @param facultyId The ID of the faculty to be deleted.
     */
    public void deleteFaculty(int facultyId) {
        String sql = "DELETE FROM faculties WHERE faculty_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, facultyId);
            statement.executeUpdate();
            System.out.println("Faculty deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a faculty record by its ID.
     * 
     * @param facultyId The ID of the faculty to be retrieved.
     * @return The Faculty object representing the record, or null if not found.
     */
    public Faculty getFacultyById(int facultyId) {
        Faculty faculty = null;
        String sql = "SELECT * FROM faculties WHERE faculty_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, facultyId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                faculty = new Faculty();
                faculty.setFacultyId(resultSet.getInt("faculty_id"));
                faculty.setName(resultSet.getString("name"));
                faculty.setContact(resultSet.getString("contact"));
                faculty.setEmail(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faculty;
    }

    /**
     * Retrieves all faculty records from the database.
     * 
     * @return A list of Faculty objects representing all records in the table.
     */
    public List<Faculty> getAllFaculties() {
        List<Faculty> faculties = new ArrayList<>();
        String sql = "SELECT * FROM faculties";
        try (Connection connection = getConnection(); // Ensure the connection is open
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Faculty faculty = new Faculty();
                faculty.setFacultyId(resultSet.getInt("faculty_id"));
                faculty.setName(resultSet.getString("name"));
                faculty.setContact(resultSet.getString("contact"));
                faculty.setEmail(resultSet.getString("email"));
                faculties.add(faculty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faculties;
    }

    /**
     * Maps a result set row to a Faculty object.
     * 
     * @param resultSet The result set containing a row of data.
     * @return A Faculty object populated with data from the row.
     * @throws SQLException If an error occurs while accessing the result set.
     */
    private Faculty mapRowToFaculty(ResultSet resultSet) throws SQLException {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(resultSet.getInt("faculty_id"));
        faculty.setName(resultSet.getString("name"));
        faculty.setContact(resultSet.getString("contact"));
        faculty.setEmail(resultSet.getString("email"));
        return faculty;
    }

    // Database connection parameters (only needed for specific methods in this class)
    
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/db_name";
    private static final String DB_USER = "db";
    private static final String DB_PASSWORD = "db_password";

    /**
     * Creates a new database connection. This method is used locally to demonstrate
     * alternate connection usage. Ideally, the DatabaseManager Singleton should be used.
     * 
     * @return A new Connection object.
     * @throws SQLException If a connection error occurs.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
