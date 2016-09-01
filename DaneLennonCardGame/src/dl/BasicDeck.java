package dl;

import java.util.Vector;

/**
 * Created by xander on 19/08/2016.
 */
public class BasicDeck implements Deck {

    private Vector<Card> _cards;

    public BasicDeck() {
        _cards = new Vector<Card>();
    }

    @Override
    public void add(Card c) {
        _cards.add(c);
    }

    @Override
    public Vector<Card> cards() {
        return _cards;
    }

    @Override
    public void remove(Card c) {
        _cards.remove(c);
    }

    @Override
    public void shuffle() {
        //needs work
    }

}
