import java.util.Scanner;

/**
 * Created by danelennon on 22/08/2016.
 */
public class EvenOdd {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter a number: ");
        int num = input.nextInt();
        if (num % 2 == 0){
            System.out.println("the number is even");
        } else{
            System.out.println("the number is odd");
        }
    }

}
