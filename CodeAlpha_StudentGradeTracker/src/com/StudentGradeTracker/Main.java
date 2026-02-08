package com.StudentGradeTracker;

import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static void pause() {
        System.out.println("\nPress ENTER to continue... : ");
        sc.nextLine();
    }

    public static void main(String[] args) {

        GradeTracker tracker = new GradeTracker();

        while (true) {
            System.out.println("\n===== STUDENT GRADE TRACKER =====");
            System.out.println("1. Add Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Update Student");
            System.out.println("4. Show Average (Subject-wise)");
            System.out.println("5. Show Highest (Subject-wise)");
            System.out.println("6. Show Lowest (Subject-wise)");
            System.out.println("7. Show Failed Students");
            System.out.println("8. Show Subject Toppers");
            System.out.println("9. Show School Topper");
            System.out.println("10. Show Summary Report");
            System.out.println("11. Exit");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1 -> {
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Maths: ");
                    int m = Integer.parseInt(sc.nextLine());
                    System.out.print("Physics: ");
                    int p = Integer.parseInt(sc.nextLine());
                    System.out.print("Social: ");
                    int s = Integer.parseInt(sc.nextLine());
                    System.out.println(
                            tracker.addStudent(name, m, p, s)
                                    ? "Student added successfully!"
                                    : "Failed to add student."
                    );
                    pause();
                }

                case 2 -> {
                    tracker.showStudentNames();
                    System.out.print("Enter name to delete: ");
                    System.out.println(
                            tracker.deleteStudent(sc.nextLine())
                                    ? "Student deleted!"
                                    : "Student not found."
                    );
                    pause();
                }

                case 3 -> {
                    tracker.showStudentNames();
                    System.out.print("Enter name to update: ");
                    String name = sc.nextLine();
                    System.out.print("Maths: ");
                    int m = Integer.parseInt(sc.nextLine());
                    System.out.print("Physics: ");
                    int p = Integer.parseInt(sc.nextLine());
                    System.out.print("Social: ");
                    int s = Integer.parseInt(sc.nextLine());
                    System.out.println(
                            tracker.updateStudent(name, m, p, s)
                                    ? "Student updated!"
                                    : "Update failed."
                    );
                    pause();
                }

                case 4 -> { tracker.showAverage(); pause(); }
                case 5 -> { tracker.showHighest(); pause(); }
                case 6 -> { tracker.showLowest(); pause(); }
                case 7 -> { tracker.showFailedStudents(); pause(); }
                case 8 -> { tracker.showSubjectToppers(); pause(); }
                case 9 -> { tracker.showSchoolTopper(); pause(); }
                case 10 -> { tracker.showSummaryReport(); pause(); }

                case 11 -> {
                    System.out.println("Exiting program...");
                    return;
                }
            }
        }
    }
}
