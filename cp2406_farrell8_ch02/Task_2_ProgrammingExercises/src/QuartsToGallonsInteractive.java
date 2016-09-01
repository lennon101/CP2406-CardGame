import com.sun.xml.internal.ws.api.model.ExceptionType;

import java.util.Scanner;

/**
 * Created by danelennon on 13/08/2016.
 */
public class QuartsToGallonsInteractive {
    public static void main(String[] args) {

        final int quartsInGallon = 4;
        boolean valid = false;
        Scanner input = new Scanner(System.in);

        int price = 0;

        while (!valid)  {
            System.out.print("Enter a price (for the job) in quarts: ");

            try {
                price = Integer.parseInt(input.next());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("invalid input, try again...");
            }

            while (valid && price <= 0){
                System.out.println("price must be greater than zero");
                System.out.print("Enter a price (for the job) in quarts: ");
                try {
                    price = Integer.parseInt(input.next());
                    valid = true;
                } catch (NumberFormatException e) {
                    System.out.println("invalid input, try again...");
                }
            }
        }

        int remainder = price % quartsInGallon;

        int gallons = (price - remainder)/quartsInGallon;

        System.out.println("A job that needs " + price + "quarts requires " + gallons + "gallons plus " + remainder + "quarts");


    }
}
