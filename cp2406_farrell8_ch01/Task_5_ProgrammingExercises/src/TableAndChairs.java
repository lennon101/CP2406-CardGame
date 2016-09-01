/**
 * Created by danelennon on 31/07/2016.
 */
public class TableAndChairs {
    public static void main(String[] args) {
        repeatX(1);
        repeatSpace(22);
        repeatX(1);
        System.out.println();

        repeatX(1);
        repeatSpace(22);
        repeatX(1);
        System.out.println();

        repeatX(1);
        repeatSpace(6);
        repeatX(10);
        repeatSpace(6);
        repeatX(1);
        System.out.println();

        repeatX(5);
        repeatSpace(2);
        repeatX(1);
        repeatSpace(8);
        repeatX(1);
        repeatSpace(2);
        repeatX(5);
        System.out.println();

        repeatX(1);
        repeatSpace(3);
        repeatX(1);
        repeatSpace(2);
        repeatX(1);
        repeatSpace(8);
        repeatX(1);
        repeatSpace(2);
        repeatX(1);
        repeatSpace(3);
        repeatX(1);
        System.out.println();

        repeatX(1);
        repeatSpace(3);
        repeatX(1);
        repeatSpace(2);
        repeatX(1);
        repeatSpace(8);
        repeatX(1);
        repeatSpace(2);
        repeatX(1);
        repeatSpace(3);
        repeatX(1);
        System.out.println();

    }

    public static void repeatX(int numToRepeat){
        for (int i = 0; i < numToRepeat; i++) {
            System.out.print("X");
        }
    }

    public static void repeatSpace(int numToRepeat){
        for (int i = 0; i < numToRepeat; i++) {
            System.out.print(" ");
        }
    }

}
