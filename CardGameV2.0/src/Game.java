import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import jdk.nashorn.internal.runtime.OptimisticReturnFilters;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

/**
 * Created by danelennon on 9/09/2016.
 */
public class Game {
    private Deck _picUpDeck = new BasicDeck();
    private int _numPlayers;
    private int NUM_CARDS_TO_START_WITH = 8;
    private int _dealerId=0;
    private int humanPlayerId=0;
    private String _humanName;
    private Vector<Player> _players;
    private int numPlayersLeft;
    private GameCategory _gameCategory;
    private Card _cardLastPlayed;

    private Vector<String> aiNames = new Vector<String>(Arrays.asList("Turkish", "Tommy", "Mickey O'Niel", "Brick Top", "Vinny", "Sol", "Tyrone", "Cousin Avi", "Boris The Blade", "Bullet Tooth Tony", "Gorgeous George", "Doug The Head", "Franky Four-Fingers", "Mullet"));

    public Game(String humanName, int numPlayers,Deck deck) {
        this._numPlayers = numPlayers;
        this.numPlayersLeft = numPlayers;
        this._picUpDeck = deck;
        this._players = new Vector<Player>();
        this._humanName = humanName;

        addPlayers();
        selectDealer();
    }

    public void selectDealer(){
        Random random = new Random();
        int dealerId = random.nextInt(numPlayersLeft);
        //todo: randomise this selection
        this._dealerId = dealerId;
    }

    public void setHuman(){
        humanPlayerId = 0;
        // TODO: 9/09/2016 randomise this
    }

    public Player getHuman(){
        return _players.get(humanPlayerId);
    }

    public void shuffleDeck(){
        this._picUpDeck.shuffle();
    }

    public void addPlayers(){
        for (int i = 0; i< _numPlayers; ++i) {
            Random random = new Random();
            int randNameIndex = random.nextInt(aiNames.size());
            if (i == humanPlayerId) {
                this._players.add(new Player(_humanName, true));
            } else {
                this._players.add(new Player(aiNames.remove(randNameIndex), false));
            }

            if (i == _dealerId) {
                _players.get(i).set_dealer(true);
            }
        }
    }

    //sets up a vector of Players and deals the right number of cards to them
    public void dealCardsToPlayers(){
        if (_picUpDeck.getNumCards() < _numPlayers * NUM_CARDS_TO_START_WITH){
            System.out.println("Not enough cards in the deck for this number of players");
        }else{
            for (int i = 0; i< _numPlayers; ++i){
                BasicDeck hand = new BasicDeck();
                for (int j = 0; j < NUM_CARDS_TO_START_WITH; ++j) {
                    Card c = _picUpDeck.getCard(j);
                    hand.add(c);
                    _picUpDeck.remove(c);
                }

                _players.get(i).add(hand);
            }
        }

    }

    public Integer getDeckSize() {
        return _picUpDeck.getNumCards();
    }

    public boolean complete() {
        if (getNumPlayersLeft() <= 1) {
            return true;
        }else{
            return false;
        }
    }

    public int getNumPlayersLeft() {
        return numPlayersLeft;
    }

    public void removePlayer(int i){
        _players.remove(i);
        --numPlayersLeft;
    }

    public int getHumanPlayerId() {
        return humanPlayerId;
    }

    public int getDealerId() {
        return _dealerId;
    }

    public Player getFirstPlayer() {
        if (_dealerId+1>=_numPlayers){
            return  _players.get(0);
        }else{
            return _players.get(_dealerId+1); //player to the left of the dealer

        }
    }

    public GameCategory getCategory(){
        return this._gameCategory;
    }

    public boolean cardCanBePlayed(Card cardToBePlayed){
        _cardLastPlayed = new GibbsiteCard();

        //test if the last card played is trumped by the new card being played
        CardComparisonResult comparisonResult = _cardLastPlayed.compare(cardToBePlayed);

        if (_cardLastPlayed.isTrump()){
            //It's a trump
        }

        boolean cardTrumpedForCategory = false;
        switch(_gameCategory){
            case HARDNESS:
                cardTrumpedForCategory = comparisonResult.hardnessWasTrumped();
                _gameCategory = GameCategory.HARDNESS;
                break;
            case ECONOMIC_VALUE:
                cardTrumpedForCategory = comparisonResult.economicValueWasTrumped();
                _gameCategory = GameCategory.ECONOMIC_VALUE;
                break;
            case CRUSTAL_ABUNDANCE:
                cardTrumpedForCategory = comparisonResult.crustalAbundanceWasTrumped();
                _gameCategory = GameCategory.CRUSTAL_ABUNDANCE;
                break;
            case SPECIFIC_GRAVITY:
                cardTrumpedForCategory = comparisonResult.gravityWasTrumped();
                _gameCategory = GameCategory.SPECIFIC_GRAVITY;
                break;
            case CLEAVAGE:
                cardTrumpedForCategory = comparisonResult.cleavageWasTrumped();
                _gameCategory = GameCategory.CLEAVAGE;
                break;
        }

        //if the last card was trumped, then the new card is allowed to be placed down
        return cardTrumpedForCategory;
    }

    public void playCard(Player p, Card c) {
        this._cardLastPlayed = c;
        p.removeCardFromHand(c);
    }

    public String getTrumpValue() {
        return _cardLastPlayed.getTrumpValueForCategory(_gameCategory);
    }

    public Vector<Player> getPlayers() {
        return _players;
    }

    public void playFirstCard(Card c, GameCategory gc,Player p) {
        _gameCategory = gc;
        _cardLastPlayed = c;
        p.removeCardFromHand(c);
    }

    public void setUpRound() {
        Random random = new Random();
        Player ai = getFirstPlayer();
        System.out.println(ai + " placing first card down and choosing trump category");

        int cardNum = random.nextInt(ai.getNumCards());
        int gcNum = random.nextInt(GameCategory.values().length);
        Card c = ai.getCard(cardNum);
        System.out.println(ai + " has selected card " + (cardNum) + ": " + c.name() + "\n");
        GameCategory gc = GameCategory.values()[gcNum];
        System.out.println(ai + " has selected the " + gc + " Category with a starting value of: " + c.getTrumpValueForCategory(gc));
    }

    public Player getDealer() {
        return _players.get(_dealerId);
    }

    public void displayAllPlayers() {
        int i = 0;
        for (Player player:_players){
            System.out.println("Player " + ++i + ": " + player.getName() + " is ready to play");
        }
    }
}