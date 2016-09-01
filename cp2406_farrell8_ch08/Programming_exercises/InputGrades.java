import com.sun.tools.internal.ws.wsdl.document.jaxws.Exception;

import java.util.Scanner;

/**
 * Created by danelennon on 30/08/2016.
 */
public class InputGrades {
    public static void main(String[] args) {
        int NUM_STUDENTS = 2;

        Student[] students = new Student[NUM_STUDENTS];
        String studentId;
        String courseId;
        char grade = ' ';

        Scanner input = new Scanner(System.in);

        for (int i=0;i<students.length;++i){
            students[i] = new Student();
            System.out.print("Enter Student ID for student " + (i +1) + ": ");
            studentId = input.next();
            students[i].setId(studentId);

            for (int j=0;j<students[i].getNumCourses();++j){
                System.out.print("Student " + (i+1) + " - enter course ID " + (j+1) + ": ");
                courseId = input.next();

                boolean valid = false;
                while (!valid){
                    System.out.print("Enter course grade: ");
                    String gradeString = input.next();
                    try {
                        grade = gradeString.charAt(0);
                        if (grade == 'A' || grade == 'B' || grade == 'C' || grade == 'D' || grade == 'F'){
                            valid = true;
                        }else{
                            System.out.println("enter only A,B,C,D or F");
                        }
                    }catch (Throwable t){
                        System.out.println("enter only A,B,C,D or F");
                    }
                }

                students[i].setCourse(new CollegeCourse(courseId,grade), j);
            }
        }

        for (int k =0;k<students.length;++k){
            System.out.println(students[k]);
        }
    }
}
