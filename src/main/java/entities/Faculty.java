package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "faculties")
public class Faculty {

    // Unique identifier for the faculty, used as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faculty_id")
    private int facultyId;

    // Name of the faculty (e.g., "Engineering Faculty")
    @Column(name = "name", nullable = false)
    private String name;

    // Contact information for the faculty (could be phone number or address)
    @Column(name = "contact")
    private String contact;

    // Email address for the faculty
    @Column(name = "email")
    private String email;

    // Constructors

    // Parameterized constructor to initialize the Faculty object with values
    public Faculty(int facultyId, String name, String contact, String email) {
        this.facultyId = facultyId;
        this.name = name;
        this.contact = contact;
        this.email = email;
    }

    // Default constructor (required by JPA for entity classes)
    public Faculty() {
    }

    // Getters and Setters

    // Returns the unique identifier for the faculty
    public int getFacultyId() {
        return facultyId;
    }

    // Sets the unique identifier for the faculty
    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    // Returns the name of the faculty
    public String getName() {
        return name;
    }

    // Sets the name of the faculty
    public void setName(String name) {
        this.name = name;
    }

    // Returns the contact information for the faculty
    public String getContact() {
        return contact;
    }

    // Sets the contact information for the faculty
    public void setContact(String contact) {
        this.contact = contact;
    }

    // Returns the email address for the faculty
    public String getEmail() {
        return email;
    }

    // Sets the email address for the faculty
    public void setEmail(String email) {
        this.email = email;
    }

    // toString Method to provide a string representation of the Faculty object
    @Override
    public String toString() {
        return "Faculty{" +
                "facultyId=" + facultyId +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
