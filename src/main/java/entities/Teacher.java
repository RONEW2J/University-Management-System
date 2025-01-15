package entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teachers")
public class Teacher {

    // Unique identifier for the teacher, used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private int teacherId;

    // Teacher's first name
    @Column(name = "first_name", nullable = false)
    private String firstName;

    // Teacher's last name
    @Column(name = "last_name", nullable = false)
    private String lastName;

    // Teacher's email address, which must be unique
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // A many-to-one relationship indicating the faculty to which the teacher belongs
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    // A one-to-many relationship indicating the list of courses assigned to this teacher
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses;

    // Constructors

    // Parameterized constructor to initialize a Teacher object
    public Teacher(String firstName, String lastName, String email, int facultyId, Faculty faculty, List<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.faculty = faculty;
        this.courses = courses != null ? courses : new ArrayList<>();
    }

    // Default constructor (no-arg constructor)
    public Teacher() {
    }

    // Getters and Setters

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    // toString Method to provide a string representation of the Teacher object
    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", faculty=" + (faculty != null ? faculty.getName() : "null") +
                ", courses=" + (courses != null ? courses.toString() : "null") +
                '}';
    }
}
