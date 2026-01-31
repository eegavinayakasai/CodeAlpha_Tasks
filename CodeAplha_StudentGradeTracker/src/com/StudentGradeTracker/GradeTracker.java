package com.StudentGradeTracker;

import java.util.ArrayList;

public class GradeTracker {

    ArrayList<Student> students = new ArrayList<>();

    // DEFAULT STUDENTS
    public GradeTracker() {
        students.add(new Student("Aarav", 78, 85, 92));
        students.add(new Student("Aditi", 67, 74, 81));
        students.add(new Student("Rahul", 25, 68, 72)); // FAIL
        students.add(new Student("Sneha", 88, 90, 95));
        students.add(new Student("Karthik", 61, 20, 69)); // FAIL
        students.add(new Student("Pooja", 73, 79, 84));
        students.add(new Student("Rohan", 49, 58, 63));
        students.add(new Student("Ananya", 91, 93, 89));
        students.add(new Student("Vikram", 65, 71, 76));
        students.add(new Student("Neha", 80, 85, 88));
    }

    public void showStudentNames() {
        System.out.println("\n--- STUDENT LIST ---");
        for (Student s : students) {
            System.out.println("- " + s.name);
        }
    }

    private boolean valid(int m) {
        return m >= 0 && m <= 100;
    }

    // ADD
    public boolean addStudent(String name, int m, int p, int s) {
        if (name.isEmpty() || !valid(m) || !valid(p) || !valid(s)) return false;
        students.add(new Student(name, m, p, s));
        return true;
    }

    // DELETE
    public boolean deleteStudent(String name) {
        return students.removeIf(s -> s.name.equalsIgnoreCase(name));
    }

    // UPDATE
    public boolean updateStudent(String name, int m, int p, int s) {
        for (Student st : students) {
            if (st.name.equalsIgnoreCase(name)) {
                if (!valid(m) || !valid(p) || !valid(s)) return false;
                st.maths = m;
                st.physics = p;
                st.social = s;
                return true;
            }
        }
        return false;
    }

    // 🔹 SUBJECT-WISE AVERAGE
    public void showAverage() {
        int m = 0, p = 0, s = 0;
        for (Student st : students) {
            m += st.maths;
            p += st.physics;
            s += st.social;
        }
        int n = students.size();
        System.out.println("Average Maths   : " + (m / n));
        System.out.println("Average Physics : " + (p / n));
        System.out.println("Average Social  : " + (s / n));
    }

    // 🔹 SUBJECT-WISE HIGHEST
    public void showHighest() {

        Student highMaths = students.get(0);
        Student highPhysics = students.get(0);
        Student highSocial = students.get(0);

        for (Student s : students) {
            if (s.maths > highMaths.maths) highMaths = s;
            if (s.physics > highPhysics.physics) highPhysics = s;
            if (s.social > highSocial.social) highSocial = s;
        }

        System.out.println("\n--- HIGHEST MARKS (SUBJECT-WISE) ---");
        System.out.println("Maths   : " + highMaths.name + " (" + highMaths.maths + ")");
        System.out.println("Physics : " + highPhysics.name + " (" + highPhysics.physics + ")");
        System.out.println("Social  : " + highSocial.name + " (" + highSocial.social + ")");
    }

    public void showLowest() {

        Student lowMaths = students.get(0);
        Student lowPhysics = students.get(0);
        Student lowSocial = students.get(0);

        for (Student s : students) {
            if (s.maths < lowMaths.maths) lowMaths = s;
            if (s.physics < lowPhysics.physics) lowPhysics = s;
            if (s.social < lowSocial.social) lowSocial = s;
        }

        System.out.println("\n--- LOWEST MARKS (SUBJECT-WISE) ---");
        System.out.println("Maths   : " + lowMaths.name + " (" + lowMaths.maths + ")");
        System.out.println("Physics : " + lowPhysics.name + " (" + lowPhysics.physics + ")");
        System.out.println("Social  : " + lowSocial.name + " (" + lowSocial.social + ")");
    }


    // 🔹 FAILED STUDENTS
    public void showFailedStudents() {
        System.out.println("\n--- FAILED STUDENTS ---");
        boolean found = false;
        for (Student s : students) {
            if (s.isOverallFailed()) {
                found = true;
                System.out.print(s.name + " failed in: ");
                if (s.maths < 30) System.out.print("Maths ");
                if (s.physics < 30) System.out.print("Physics ");
                if (s.social < 30) System.out.print("Social ");
                System.out.println();
            }
        }
        if (!found) System.out.println("No failed students 🎉");
    }

    // 🔹 SUBJECT TOPPERS
    public void showSubjectToppers() {
        Student mTop = students.get(0);
        Student pTop = students.get(0);
        Student sTop = students.get(0);

        for (Student st : students) {
            if (st.maths > mTop.maths) mTop = st;
            if (st.physics > pTop.physics) pTop = st;
            if (st.social > sTop.social) sTop = st;
        }

        System.out.println("Maths Topper   : " + mTop.name);
        System.out.println("Physics Topper : " + pTop.name);
        System.out.println("Social Topper  : " + sTop.name);
    }

    // 🔹 SCHOOL TOPPER
    public void showSchoolTopper() {
        Student topper = students.get(0);
        for (Student st : students)
            if (st.getTotal() > topper.getTotal())
                topper = st;

        System.out.println("School Topper : " + topper.name);
        System.out.println("Total Marks  : " + topper.getTotal());
    }

    // 🔹 SUMMARY REPORT
    public void showSummaryReport() {
        System.out.println("\n--- SUMMARY REPORT ---");
        for (Student s : students) {
            System.out.println(
                    s.name +
                            " | Maths: " + s.maths +
                            " | Physics: " + s.physics +
                            " | Social: " + s.social +
                            " | Total: " + s.getTotal()
            );
        }
    }
}
