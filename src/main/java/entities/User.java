package entities;

import jakarta.persistence.*;

@Entity  // Specifies that this class is an entity and will be mapped to a database table
@Table(name = "users")  // Defines the table name in the database for this entity
public class User extends Common {

    @Id  // Marks the field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Indicates that the primary key will be auto-generated
    @Column(name = "user_id")  // Specifies the column name in the database
    private int userId;  // Unique identifier for the user

    @Column(name = "email", nullable = false, unique = true)  // Defines a column with constraints for unique email
    private String email;  // User's email address

    @Column(name = "password", nullable = false)  // Defines a column for password with a not-null constraint
    private String password;  // User's password

    @Column(name = "role", nullable = false)  // Defines a column for role (e.g., teacher, student)
    private String role;  // User's role (could be "admin", "teacher", "student", etc.)

    @Column(name = "user_name", nullable = false)  // Defines a column for the user's name
    private String userName;  // User's display name

    @Column(name = "teacher_id")  // Optional foreign key to the Teacher entity (nullable)
    private Integer teacherId;  // The ID of the teacher associated with the user (if applicable)

    @Column(name = "student_id")  // Optional foreign key to the Student entity (nullable)
    private Integer studentId;  // The ID of the student associated with the user (if applicable)

    @Column(name = "grade")  // Optional field to store the grade of a student
    private Integer grade;  // User's grade (if the user is a student)

    // Constructors

    // Parameterized constructor to initialize the User object with values
    public User(String email, String password, String role, String userName, Integer teacherId, Integer studentId, Integer grade) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.userName = userName;
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.grade = grade;
    }

    // Default constructor (no arguments), required by JPA
    public User() {
    }

    // Getters and Setters

    public int getUserId() {
        return userId;  // Returns the user ID
    }

    public void setUserId(int userId) {
        this.userId = userId;  // Sets the user ID
    }

    public String getEmail() {
        return email;  // Returns the email of the user
    }

    public void setEmail(String email) {
        this.email = email;  // Sets the email of the user
    }

    public String getPassword() {
        return password;  // Returns the password of the user
    }

    public void setPassword(String password) {
        this.password = password;  // Sets the password of the user
    }

    public String getRole() {
        return role;  // Returns the role of the user
    }

    public void setRole(String role) {
        this.role = role;  // Sets the role of the user
    }

    public String getUserName() {
        return userName;  // Returns the user name
    }

    public void setUserName(String userName) {
        this.userName = userName;  // Sets the user name
    }

    public Integer getTeacherId() {
        return teacherId;  // Returns the teacher ID if the user is a teacher
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;  // Sets the teacher ID
    }

    public Integer getStudentId() {
        return studentId;  // Returns the student ID if the user is a student
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;  // Sets the student ID
    }

    public Integer getGrade() {
        return grade;  // Returns the grade of the student
    }

    public void setGrade(Integer grade) {
        this.grade = grade;  // Sets the grade of the student
    }

    // toString Method

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +  // Prints user ID
                ", email='" + email + '\'' +  // Prints email address
                ", password='" + password + '\'' +  // Prints password
                ", role='" + role + '\'' +  // Prints role
                ", userName='" + userName + '\'' +  // Prints user name
                ", teacherId=" + teacherId +  // Prints teacher ID (if applicable)
                ", studentId=" + studentId +  // Prints student ID (if applicable)
                ", grade='" + grade + '\'' +  // Prints grade (if applicable)
                '}';  // Returns the string representation of the User object
    }
}
