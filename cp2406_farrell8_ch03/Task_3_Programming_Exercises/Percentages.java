/**
 * Created by danelennon on 15/08/2016.
 */
public class Percentages {
    public static void main(String[] args) {
        double num1 = 10;
        double num2 = 15;

        computePercentage(num1, num2);
        computePercentage(num2, num1);
    }

    public static void computePercentage(double a, double b){
        System.out.println(a + " is exactly " + String.format("%.2f",a/b *100) + "% of " + b);
    }

}
