/**
 * Created by danelennon on 4/10/16.
 */
public class TestCard {
    public static void main(String[] args) {
        GibbsiteCard gibbsiteCard = new GibbsiteCard();
        System.out.println("=== Testing GibbsiteCard ===\n");

        System.out.println("Testing card display");
        System.out.println(gibbsiteCard);

        System.out.print("Cards trump value for game category of HARDNESS is:");
        System.out.println(gibbsiteCard.getTrumpValueForCategory(GameCategory.HARDNESS));

        System.out.print("Is the card a trump card? ");
        System.out.println(gibbsiteCard.isTrump());

        System.out.print("The cards trump type is: ");
        System.out.println(gibbsiteCard.trumpType());
    }
}
