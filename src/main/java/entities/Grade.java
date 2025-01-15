package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "grades")
public class Grade {

    // Unique identifier for the grade entry, used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private int gradeId;

    // The academic year of the semester (e.g., "2023-2024")
    @Column(name = "semester_year", nullable = false)
    private String semesterYear;

    // The specific semester (e.g., "Fall" or "Spring")
    @Column(name = "semester", nullable = false)
    private String semester;

    // The GPA obtained by the student in the course for this semester
    @Column(name = "gpa", nullable = false)
    private Double GPA;

    // Many-to-One relationship with Student (each grade belongs to one student)
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Many-to-One relationship with Course (each grade is associated with one course)
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // Constructors

    // Parameterized constructor to initialize the Grade object with values
    public Grade(String semesterYear, String semester, Double GPA, Student student, Course course) {
        this.semesterYear = semesterYear;
        this.semester = semester;
        this.GPA = GPA;
        this.student = student;
        this.course = course;
    }

    // Default constructor (required by JPA for entity classes)
    public Grade() {}

    // Getters and Setters

    // Returns the unique identifier for the grade
    public int getGradeId() {
        return gradeId;
    }

    // Sets the unique identifier for the grade
    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    // Returns the academic year of the semester
    public String getSemesterYear() {
        return semesterYear;
    }

    // Sets the academic year of the semester
    public void setSemesterYear(String semesterYear) {
        this.semesterYear = semesterYear;
    }

    // Returns the semester (e.g., "Fall", "Spring")
    public String getSemester() {
        return semester;
    }

    // Sets the semester for the grade
    public void setSemester(String semester) {
        this.semester = semester;
    }

    // Returns the GPA for the grade
    public Double getGPA() {
        return GPA;
    }

    // Sets the GPA for the grade
    public void setGPA(Double GPA) {
        this.GPA = GPA;
    }

    // Returns the student associated with the grade
    public Student getStudent() {
        return student;
    }

    // Sets the student for the grade
    public void setStudent(Student student) {
        this.student = student;
    }

    // Returns the course associated with the grade
    public Course getCourse() {
        return course;
    }

    // Sets the course for the grade
    public void setCourse(Course course) {
        this.course = course;
    }

    // toString Method to provide a string representation of the Grade object
    @Override
    public String toString() {
        return "Grade{" +
                "gradeId=" + gradeId +
                ", semesterYear='" + semesterYear + '\'' +
                ", semester='" + semester + '\'' +
                ", GPA=" + GPA +
                ", student=" + student +
                ", course=" + course +
                '}';
    }
}
