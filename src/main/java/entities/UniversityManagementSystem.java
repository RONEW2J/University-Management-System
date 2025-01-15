package entities;

import java.util.ArrayList;
import java.util.List;

public class UniversityManagementSystem {
    private List<Student> students = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<Faculty> faculties = new ArrayList<>();

    // Register a new student
    public void registerStudent(Student student) {
        students.add(student);
        System.out.println("Student registered: " + student.getFirstName() + " " + student.getLastName());
    }

    // Register a new teacher
    public void registerTeacher(Teacher teacher) {
        teachers.add(teacher);
        System.out.println("Teacher registered: " + teacher.getFirstName());
    }

    // Create a new course
    public void createCourse(Course course) {
        courses.add(course);
        System.out.println("Course created: " + course.getCourseName());
    }

    // Assign a course to a teacher
    public void assignCourseToTeacher(Course course, Teacher teacher) {
        teacher.getCourses().add(course);
        System.out.println("Assigned course: " + course.getCourseName() + " to teacher: " + teacher.getFirstName());
    }

    // Add a student to a course
    public void addStudentToCourse(Student student, Course course) {
        student.getEnrolledCourses().add(course);
        System.out.println("Added student: " + student.getFirstName() + " to course: " + course.getCourseName());
    }

    // Add a grade for a student in a course
    public void addGrade(Student student, Course course, Double GPA, String semesterYear, String semester) {
        Grade grade = new Grade(semesterYear, semester, GPA, student, course);
        student.getGrades().add(grade);  // Assuming the Student class has a grades list.
        System.out.println("Added grade: " + GPA + " for student: " + student.getFirstName() + " in course: " + course.getCourseName());
    }

    // Calculate average grade for a course
    public double getAverageGradeByCourse(Course course) {
        double totalGrades = 0;
        int gradeCount = 0;
    
        for (Student student : students) {
            for (Grade grade : student.getGrades()) {
                if (grade.getCourse().equals(course)) {
                    totalGrades += grade.getGPA(); 
                    gradeCount++;
                }
            }
        }
        return gradeCount > 0 ? totalGrades / gradeCount : 0.0;
    }
    

    // Get all students in a specific faculty
    public List<Student> getStudentsByFaculty(Faculty faculty) {
        List<Student> facultyStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.getFaculty().equals(faculty)) {
                facultyStudents.add(student);
            }
        }
        return facultyStudents;
    }
}
