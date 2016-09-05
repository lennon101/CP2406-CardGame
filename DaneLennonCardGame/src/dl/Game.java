package dl;

import java.util.Vector;

/**
 * Created by danelennon on 2/09/2016.
 */
public class Game {

    private int numPlayers;
    private int NUM_STARTS_CARDS = 8;
    private int dealerId;
    private Vector<Player> players;
    private Deck deck;

    private String[] cleavageRank = {"none","poor/none","1 poor","2 poor","1 good, 1 poor",
        "2 good","3 good","1 perfect","1 perfect, 1 good","1 perfect, 2 good",
        "2 perfect, 1 good","3 perfect","4 perfect","6 perfect"};
    private String[] _crustalAbundanceRank = {"ultratrace", "trace", "low", "moderate", "high", "very high"};
    private String[] _economicValue = {"trivial","low","moderate","high","very high","I'm rich!"};
    private String trumpCategory;
    private String trumpValue;

    public Game(int numPlayers,Deck deck) {
        this.numPlayers = numPlayers;
        this.deck = deck;
        this.players = new Vector<Player>();
    }

    public void selectDealer(){
        //todo: randomise this selection
        this.dealerId = 1;
    }

    public void shuffleDeck(){
        this.deck.shuffle();
    }

    //sets up a vector of Players and deals the right number of cards to them
    public void dealCardsToPlayers(){
        for (int i = 0;i<numPlayers;++i){
            BasicDeck hand = new BasicDeck();
            for (int j = 0; j < NUM_STARTS_CARDS; ++j) {
                Card c = deck.cards().get(j);
                if (c.getClass().equals(PlayCard.class) || (c.getClass().equals(TrumpCard.class))) {
                    hand.add(c);
                    deck.remove(c);
                }
            }
            this.players.add(new Player(hand));
        }
    }

    /*
    public boolean isTrumpHigherThanCurrent(String trumpCategory,String trumpValue){
        boolean higher = false;
        switch (trumpCategory){
            case "hardness": higher = testHardness(trumpValue);
                break;
            case "specific_gravity":higher = testSpecificGravity(trumpValue);
                break;
            case "cleavage":higher = testCleavage(trumpValue);
                break;
            case "crustal_abundance":higher = testCrustalAbundance(trumpValue);
                break;
            case "economic_value":higher = testEconomicValue(trumpValue);
                break;
        }
        return higher;
    }


    private boolean testHardness(String trumpValue) {
        try{
            this.
        }
        return higher;
    }
    */

    public Player getPlayer(int indexOfPlayer) {
        return players.get(indexOfPlayer);
    }

    public Vector<Card> getDeck(){
        return deck.cards();
    }

    public void setTrumpCategory(String trumpCategory) {
        this.trumpCategory = trumpCategory;
    }

    public String getTrumpCategory() {
        return this.trumpCategory;
    }

    public void setTrumpValue(Object o) {
        this.trumpValue = (String) o;
    }

    public boolean roundComplete() {
        int numPlayersPassed = 0;
        for (int i=0;i<numPlayers;++i){
            if (players.get(i).isPassed()){
                ++numPlayersPassed;
            }
        }

        if (numPlayersPassed==numPlayers-1){
            return true;
        }else {
            return false;
        }
    }

    public void pickUp(Player player, int numCards) {
        for (int i = 0;i<numCards;++i){
            Card c = deck.cards().firstElement();
            deck.remove(c);
            player.get_playerDeck().add(c);
        }
    }
}
