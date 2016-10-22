
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

    public boolean hasCombo(){
        boolean foundGeophysicist = false;
        boolean foundMagnesite = false;
        for (Card c:_hand.cards()){
            if (c.name().equals("Magnesite")){
                foundGeophysicist = true;
            }
            if (c.name().equals("The Geophysicist")){
                foundMagnesite = true;
            }
        }

        return foundGeophysicist && foundMagnesite;
    }

    public void playCard(Game g, Card c, GameView gv) {
        g.setLastCardPlayed(c);
        removeCardFromHand(c);

        if (getNumCards() == 0){
            System.out.println(getName() + " \n\nhas no more cards");
        }

        gv.displayCards(g);
    }

    public void playCombo(Game g,GameView gv) {
        if (hasCombo()){
            g.comboWasPlayed();

            //find each cardPanel to be played
            Card magnesite = null;
            Card geophysicist = null;
            for (Card c:_hand.cards()){
                if (c.name().equals("Magnesite")){
                    magnesite = c;
                }
                if (c.name().equals("The Geophysicist")){
                    geophysicist = c;
                }
            }
            playCard(g,magnesite, gv);
            playCard(g,geophysicist, gv);
        }
    }

    public void playFirstCard(Game g, Card c, GameCategory gc, GameView gv) {
        g.setGameCategory(gc);
        g.setLastCardPlayed(c);
        removeCardFromHand(c);
        if (getNumCards() == 0){
            System.out.println(getName() + " has no more cards");
        }

        gv.displayCards(g);
    }
}
