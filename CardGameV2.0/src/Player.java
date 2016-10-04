
/**
 * Created by danelennon on 1/09/2016.
 */
public class Player {
    private boolean _dealer;
    private boolean _human;
    private BasicDeck _hand;
    private boolean passed = false;
    private String _name;

    public Player(String name, boolean isHuman) {
        this._name = name;
        this._human = isHuman;
    }

    public BasicDeck get_playerDeck() {
        return _hand;
    }

    public int getNumCards(){
        return _hand.getNumCards();
    }

    public Card getCard(int card){
        return this._hand.cards().get(card);
    }

    @Override
    public String toString() {
        return "Player{" +
                "_hand=" + _hand +
                '}';
    }

    public void displayHand() {
        for (int i = 0; i< getNumCards(); ++i){
            System.out.println("Card " + (i+1) + ": " + getCard(i));
        }
        System.out.println();
    }

    public boolean isPassed() {
        return passed;
    }

    public boolean isHuman() {
        return this._human;
    }

    public boolean isDealer() {
        return this._dealer;
    }

    public void set_dealer(boolean _dealer) {
        this._dealer = _dealer;
    }

    public void removeCardFromHand(Card c) {
        _hand.remove(c);
    }

    public String getName() {
        return _name;
    }

    public void add(BasicDeck hand) {
        this._hand = hand;
    }

    public void passed(boolean b) {
        passed = b;
    }


    public void pickUpCard(Card c) {
        _hand.add(c);
    }
}
