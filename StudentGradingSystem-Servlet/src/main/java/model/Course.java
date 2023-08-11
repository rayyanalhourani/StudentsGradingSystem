package model;

public class Course {
    private int course_id;
    private String course_name;
    private int teacher_id;

    public Course(int course_id, String course_name, int teacher_id) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.teacher_id = teacher_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    @Override
    public String toString() {
        return "Course{" +
                "course_id=" + course_id +
                ", course_name='" + course_name + '\'' +
                ", teacher_id=" + teacher_id +
                '}';
    }
}
