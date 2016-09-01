import java.util.Scanner;

/**
 * Created by danelennon on 15/08/2016.
 */
public class NumbersDemo2 {
    public static void main(String[] args) {

        System.out.print("Enter number 1: ");
        Scanner input = new Scanner(System.in);
        int num1 = input.nextInt();

        System.out.print("Enter number 2: ");
        int num2 = input.nextInt();

        displayNumberPlusFive(num1);
        displayNumberPlusFive(num2);

        displayTwiceTheNumber(num1);
        displayTwiceTheNumber(num2);

    }

    public static void displayTwiceTheNumber(int number){
        System.out.println("Twice of " + number + " is " + number * 2);
    }

    public static void displayNumberPlusFive(int number){
        System.out.println(number +  " plus 5 is: " + (number + 5));
    }
}
