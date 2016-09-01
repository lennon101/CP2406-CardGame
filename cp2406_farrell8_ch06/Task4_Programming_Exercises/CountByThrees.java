/**
 * Created by danelennon on 22/08/2016.
 */
public class CountByThrees {
    public static void main(String[] args) {
        int NUM = 3;
        int END_NUM = 300;

        for (int i = NUM; i <=END_NUM; i=i+3){
            System.out.print(i + " ");
            if (i%30 == 0) System.out.println();
        }

    }
}
