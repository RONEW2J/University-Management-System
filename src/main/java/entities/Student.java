package entities;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    // Unique identifier for the student, used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private int studentId;

    // The first name of the student
    @Column(name = "first_name", nullable = false)
    private String firstName;

    // The last name of the student
    @Column(name = "last_name", nullable = false)
    private String lastName;

    // Many-to-One relationship with Faculty (a student belongs to one faculty)
    @ManyToOne(fetch = FetchType.EAGER)  // FetchType.EAGER means the faculty is fetched with the student
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    // Many-to-Many relationship with Course (a student can enroll in multiple courses)
    @ManyToMany(fetch = FetchType.LAZY)  // Explicitly specifying lazy loading
    @JoinTable(
            name = "student_courses",  // The join table to link students and courses
            joinColumns = @JoinColumn(name = "student_id"),  // Foreign key in the join table pointing to the student
            inverseJoinColumns = @JoinColumn(name = "course_id")  // Foreign key in the join table pointing to the course
    )
    private List<Course> enrolledCourses;

    // One-to-Many relationship with Grade (a student can have multiple grades for different courses)
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)  // Lazy loading for grades
    private List<Grade> grades;

    // Constructors

    // Constructor with parameters to initialize all attributes
    public Student(int studentId, String firstName, String lastName, Faculty faculty, List<Course> enrolledCourses, List<Grade> grades) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.faculty = faculty;
        this.enrolledCourses = enrolledCourses;
        this.grades = grades;
    }

    // Default constructor (required by JPA for entity classes)
    public Student() {
    }

    // Getters and Setters

    // Returns the unique identifier for the student
    public int getStudentId() {
        return studentId;
    }

    // Sets the unique identifier for the student
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    // Returns the first name of the student
    public String getFirstName() {
        return firstName;
    }

    // Sets the first name of the student
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Returns the last name of the student
    public String getLastName() {
        return lastName;
    }

    // Sets the last name of the student
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Returns the faculty that the student belongs to
    public Faculty getFaculty() {
        return faculty;
    }

    // Sets the faculty for the student
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    // Returns the list of courses the student is enrolled in
    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    // Sets the list of courses the student is enrolled in
    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    // Returns the list of grades the student has received
    public List<Grade> getGrades() {
        return grades;
    }

    // Sets the list of grades for the student
    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    // toString Method to provide a string representation of the Student object
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", faculty=" + faculty +
                ", enrolledCourses=" + enrolledCourses +
                ", grades=" + grades +
                '}';
    }
}
