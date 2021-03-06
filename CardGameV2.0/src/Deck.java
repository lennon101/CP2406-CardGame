/**
 * Created by danelennon on 19/08/2016.
 */

import java.util.Vector;

public interface Deck {
    void add(Card c);

    Card getCard(int i);

    Vector<Card> cards();

    int getNumCards();

    void remove(Card c);

    void shuffle();
}
