/**
 * Created by danelennon on 13/08/2016.
 */
public class QuartsToGallons {
    public static void main(String[] args) {
        final int quartsInGallon = 4;
        int price = 18;

        int remainder = price % quartsInGallon;

        int gallons = (price - remainder)/quartsInGallon;

        System.out.println("A job that needs " + price + "quarts requires " + gallons + "gallons plus " + remainder + "quarts");

    }
}
