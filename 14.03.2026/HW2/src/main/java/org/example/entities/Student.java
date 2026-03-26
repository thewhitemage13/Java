package org.example.entities;

import java.util.List;

public class Student {
    private String name;
    private List<Integer> examGrades;

    public Student(String name, List<Integer> examGrades) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
        this.examGrades = examGrades;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getExamGrades() {
        return examGrades;
    }

    public double getAverageGrade() {
        return examGrades.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);
    }
}