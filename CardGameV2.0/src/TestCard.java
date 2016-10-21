/**
 * Created by danelennon on 4/10/16.
 */
public class TestCard {
    public static void main(String[] args) {
        MagnesiteCard magnesiteCard = new MagnesiteCard();
        System.out.println("=== Testing GibbsiteCard ===\n");

        System.out.println("Testing cardPanel display");
        System.out.println(magnesiteCard);

        System.out.print("Cards trump value for game category of HARDNESS is:");
        System.out.println(magnesiteCard.getTrumpValueForCategory(GameCategory.HARDNESS));

        System.out.print("Is the cardPanel a trump cardPanel? ");
        System.out.println(magnesiteCard.isTrump());

        System.out.print("The cards trump type is: ");
        System.out.println(magnesiteCard.trumpType());
    }
}
