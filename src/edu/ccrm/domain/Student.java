package edu.ccrm.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a student in the CCRM system. Extends Person and manages
 * enrollments and credit load.
 *
 * @author VITyarthi
 * @version 1.1
 */
public class Student extends Person {

    private final List<Enrollment> enrollments = new ArrayList<>();

    /**
     * Constructs a new Student with the specified details.
     *
     * @param id the unique student ID
     * @param regNo the registration number
     * @param name the student's name
     * @param email the student's email
     */
    public Student(long id, String regNo, Name name, String email) {
        super(id, regNo, name, email);
    }

    /**
     * Gets an unmodifiable list of the student's enrollments.
     *
     * @return the list of enrollments
     */
    public List<Enrollment> getEnrollments() {
        return Collections.unmodifiableList(enrollments);
    }

    /**
     * Adds an enrollment to the student if not already enrolled in the course.
     *
     * @param enrollment the enrollment to add
     * @return true if added, false if already enrolled
     */
    public boolean addEnrollment(Enrollment enrollment) {
        Objects.requireNonNull(enrollment, "enrollment");
        for (Enrollment e : enrollments) {
            if (e.getCourse().getCode().equalsIgnoreCase(enrollment.getCourse().getCode())) {
                return false;
            }
        }
        return enrollments.add(enrollment);
    }

    /**
     * Removes an enrollment from the student.
     *
     * @param enrollment the enrollment to remove
     * @return true if removed, false otherwise
     */
    public boolean removeEnrollment(Enrollment enrollment) {
        return enrollments.remove(enrollment);
    }

    /**
     * Calculates the current credit load of the student.
     *
     * @return the total credits from current enrollments
     */
    public int currentCreditLoad() {
        return enrollments.stream().mapToInt(e -> e.getCourse().getCredits()).sum();
    }

    /**
     * Checks whether the student has any active enrollments.
     *
     * @return true if the student is enrolled in at least one course
     */
    public boolean hasEnrollments() {
        return !enrollments.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("Student %s | regNo=%s | credits=%d | active=%b",
                getName().getFullName(), getRegNo(), currentCreditLoad(), isActive());
    }

    /**
     * Interface for transcript line representation.
     */
    public interface TranscriptLine {

        String getCourseCode();

        String getCourseTitle();

        int getCredits();

        String getGrade();

        String toString();
    }

    public class TranscriptEntry implements TranscriptLine {

        private final Course course;
        private final String grade;

        public TranscriptEntry(Course course, String grade) {
            this.course = Objects.requireNonNull(course, "course");
            this.grade = Objects.requireNonNull(grade, "grade");
        }

        @Override
        public String getCourseCode() {
            return course.getCode();
        }

        @Override
        public String getCourseTitle() {
            return course.getTitle();
        }

        @Override
        public int getCredits() {
            return course.getCredits();
        }

        @Override
        public String getGrade() {
            return grade;
        }

        @Override
        public String toString() {
            return String.format("%s - %s (%d credits) | Grade: %s",
                    getCourseCode(), getCourseTitle(), getCredits(), grade);
        }
    }

    public List<TranscriptLine> generateTranscript() {
        List<TranscriptLine> transcript = new ArrayList<>();
        for (Enrollment e : enrollments) {
            // Convert Grade enum to String safely
            String gradeStr = e.getGrade() != null ? e.getGrade().name() : "N/A";
            transcript.add(new TranscriptEntry(e.getCourse(), gradeStr));
        }
        return transcript;
    }
}
