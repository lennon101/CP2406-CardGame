package dl;

import java.util.Vector;

/**
 * Created by danelennon on 2/09/2016.
 */
public class Game {

    private int _numPlayers;
    private int NUM_CARDS_TO_START_WITH = 8;
    private int _dealerId;
    private Vector<Player> _players;
    private Deck _deck;

    private Trump_Categories _trumpCategory = null;
    private double _hardnessTrump =0;
    private double _specificGravityTrump =0;
    private int _cleavageTrump = 0;
    private int _crustalAbundanceTrump = 0;
    private int _economicValueTrump = 0;

    private String[] cleavageList = {"none","poor/none","1 poor","2 poor","1 good, 1 poor",
        "2 good","3 good","1 perfect","1 perfect, 1 good","1 perfect, 2 good",
        "2 perfect, 1 good","3 perfect","4 perfect","6 perfect"};

    private String[] _crustalAbundanceList = {"ultratrace", "trace", "low", "moderate", "high", "very high"};
    private String[] _economicValueList = {"trivial","low","moderate","high","very high","I'm rich!"};

    public Game(int numPlayers,Deck deck) {
        this._numPlayers = numPlayers;
        this._deck = deck;
        this._players = new Vector<Player>();
    }

    public boolean testAndSetHardnessTrump(double hardnessValue){
        if (hardnessValue>this._hardnessTrump){
            this._hardnessTrump = hardnessValue;
            return true;
        }else return false;
    }

    public boolean testAndSetSpecificGravityTrump(double specificGravityValue){
        if (specificGravityValue>this._specificGravityTrump){
            this._specificGravityTrump = specificGravityValue;
            return true;
        }else return false;
    }

    public boolean testAndSetCleavageTrump(String cleavageValue){
        int cleavageRank = 0;
        for (int i = 0; i< cleavageList.length; ++i){
            if (cleavageList[i].equals(cleavageValue)){
                cleavageRank = i;
            }
        }
        if (cleavageRank > _cleavageTrump){
            this._cleavageTrump = cleavageRank;
            return true;
        }else {
            return false;
        }
    }

    public boolean testAndSetCrustalAbundanceTrump(String crustalAbundanceValue){
        int crustalAbundanceRank = 0;
        for (int i = 0; i< _crustalAbundanceList.length; ++i){
            if (this._crustalAbundanceList[i].equals(crustalAbundanceValue)){
                crustalAbundanceRank = i;
            }
        }
        if (crustalAbundanceRank > this._crustalAbundanceTrump){
            this._crustalAbundanceTrump = crustalAbundanceRank;
            return true;
        }else {
            return false;
        }
    }

    public boolean testAndSetEconomicValueTrump(String economicValue){
        int economicValueRank = 0;
        for (int i = 0; i< this._economicValueList.length; ++i){
            if (this._economicValueList[i].equals(economicValue)){
                economicValueRank = i;
            }
        }
        if (economicValueRank > this._economicValueTrump){
            this._crustalAbundanceTrump = economicValueRank;
            return true;
        }else {
            return false;
        }
    }

    public Trump_Categories get_trumpCategory() {
        return _trumpCategory;
    }

    public void set_trumpCategory(Trump_Categories _trumpCategory) {
        this._trumpCategory = _trumpCategory;
    }

    public int get_numPlayers() {
        return _numPlayers;
    }

    public void set_numPlayers(int _numPlayers) {
        this._numPlayers = _numPlayers;
    }

    public void selectDealer(){
        //todo: randomise this selection
        this._dealerId = 1;
    }

    public void shuffleDeck(){
        this._deck.shuffle();
    }

    //sets up a vector of Players and deals the right number of cards to them
    public void dealCardsToPlayers(){
        for (int i = 0; i< _numPlayers; ++i){
            BasicDeck hand = new BasicDeck();
            for (int j = 0; j < NUM_CARDS_TO_START_WITH; ++j) {
                Card c = _deck.cards().get(j);
                if (c.getClass().equals(PlayCard.class) || (c.getClass().equals(TrumpCard.class))) {
                    hand.add(c);
                    _deck.remove(c);
                }
            }
            this._players.add(new Player(hand));
        }
    }

    /*
    public boolean isTrumpHigherThanCurrent(String _trumpCategory,String trumpValue){
        boolean higher = false;
        switch (_trumpCategory){
            case "hardness": higher = testHardness(trumpValue);
                break;
            case "specific_gravity":higher = testSpecificGravity(trumpValue);
                break;
            case "cleavage":higher = testCleavage(trumpValue);
                break;
            case "crustal_abundance":higher = testCrustalAbundance(trumpValue);
                break;
            case "economic_value":higher = testEconomicValue(trumpValue);
                break;
        }
        return higher;
    }


    private boolean testHardness(String trumpValue) {
        try{
            this.
        }
        return higher;
    }
    */

    public Player getPlayer(int indexOfPlayer) {
        return _players.get(indexOfPlayer);
    }

    public Vector<Card> get_deck(){
        return _deck.cards();
    }

    public boolean roundComplete() {
        int numPlayersPassed = 0;
        for (int i = 0; i< _numPlayers; ++i){
            if (_players.get(i).isPassed()){
                ++numPlayersPassed;
            }
        }

        if (numPlayersPassed== _numPlayers -1){
            return true;
        }else {
            return false;
        }
    }

    public void pickUp(Player player, int numCards) {
        for (int i = 0;i<numCards;++i){
            Card c = _deck.cards().firstElement();
            _deck.remove(c);
            player.get_playerDeck().add(c);
        }
    }
}
