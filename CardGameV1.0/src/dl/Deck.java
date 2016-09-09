package dl;

import java.util.Vector;

/**
 * Created by danelennon on 19/08/2016.
 */
public interface Deck {
    void add(Card c);

    Vector<Card> cards();

    int getNumCards();

    void remove(Card c);

    void shuffle();
}
