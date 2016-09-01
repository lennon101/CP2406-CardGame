/**
 * Created by danelennon on 15/08/2016.
 */
public class NumbersDemo {
    public static void main(String[] args) {
        int num1 = 10;
        int num2 = 15;

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
