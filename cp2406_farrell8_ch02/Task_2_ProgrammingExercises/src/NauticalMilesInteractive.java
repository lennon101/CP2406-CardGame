import java.util.Scanner;

/**
 * Created by danelennon on 14/08/2016.
 */
public class NauticalMilesInteractive {

    public static void main(String[] args) {
        double km = 1.852;
        double miles = 1.150779;
        boolean valid = false;

        double nauticalMiles = 0;

        Scanner input = new Scanner(System.in);

        while (!valid)  {
            System.out.print("Enter a value in nautical miles to convert: ");
            try {
                nauticalMiles = Double.parseDouble(input.next());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("invalid input, try again...");
            }

            while (valid && nauticalMiles <= 0){
                System.out.println("number must be greater than zero");
                System.out.print("Enter a value in nautical miles to convert: ");
                try {
                    nauticalMiles = Double.parseDouble(input.next());
                    valid = true;
                } catch (NumberFormatException e) {
                    System.out.println("invalid input, try again...");
                }
            }
        }

        System.out.println("Given " + nauticalMiles + " nautical miles, this equats to " + km * nauticalMiles + " km's and " + miles * nauticalMiles + " miles");
    }
}

