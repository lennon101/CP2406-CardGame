
import java.util.Collections;
import java.util.Vector;

/**
 * Created by danelennon on 19/08/2016.
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
    public Card getCard(int i) {
        return _cards.get(i);
    }

    @Override
    public int getNumCards() {
        return _cards.size();
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
        if (_cards.size() <= 2){
            System.out.println("only 2 or less cards left in the deck, nothing to shuffle");
        }else {
            Collections.shuffle(_cards);
        }
    }

    @Override
    public String toString() {
        return "BasicDeck{" +
                "_cards=" + _cards +
                '}';
    }
}
