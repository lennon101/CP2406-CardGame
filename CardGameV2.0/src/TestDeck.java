import java.util.Random;
import java.util.Scanner;

/**
 * Created by danelennon on 4/10/16.
 */
public class TestDeck {
    private static final String CARD_XML_FILE = "MstCards_151021.plist";

    public static void main(String[] args) {
        System.out.println("=== Testing Deck Class ===\n");

        Random random = new Random();

        String xmlFile = System.getProperty("user.dir") + "/CardGameV2.0/" + CARD_XML_FILE;
        Deck deck = new XMLDeckBuilder(xmlFile).deck();

        System.out.print("The number of cards in the deck is: ");
        int numCards = deck.getNumCards();
        System.out.println(numCards);

        System.out.println("Display a random cardPanel from the deck");
        int randNum =random.nextInt(numCards);
        System.out.println("Displaying cardPanel number " + Integer.toString(randNum));
        System.out.println(deck.getCard(randNum));

        System.out.println("Test deck shuffle");
        deck.shuffle();
        System.out.println("Card number " + Integer.toString(randNum) + " is now: ");
        System.out.println(deck.getCard(randNum-1));
    }
}
