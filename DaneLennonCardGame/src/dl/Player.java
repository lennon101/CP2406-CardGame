package dl;

/**
 * Created by danelennon on 1/09/2016.
 */
public class Player {
    BasicDeck _hand;
    boolean trumpChooser;

    public Player(BasicDeck hand) {
        this._hand = hand;
    }

    public BasicDeck get_playerDeck() {
        return _hand;
    }

    public int getNumCardsInHand(){
        return _hand.getNumCards();
    }

    public Card getPlayerCard(int card){
        return this._hand.cards().get(card);
    }

    @Override
    public String toString() {
        return "Player{" +
                "_hand=" + _hand +
                '}';
    }

    public void displayHand() {
        for (int i = 0; i<getNumCardsInHand(); ++i){
            System.out.println("Card " + (i+1) + ": " + getPlayerCard(i));
        }
    }

    public boolean isTrumpChooser() {
        return trumpChooser;
    }

    public void setTrumpChooser(boolean trumpChooser) {
        this.trumpChooser = trumpChooser;
    }
}
