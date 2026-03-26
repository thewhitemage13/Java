package org.example.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Group {
    private String name;
    private List<Student> students;

    public Group(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Group name cannot be null or empty");
        }
        this.name = name;
        this.students = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        students.add(student);
    }

    public int getStudentCount() {
        return students.size();
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public boolean removeStudentByName(String name) {
        return students.removeIf(s -> s.getName().equals(name));
    }

    public List<Student> getStudentsSortedByAverageExamGrade() {
        return students.stream()
                .sorted(Comparator.comparingDouble(Student::getAverageGrade))
                .collect(Collectors.toList());
    }
}
