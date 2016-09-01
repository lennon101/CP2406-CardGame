/**
 * Created by danelennon on 30/08/2016.
 */
public class CollegeCourse {
    private String courseId;
    private int creditHours;
    private char grade;

    public CollegeCourse(String courseId, char grade) {
        this.courseId = courseId;
        this.grade = grade;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public char getGrade() {
        return grade;
    }

    public void setGrade(char grade) {
        this.grade = grade;
    }
}
