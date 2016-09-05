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

    public boolean hasHigherHardnessTrump(double hardnessValue){
        if (hardnessValue>this._hardnessTrump){
            return true;
        }else return false;
    }

    public boolean hasHigherSpecificGravityTrump(double specificGravityValue){
        if (specificGravityValue>this._specificGravityTrump){
            return true;
        }else return false;
    }

    public boolean hasHigherCleavageTrump(String cleavageValue){
        int cleavageRank = getCleavageValueRank(cleavageValue);
        if (cleavageRank > _cleavageTrump){
            this._cleavageTrump = cleavageRank;
            return true;
        }else {
            return false;
        }
    }

    public int getCleavageValueRank(String cleavageValue){
        int cleavageRank = 0;
        for (int i = 0; i< cleavageList.length; ++i){
            if (cleavageList[i].equals(cleavageValue)){
                cleavageRank = i;
            }
        }
        return cleavageRank;
    }

    public boolean hasHigherCrustalAbundanceTrump(String crustalAbundanceValue){
        int crustalAbundanceRank = getCrustalAbundanceRank(crustalAbundanceValue);
        if (crustalAbundanceRank > this._crustalAbundanceTrump){
            return true;
        }else {
            return false;
        }
    }
    public int getCrustalAbundanceRank(String crustalAbundanceValue){
        int crustalAbundanceRank = 0;
        for (int i = 0; i< _crustalAbundanceList.length; ++i){
            if (this._crustalAbundanceList[i].equals(crustalAbundanceValue)){
                crustalAbundanceRank = i;
            }
        }
        return crustalAbundanceRank;
    }

    public boolean hasHigherEconomicValueTrump(String economicValue){
        int economicValueRank = getEconomicValueRank(economicValue);
        if (economicValueRank > this._economicValueTrump){
            this._crustalAbundanceTrump = economicValueRank;
            return true;
        }else {
            return false;
        }
    }
    public int getEconomicValueRank(String economicValue){
        int economicValueRank = 0;
        for (int i = 0; i< this._economicValueList.length; ++i){
            if (this._economicValueList[i].equals(economicValue)){
                economicValueRank = i;
            }
        }
        return economicValueRank;
    }

    public void set_hardnessTrump(double _hardnessTrump) {
        this._hardnessTrump = _hardnessTrump;
    }

    public void set_specificGravityTrump(double _specificGravityTrump) {
        this._specificGravityTrump = _specificGravityTrump;
    }

    public void set_cleavageTrump(int _cleavageTrump) {
        this._cleavageTrump = _cleavageTrump;
    }

    public void set_crustalAbundanceTrump(int _crustalAbundanceTrump) {
        this._crustalAbundanceTrump = _crustalAbundanceTrump;
    }

    public void set_economicValueTrump(int _economicValueTrump) {
        this._economicValueTrump = _economicValueTrump;
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

    public boolean playedCardHasHigherTrumpValue(PlayCard c) {
        Object trumpObject = c.getDictOfTrumpCategories().get(this._trumpCategory);

        switch (this._trumpCategory) {
            case HARDNESS:
                try {
                    if (hasHigherHardnessTrump((double) trumpObject)) {
                        set_hardnessTrump((double) trumpObject);
                        return true;
                    } else {
                        return false;
                    }
                }catch (Throwable t){
                    System.out.println("failed to cast trump category to double");
                }
                break;
            case SPECIFIC_GRAVITY:
                try {
                    if (hasHigherSpecificGravityTrump((double) trumpObject)) {
                        set_specificGravityTrump((double) trumpObject);
                        return true;
                    } else {
                        return false;
                    }
                }catch (Throwable t){
                    System.out.println("failed to cast trump category to double");
                }
                break;
            case CLEAVAGE:
                try {
                    if (hasHigherCleavageTrump((String) trumpObject)) {
                        set_cleavageTrump(getCleavageValueRank((String) trumpObject));
                        return true;
                    } else {
                        return false;
                    }
                }catch (Throwable t){
                    System.out.println("failed to cast trump category to String");
                }
                break;
            case CRUSTAL_ABUNDANCE:
                try {
                    if (hasHigherCrustalAbundanceTrump((String) trumpObject)) {
                        set_crustalAbundanceTrump(getCrustalAbundanceRank((String) trumpObject));
                        return true;
                    } else {
                        return false;
                    }
                }catch (Throwable t){
                    System.out.println("failed to cast trump category to String");
                }
                break;
            case ECONOMIC_VALUE:
                try {
                    if (hasHigherEconomicValueTrump((String) trumpObject)) {
                        set_economicValueTrump(getEconomicValueRank((String) trumpObject));
                        return true;
                    } else {
                        return false;
                    }
                } catch (Throwable t) {
                    System.out.println("Failed to cast trump category to String");
                }
                break;
        }
        return false;
    }

    public String getTrumpValue(){
        switch (this._trumpCategory) {
            case HARDNESS:
                try{
                    return this._hardnessTrump + "";
                }catch (Throwable t){}
            break;
            case SPECIFIC_GRAVITY:
                try{
                    return this._specificGravityTrump + "";
                }catch (Throwable t){}
            break;
            case CLEAVAGE:
                try{
                    return this._cleavageTrump + "";
                }catch (Throwable t){}
            break;
            case CRUSTAL_ABUNDANCE:
                try{
                    return this._crustalAbundanceTrump + "";
                }catch (Throwable t){}
            break;
            case ECONOMIC_VALUE:
                try{
                    return this._economicValueTrump + "";
                }catch (Throwable t){}
            break;
        }
        return "";
    }
}
