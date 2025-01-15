package view;

import java.util.List;
import java.util.Scanner;
import entities.Faculty;

public class FacultyView {
    public void showFacultyDetails(Faculty faculty) {
        if (faculty == null) {
            System.out.println("Faculty not found.");
            return;
        }
        System.out.println("Faculty Details:");
        System.out.println("ID: " + faculty.getFacultyId());
        System.out.println("Name: " + faculty.getName());
        System.out.println("Contact: " + faculty.getContact());
        System.out.println("Email: " + faculty.getEmail());
    }
}
