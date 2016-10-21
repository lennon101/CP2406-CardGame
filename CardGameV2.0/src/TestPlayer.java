import java.nio.channels.Pipe;
import java.util.Random;

/**
 * Created by danelennon on 4/10/16.
 */
public class TestPlayer {
    private static final String CARD_XML_FILE = "MstCards_151021.plist";

    public static void main(String[] args) {
        Random random = new Random();
        System.out.println("=== Testing Player Class ===\n");

        //setting up deck
        String xmlFile = System.getProperty("user.dir") + "/CardGameV2.0/" + CARD_XML_FILE;
        Deck deck = new XMLDeckBuilder(xmlFile).deck();

        Player player = new Player("Charlie",true);

        System.out.print("Players name is: ");
        System.out.println(player.getName());

        System.out.print("Is the player human? ");
        System.out.println(player.isHuman());

        System.out.println("Is the player currently pased? ");
        System.out.println(player.isPassed());

        System.out.println("Give the player a deck of cards");
        BasicDeck hand = new BasicDeck();
        for (int j = 0; j < 5; ++j) {
            Card c = deck.getCard(j);
            hand.add(c);
        }
        hand.add(new TheGeophysicistCard());
        hand.add(new MagnesiteCard());
        player.add(hand);

        int numCards = player.getNumCards();
        System.out.println("The player has: " + Integer.toString(numCards) + " cards in their hand");

        System.out.println("Display the players hand:");
        player.displayHand();

        System.out.print("A random cardPanel drawn from the players hand is: ");
        System.out.println(player.getCard(numCards-1));

        System.out.println("does player have the special combo? " + player.hasCombo());


    }

}
