import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
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
    private GameCategory _gameCategory;
    private Card _lastCardPlayed;

    private Vector<String> aiNames = new Vector<String>(Arrays.asList("Turkish", "Tommy", "Mickey O'Niel", "Brick Top", "Vinny", "Sol", "Tyrone", "Cousin Avi", "Boris The Blade", "Bullet Tooth Tony", "Gorgeous George", "Doug The Head", "Franky Four-Fingers", "Mullet"));
    private int _nextPlayerId = 0;

    public Game(String humanName, int numPlayers,Deck deck) {
        this._numPlayers = numPlayers;
        this._picUpDeck = deck;
        this._players = new Vector<Player>();
        this._humanName = humanName;

        addPlayers();
        selectDealer();
    }

    public void selectDealer(){
        Random random = new Random();
        int dealerId = random.nextInt(numPlayersLeft());
        this._dealerId = dealerId;
        this._nextPlayerId = dealerId;
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
        if (numPlayersLeftInGame() <= 1) {
            return true;
        }else{
            return false;
        }
    }

    private int numPlayersLeftInGame() {
        return _players.size();
    }

    public void removePlayer(Player p){
        _players.remove(p);
    }

    public Player getNextAvailablePlayer() {
        incrementPlayer();
        while (_players.get(_nextPlayerId).isPassed()){
            incrementPlayer();
        }
        return _players.get(_nextPlayerId); //player to the left of last player
    }

    public Player getPreviousPlayer() {
        if (_nextPlayerId - 1 < 0){
            return _players.get(0);
        }else {
            return _players.get(_nextPlayerId-1);
        }
    }

    private void incrementPlayer() {
        if (_nextPlayerId +1>=_players.size()){
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
    }

    public void setUpRound(Player ai) {

        System.out.println("--- " + ai.getName() + " placing first card down and choosing trump category");
        Card c = getValidFirstCard(ai);
        int gcNum = chooseCategory();
        System.out.println("--- he has selected card: " + c.name());
        GameCategory gc = GameCategory.values()[gcNum];
        System.out.println("--- he selected the " + gc + " Category with a top value of: " + c.getTrumpValueForCategory(gc) + "\n");

        playFirstCard(c, gc, ai);
    }

    public Card getValidFirstCard(Player p){
        if (p.isHuman()){
            Card selectedCard = null;
            boolean validCardChoice = false;
            while (!validCardChoice){
                System.out.print("Choose a card to play by entering the card number (1-" + p.getNumCards() + "): ");
                int cardNum = getNumInRange(1,p.getNumCards());
                selectedCard = p.getCard(cardNum-1);

                System.out.println("You have selected card " + (cardNum) + ": " + selectedCard.name() + "\n");

                if (selectedCard.isTrump()){
                    System.out.println("You must select a card other than a trump to start the round");
                }else {
                    validCardChoice = true;
                }
            }
            return selectedCard;
        }else {
            Random random = new Random();
            Card c = null;
            boolean isValid = false;
            while (!isValid) {
                int cardNum = random.nextInt(p.getNumCards());
                c = p.getCard(cardNum);
                if (!c.isTrump()) {
                    isValid = true;
                }
            }
            return c;
        }
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
        try {
            if (_lastCardPlayed.isTrump()){
                return true;
            }
        }catch (Throwable t){
            System.out.println(t);
        }
        return numPlayersLeft() <=1;
    }

    public int numPlayersLeft(){
        int numPlayersPassed = 0;
        for (Player p:_players){
            if (p.isPassed()){
                ++numPlayersPassed;
            }
        }
        return getNumPlayers() - numPlayersPassed;
    }

    private int getNumPlayers() {
        return _players.size();
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
                System.out.print("--- he has selected: " + c.name() + " with a trump category of: " + c.trumpType() + "\n");
                playCard(ai, c);
                playAfterTrump(ai);
                canPlay = true;
                break;
            } else if (cardCanBePlayed(c)) {
                System.out.println("--- he has selected: " + c.name() + " with a " + _gameCategory + " value of: " + c.getTrumpValueForCategory(_gameCategory) + "\n");
                playCard(ai, c);
                canPlay = true;
                break;
            }
        }

        if (!canPlay){
            System.out.println("--- he has chosen to pass\n");
            ai.passed(true);
            pickUp(ai);
        }
    }

    public void playAfterTrump(Player p){
        GameCategory gc = null;
        System.out.println("--- " + p.getName() + " is following instructions on trump card placed down");
        System.out.println("--- Game Category set to: " + _lastCardPlayed.trumpType());

        if (p.getNumCards() >0){
            Card c = getValidFirstCard(p);

            switch (_lastCardPlayed.trumpType()) {
                case ANY:
                    System.out.println("--- choosing trump category");
                    if (p.isHuman()){
                        c.displayCategories();
                        gc = getGameCategoryFromUser();
                    }else {
                        int gcNum = chooseCategory();
                        gc = GameCategory.values()[gcNum];
                    }
                    System.out.println("--- he chose: " + gc);
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
            System.out.println("--- has selected the " + c.name() + " card: " + c.getTrumpValueForCategory(gc) + "\n");
            playFirstCard(c,gc,p);
            unPassAllPlayers();
        }else{
            System.out.println(p.getName() + " has no more cards to play and has won this game! yew!");
        }
    }

    public GameCategory getGameCategoryFromUser() {
        int i = getNumInRange(1,GameCategory.values().length);
        GameCategory gc = GameCategory.values()[i-1];
        return gc;
    }

    public int getNumInRange(int start, int stop) {
        Scanner input = new Scanner(System.in);
        boolean valid = false;
        int index = -1;
        String indexStr = "";
        while (!valid){
            indexStr = input.next();
            try{
                index = Integer.parseInt(indexStr);
            } catch (Throwable t){
                System.out.println("Not a valid number.");
            }
            if (index<start || index>stop){
                System.out.println("This is outside the valid range");
                System.out.print("Try again: ");
            }else {
                valid = true;
            }
        }
        return index;
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
        if (_picUpDeck.cards().size() <= 0) {
            System.out.println("No cards to pick up");
        }else{
            Card c = _picUpDeck.getCard(0); //the top card
            _picUpDeck.remove(c);
            player.pickUpCard(c);
        }

    }

    public Card getLastCard() {
        return _lastCardPlayed;
    }

    public Player getRoundWinner() {
        Player roundWinner = null;
        if (_lastCardPlayed.isTrump()){
            return getPreviousPlayer();
        }else if (numPlayersLeft() == 1){
            for (Player p:_players){
                if (!p.isPassed()){
                    roundWinner = p;
                    break;
                }
            }
            return roundWinner;
        }else {
            System.out.println("no winner for this round yet");
        }
        return null;
    }


}