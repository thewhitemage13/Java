import org.example.entities.Group;
import org.example.entities.Student;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupTest {
    @Test
    void testGroupCreation() {
        Group group = new Group("CS-101");
        assertEquals("CS-101", group.getName());
        assertEquals(0, group.getStudentCount());
    }

    @Test
    void testAddStudent() {
        Group group = new Group("CS-101");
        Student student = new Student("Ivan", List.of(80, 90, 100));

        group.addStudent(student);

        assertEquals(1, group.getStudentCount());
        assertEquals("Ivan", group.getStudents().get(0).getName());
    }

    @Test
    void testInvalidGroupName() {
        assertThrows(IllegalArgumentException.class, () -> new Group(""));
        assertThrows(IllegalArgumentException.class, () -> new Group(null));
    }

    @Test
    void testAddNullStudent() {
        Group group = new Group("CS-101");
        assertThrows(IllegalArgumentException.class, () -> group.addStudent(null));
    }

    @Test
    void testRemoveStudentByName() {
        Group group = new Group("CS-101");
        group.addStudent(new Student("Ivan", List.of(80, 90)));
        group.addStudent(new Student("Petro", List.of(70, 75)));

        boolean removed = group.removeStudentByName("Ivan");

        assertTrue(removed);
        assertEquals(1, group.getStudentCount());
    }

    @Test
    void testSortingByAverageGrade() {
        Student s1 = new Student("A", List.of(60, 70));
        Student s2 = new Student("B", List.of(90, 100));
        Student s3 = new Student("C", List.of(80, 85));

        Group group = new Group("CS-101");
        group.addStudent(s1);
        group.addStudent(s2);
        group.addStudent(s3);

        List<Student> sorted = group.getStudentsSortedByAverageExamGrade();

        assertEquals("A", sorted.get(0).getName());
        assertEquals("C", sorted.get(1).getName());
        assertEquals("B", sorted.get(2).getName());
    }
}
