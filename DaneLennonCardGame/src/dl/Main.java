package dl;

public class Main {

    private static final String CARD_XML_FILE = "MstCards_151021.plist";

    public static void main(String[] args) {
        String xmlFile = System.getProperty("user.dir") + "/" + CARD_XML_FILE;

        Deck deck = new XMLDeckBuilder(xmlFile).deck();

        //set up UI player and list of AI Players
        BasicDeck playerDeck = new BasicDeck();
        for (int i=0;i<8;++i) {
            Card c = deck.cards().get(i);
            if (c.getClass().equals(PlayCard.class)){
                playerDeck.add(c);
            }
        }
        Player player1 = new Player(playerDeck);
        System.out.println(player1);
        System.out.println(player1.getPlayerCard(1));

		
		System.out.println("Welcome to the ultimate Super Trump Game\nA game that will make you an expert in elements from around the world"); 
		//create game class --> contains players which contain cards 
}
