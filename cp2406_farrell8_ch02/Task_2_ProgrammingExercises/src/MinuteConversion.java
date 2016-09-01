import java.util.Scanner;

/**
 * Created by danelennon on 14/08/2016.
 */
public class MinuteConversion {
    public static void main(String[] args) {
        boolean valid = false;
        int numMinutes = 0;

        Scanner input = new Scanner(System.in);

        while (!valid) {
            System.out.print("Enter the number of minutes: ");
            try {
                numMinutes = Integer.parseInt(input.next());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("invalid input, try again...");
            }

            while (valid && numMinutes<=0){
                System.out.println("number of minutes must be greater than zero");
                System.out.print("Enter the number of minutes: ");
                try {
                    numMinutes = Integer.parseInt(input.next());
                    valid = true;
                } catch (NumberFormatException e) {
                    System.out.println("invalid input, try again...");
                    System.out.print("Enter the number of minutes: ");
                }

            }
        }


        double numHours = numMinutes/60;
        double numDays = numHours/24;

        System.out.println(numMinutes + " minutes equats to: " + String.format("%.2f", numHours)  + " hrs OR " + String.format("%.2f", numDays) + " days");
    }
}
