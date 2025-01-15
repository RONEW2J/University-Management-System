package view;

import entities.Common;

public class CommonView {
    public void showCommonDetails(Common common) {
        if (common != null) {
            System.out.println("Common Details:");
            System.out.println("Email: " + common.getEmail());
            System.out.println("Password: " + common.getPassword());
            System.out.println("Role: " + common.getRole());
            System.out.println("User: " + common.getUser());

            if (common.getTeacherId() != null) {
                System.out.println("Teacher ID: " + common.getTeacherId());
            } else {
                System.out.println("Teacher ID: Not assigned");
            }

            if (common.getStudentId() != null) {
                System.out.println("Student ID: " + common.getStudentId());
            } else {
                System.out.println("Student ID: Not assigned");
            }

            if (common.getGrade() != null) {
                System.out.println("Grade: " + common.getGrade());
            } else {
                System.out.println("Grade: Not assigned");
            }
        } else {
            System.out.println("Common entity not found.");
        }
    }
}
