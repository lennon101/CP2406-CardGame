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
    private Card _lastCardPlayed;

    private Vector<String> aiNames = new Vector<String>(Arrays.asList("Turkish", "Tommy", "Mickey O'Niel", "Brick Top", "Vinny", "Sol", "Tyrone", "Cousin Avi", "Boris The Blade", "Bullet Tooth Tony", "Gorgeous George", "Doug The Head", "Franky Four-Fingers", "Mullet"));
    private int _nextPlayerId = 0;

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
        this._nextPlayerId = dealerId;
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
        incrementPlayer();
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

    public Player getNextPlayer() {
        return _players.get(_nextPlayerId); //player to the left of last player
    }

    private void incrementPlayer() {
        if (_nextPlayerId +1>=_numPlayers){
            _nextPlayerId = 0;
        }else{
            ++_nextPlayerId; //player to the left of last player
        }
    }

    public GameCategory getCategory(){
        return this._gameCategory;
    }

    public boolean cardCanBePlayed(Card cardToBePlayed){
        //test if the last card played is trumped by the new card being played
        CardComparisonResult comparisonResult = _lastCardPlayed.compare(cardToBePlayed);
        // TODO: 10/09/2016 this is breaking if a trump card has been previoiusly played
        if (_lastCardPlayed.isTrump()){
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
        _lastCardPlayed = c;
        p.removeCardFromHand(c);
        incrementPlayer();
    }

    public String getTrumpValue() {
        return _lastCardPlayed.getTrumpValueForCategory(_gameCategory);
    }

    public Vector<Player> getPlayers() {
        return _players;
    }

    public void playFirstCard(Card c, GameCategory gc,Player p) {
        _gameCategory = gc;
        _lastCardPlayed = c;
        p.removeCardFromHand(c);
        incrementPlayer();
    }

    public void setUpRound(Player ai) {
        Card c = null;
        GameCategory gc = null;
        if (_lastCardPlayed != null){
            if (_lastCardPlayed.isTrump()) {
                System.out.println("--- " + ai.getName() + " following instructions on trump card he placed down");
                System.out.println("--- Game Category set to: " + _lastCardPlayed.trumpType());
                switch (_lastCardPlayed.trumpType()) {
                    case ANY:
                        System.out.println("--- choosing trump category");
                        c = getValidFirstCard(ai);
                        int gcNum = chooseCategory();
                        gc = GameCategory.values()[gcNum];
                        break;
                    case HARDNESS:
                        gc = GameCategory.HARDNESS;
                        break;
                    case SPECIFIC_GRAVITY:
                        gc = GameCategory.SPECIFIC_GRAVITY;
                        break;
                    case CLEAVAGE:
                        gc = GameCategory.CLEAVAGE;
                        break;
                    case CRUSTAL_ABUNDANCE:
                        gc = GameCategory.CRUSTAL_ABUNDANCE;
                        break;
                    case ECONOMIC_VALUE:
                        gc = GameCategory.ECONOMIC_VALUE;
                        break;
                }
                c = getValidFirstCard(ai);
                System.out.println("--- he has selected the " + c.name() + " card: " + c.getTrumpValueForCategory(gc) + "\n");
            }
        }else {
            System.out.println("--- " + ai.getName() + " placing first card down and choosing trump category");
            c = getValidFirstCard(ai);
            int gcNum = chooseCategory();
            System.out.println("--- he has selected card: " + c.name());
            gc = GameCategory.values()[gcNum];
            System.out.println("--- he selected the " + gc + " Category with a top value of: " + c.getTrumpValueForCategory(gc) + "\n");
        }
        playFirstCard(c, gc, ai);
    }

    private Card getValidFirstCard(Player ai){
        Random random = new Random();
        Card c = null;
        boolean isValid = false;
        while (!isValid) {
            int cardNum = random.nextInt(ai.getNumCards());
            c = ai.getCard(cardNum);
            if (!c.isTrump()) {
                isValid = true;
            }
        }
        return c;
    }

    private int chooseCategory(){
        Random random = new Random();
        return random.nextInt(GameCategory.values().length);
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

    public boolean roundComplete() {
        int numPlayersPassed = 0;
        if (_lastCardPlayed.isTrump()){
            return true;
        }
        for (Player p:_players){
            if (p.isPassed()){
                ++numPlayersPassed;
            }
        }
        return numPlayersPassed >= numPlayersLeft-1;
    }

    public void aIRound(Player ai) {
        Random random = new Random();
        System.out.println(ai.getName() + "'s turn...");
        Card c = null;
        boolean canPlay = false;
        for (int i=0;i<ai.getNumCards();++i) {
            int cardNum = random.nextInt(ai.getNumCards());
            c = ai.getCard(cardNum);
            if (c.isTrump()) {
                System.out.println("--- he played a trump card!");
                System.out.print("--- he has selected: " + c.name() + " with a trump category of: " + c.getTrumpValueForCategory(_gameCategory) + "\n");
                --_nextPlayerId;
                canPlay = true;
                break;
            } else if (cardCanBePlayed(c)) {
                System.out.println("--- he has selected: " + c.name() + " with a " + _gameCategory + " value of: " + c.getTrumpValueForCategory(_gameCategory) + "\n");
                canPlay = true;
                break;
            }
        }
        if (canPlay){
            playCard(ai, c);
        }else{
            System.out.println("--- he has chosen to pass\n");
            ai.passed(true);
            pickUp(ai);
            incrementPlayer();
        }
    }

    public void unPassAllPlayers() {
        for (Player p:_players){
            p.passed(false);
        }
    }

    public void displayPickUpDeck() {
        for (Card c:_picUpDeck.cards()){
            System.out.println(c);
        }
    }

    public void pickUp(Player player) {
        Card c = _picUpDeck.getCard(0); //the top card
        _picUpDeck.remove(c);
        player.pickUpCard(c);
    }
}