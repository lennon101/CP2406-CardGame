import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Created by danelennon on 29/08/2016.
 */
public class TwelveInts {
    public static void main(String[] args) {
        Integer[] numbers = new Integer[12];

        Scanner input = new Scanner(System.in);
        for (int i = 0; i<numbers.length;++i){
            System.out.println("enter a number");
            Integer number = input.nextInt();
            numbers[i] = number;
        }

        System.out.println("the numbers in order they were entered is: ");
        for (int i = 0;i< numbers.length;++i){
            System.out.println(numbers[i]);
        }

        System.out.println("the numbers in reverse order is: ");
        for (int i = numbers.length-1;i>0;--i){
            System.out.println(numbers[i]);
        }
    }
}
