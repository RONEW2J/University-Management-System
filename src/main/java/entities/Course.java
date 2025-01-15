package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

    // Unique identifier for the course, used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "courses_course_id_seq")
    @SequenceGenerator(name = "courses_course_id_seq", sequenceName = "courses_course_id_seq", allocationSize = 1)
    @Column(name = "course_id")
    private int courseId;

    // Name of the course (e.g., "Mathematics 101")
    @Column(name = "course_name", nullable = false, length = 255)
    private String courseName;

    // Number of credits assigned to the course (e.g., 3 credits)
    @Column(name = "course_credits", nullable = false)
    private int courseCredits;

    // Duration of the course (e.g., "1 semester", "6 months")
    @Column(name = "course_duration", nullable = false, length = 100)
    private String courseDuration;

    // Constructor with parameters to initialize all attributes
    public Course(int courseId, String courseName, int courseCredits, String courseDuration) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCredits = courseCredits;
        this.courseDuration = courseDuration;
    }

    // Default constructor (required by JPA for entity classes)
    public Course() {}

    // Getters and Setters

    // Returns the unique identifier for the course
    public int getCourseId() {
        return courseId;
    }

    // Sets the unique identifier for the course
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    // Returns the name of the course
    public String getCourseName() {
        return courseName;
    }

    // Sets the name of the course
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    // Returns the number of credits assigned to the course
    public int getCourseCredits() {
        return courseCredits;
    }

    // Sets the number of credits for the course
    public void setCourseCredits(int courseCredits) {
        this.courseCredits = courseCredits;
    }

    // Returns the duration of the course (e.g., "1 semester")
    public String getCourseDuration() {
        return courseDuration;
    }

    // Sets the duration of the course
    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    // toString Method to provide a string representation of the Course object
    @Override
    public String toString() {
        return "Course(courseId=" + courseId + ", courseName=" + courseName +
                ", courseCredits=" + courseCredits + ", courseDuration=" + courseDuration + ")";
    }
}
