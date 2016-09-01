package dl;

/**
 * Created by danelennon on 1/09/2016.
 */
public class Player {
    BasicDeck _playerDeck;

    public Player(BasicDeck playerDeck) {
        this._playerDeck = playerDeck;
    }

    public BasicDeck get_playerDeck() {
        return _playerDeck;
    }

    public Card getPlayerCard(int card1){
        return this._playerDeck.cards().get(card1);
    }

    @Override
    public String toString() {
        return "Player{" +
                "_playerDeck=" + _playerDeck +
                '}';
    }
}
