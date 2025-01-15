package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "common")
public class Common {

    // Unique identifier for the user, used as the primary key
    @Id
    @Column(name = "email")
    private String email;

    // Password for user authentication
    @Column(name = "password", nullable = false)
    private String password;

    // Role of the user (e.g., student, teacher, admin)
    @Column(name = "role", nullable = false)
    private String role;

    // Name or username of the user (likely used for display or login purposes)
    @Column(name = "user", nullable = false)
    private String user;

    // ID to link the user to a teacher (if applicable)
    @Column(name = "teacher_id")
    private Integer teacherId;

    // ID to link the user to a student (if applicable)
    @Column(name = "student_id")
    private Integer studentId;

    // Grade assigned to the user, potentially for students (nullable)
    @Column(name = "grade")
    private Integer grade;

    // Constructors

    // Default constructor
    public Common() {}

    // Parameterized constructor to initialize the Common object
    public Common(String email, String password, String role, String user, Integer teacherId, Integer studentId, Integer grade) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.user = user;
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.grade = grade;
    }

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    // Override the toString method to provide a string representation of the Common object
    @Override
    public String toString() {
        return "Common{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", user='" + user + '\'' +
                ", teacherId=" + teacherId +
                ", studentId=" + studentId +
                ", grade='" + grade + '\'' +
                '}';
    }
}
