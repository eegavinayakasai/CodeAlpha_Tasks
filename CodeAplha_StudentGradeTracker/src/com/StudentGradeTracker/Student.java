package com.StudentGradeTracker;

public class Student {

    String name;
    int maths, physics, social;

    public Student(String name, int maths, int physics, int social) {
        this.name = name;
        this.maths = maths;
        this.physics = physics;
        this.social = social;
    }

    public int getTotal() {
        return maths + physics + social;
    }

    public boolean isOverallFailed() {
        return maths < 30 || physics < 30 || social < 30;
    }

    public String getGrade(int mark) {
        if (mark >= 90) return "A+";
        if (mark >= 70) return "A";
        if (mark >= 50) return "B";
        if (mark >= 30) return "C";
        return "F";
    }
}
