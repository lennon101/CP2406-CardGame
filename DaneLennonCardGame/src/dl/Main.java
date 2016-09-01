package dl;

public class Main {

    private static final String CARD_XML_FILE = "MstCards_151021.plist";

    public static void main(String[] args) {
        String xmlFile = System.getProperty("user.dir") + "/" + CARD_XML_FILE;

        Deck deck = new XMLDeckBuilder(xmlFile).deck();
        for (Card c : deck.cards()) {
            System.out.println(c.toString());
        }
    }
}
