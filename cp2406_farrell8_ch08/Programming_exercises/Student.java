/**
 * Created by danelennon on 30/08/2016.
 */
public class Student {

    private String id;
    private CollegeCourse[] courses = new CollegeCourse[5];

    public CollegeCourse getCourse(int subjectNum) {
        return courses[subjectNum];
    }

    public int getNumCourses(){
        return courses.length;
    }

    public void setCourse(CollegeCourse course,int subjectNum) {
        this.courses[subjectNum] = course;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ID: " + getId();
    }
}
