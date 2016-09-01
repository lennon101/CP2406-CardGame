import java.util.Scanner;

/**
 * Created by danelennon on 15/08/2016.
 */
public class Percentages2 {
    public static void main(String[] args) {
        System.out.print("Enter number 1: ");
        Scanner input = new Scanner(System.in);
        int num1 = input.nextInt();

        System.out.print("Enter number 2: ");
        int num2 = input.nextInt();

        computePercentage(num1, num2);
        computePercentage(num2, num1);
    }

    public static void computePercentage(double a, double b){
        System.out.println(a + " is exactly " + String.format("%.2f",a/b *100) + "% of " + b);
    }
}
