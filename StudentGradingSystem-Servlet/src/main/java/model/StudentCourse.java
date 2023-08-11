package model;

public class StudentCourse {
    private int student_id;
    private int course_id;
    private int medterm_mark;
    private int assignments_mark;
    private int final_exam_mark;

    public StudentCourse(int student_id, int course_id, int medterm_mark, int assignments_mark, int final_exam_mark) {
        this.student_id = student_id;
        this.course_id = course_id;
        this.medterm_mark = medterm_mark;
        this.assignments_mark = assignments_mark;
        this.final_exam_mark = final_exam_mark;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getMedterm_mark() {
        return medterm_mark;
    }

    public void setMedterm_mark(int medterm_mark) {
        this.medterm_mark = medterm_mark;
    }

    public int getAssignments_mark() {
        return assignments_mark;
    }

    public void setAssignments_mark(int assignments_mark) {
        this.assignments_mark = assignments_mark;
    }

    public int getFinal_exam_mark() {
        return final_exam_mark;
    }

    public void setFinal_exam_mark(int final_exam_mark) {
        this.final_exam_mark = final_exam_mark;
    }

    @Override
    public String toString() {
        return "StudentCourse{" +
                "student_id=" + student_id +
                ", course_id=" + course_id +
                ", medterm_mark=" + medterm_mark +
                ", assignments_mark=" + assignments_mark +
                ", final_exam_mark=" + final_exam_mark +
                '}';
    }
}
