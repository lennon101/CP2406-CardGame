import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Created by danelennon on 22/08/2016.
 */
public class AscendingAndDescending {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        ArrayList numArray = new ArrayList();

        for (int i = 0; i < 3; ++i){
            System.out.print("enter number " + (i+1) + ": ");
            numArray.add(input.nextInt());
        }

        Collections.sort(numArray);
        System.out.println(numArray);
        Collections.reverse(numArray);
        System.out.println(numArray);
    }
}
