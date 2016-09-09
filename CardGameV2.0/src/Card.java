/**
 * Created by danelennon on 9/09/2016.
 */
public abstract class Card {
    abstract String name();

    abstract Range hardness();

    abstract Range gravity();

    abstract CleavageValue cleavage();

    abstract CrustalAbundanceValue crustalAbundance();

    abstract EconomicValue economicValue();

    abstract TrumpType trumpType();

    public boolean isTrump(){
        return trumpType() != TrumpType.NONE;
    }

    public CardComparisonResult compare(Card other){
        if (other.isTrump()) {
            //We have a trump card
            return new CardComparisonResult(true, true, true, true, true);
        } else {
            boolean otherHardnessTrumps = other.hardness().isHigherThan(hardness());

            boolean myGravityTrumps = gravity().isHigherThan(other.gravity());
            boolean myCleavageTrumps = cleavage().compareTo(other.cleavage()) > 0;
            boolean myEconomicTrumps = economicValue().compareTo(other.economicValue()) > 0;
            boolean myCrustalTrumps = crustalAbundance().compareTo(other.crustalAbundance()) > 0;

            return new CardComparisonResult(otherHardnessTrumps, !myGravityTrumps, !myCleavageTrumps, !myEconomicTrumps, !myCrustalTrumps);
        }
    }
}