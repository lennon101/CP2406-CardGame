import com.sun.org.apache.xpath.internal.operations.And;

import java.util.Scanner;

/**
 * Created by danelennon on 14/08/2016.
 */
public class TicketNumber {
    public static void main(String[] args) {
        int ticketNum = 0;
        boolean valid = false;

        Scanner input = new Scanner(System.in);

        while (!valid)  {
            System.out.print("Enter a ticket number: ");

            try {
                ticketNum = Integer.parseInt(input.next());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("invalid input, try again...");
            }

            while (valid && ticketNum <= 0){
                System.out.println("ticket number must be greater than zero");
                System.out.print("Enter a ticket number: ");
                try {
                    ticketNum = Integer.parseInt(input.next());
                    valid = true;
                } catch (NumberFormatException e) {
                    System.out.println("invalid input, try again...");
                }
            }
        }

        int truncated = ticketNum / 10;
        int lastDigit = ticketNum % 10;
        int remainder = truncated % 7;

        System.out.println("The ticket number is: " + (remainder == lastDigit));
    }

}
