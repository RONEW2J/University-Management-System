package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The DatabaseManager class implements the Singleton Pattern to manage database connections.
 * This ensures that only one instance of the DatabaseManager exists throughout the application's lifecycle,
 * reducing resource usage and avoiding potential connection conflicts.
 */
public class DatabaseManager {

    // Database connection parameters
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/db_name";
    private static final String USER = "db";
    private static final String PASSWORD = "db_password";

    // The single instance of DatabaseManager
    private static DatabaseManager instance;

    // The single database connection object
    private static Connection connection;

    /**
     * Private constructor ensures that the class cannot be instantiated from outside.
     * This enforces the Singleton Pattern and ensures that the database connection is managed centrally.
     */
    private DatabaseManager() {
        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
            // Establish a connection to the database
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Synchronized method to get the single instance of the DatabaseManager class.
     * This ensures thread safety when multiple threads access the instance.
     * 
     * @return The single instance of DatabaseManager.
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Retrieves the current database connection. If the connection is closed or null,
     * a new connection is established. This ensures that the application always has
     * a valid connection to the database.
     * 
     * @return The active database connection.
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Closes the database connection. This method should be called when the application
     * is shutting down or when no further database operations are required.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();  // Close only when you're sure no further operations are needed
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
