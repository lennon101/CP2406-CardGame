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

    public Game(int numPlayers,Deck deck) {
        this.numPlayers = numPlayers;
        this.deck = deck;
        this.players = new Vector<Player>();
    }

    public void selectDealer(){
        //todo: randomise this selection
        this.dealerId = 1;
    }

    //sets up a vector of Players and deals the right number of cards to them
    public void dealCardsToPlayers(){
        for (int i = 0;i<numPlayers;++i){
            BasicDeck hand = new BasicDeck();
            for (int j = 0; j < NUM_STARTS_CARDS; ++j) {
                Card c = deck.cards().get(j);
                if (c.getClass().equals(PlayCard.class)) {
                    hand.add(c);
                }
            }
            this.players.add(new Player(hand));
        }

    }

    public Player getPlayer(int indexOfPlayer) {
        return players.get(indexOfPlayer);
    }
}
