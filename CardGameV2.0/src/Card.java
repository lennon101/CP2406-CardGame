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
            boolean myHardnessTrumps = hardness().isHigherThan(other.hardness());
            boolean myGravityTrumps = gravity().isHigherThan(other.gravity());
            boolean myCleavageTrumps = cleavage().compareTo(other.cleavage()) > 0;
            boolean myEconomicTrumps = economicValue().compareTo(other.economicValue()) > 0;
            boolean myCrustalTrumps = crustalAbundance().compareTo(other.crustalAbundance()) > 0;

            return new CardComparisonResult(!myHardnessTrumps, !myGravityTrumps, !myCleavageTrumps, !myEconomicTrumps, !myCrustalTrumps);
        }
    }

    public void displayCategories() {
        if (isTrump()){
            System.out.println("Trump Card! Category is: " + trumpType());
        }else {

            for (GameCategory category:GameCategory.values()){
                System.out.println((category.ordinal() + 1) + ": " + category + " Value: " + getTrumpValueForCategory(category));
            }
        }
    }

    public String getTrumpValueForCategory(GameCategory gameCategory) {
        if (isTrump()) {
            return trumpType().toString();
        } else {
            switch (gameCategory) {
                case HARDNESS:
                    return hardness().higher() + "";
                case ECONOMIC_VALUE:
                    return economicValue().toString();
                case CRUSTAL_ABUNDANCE:
                    return crustalAbundance().toString();
                case SPECIFIC_GRAVITY:
                    return gravity().higher() + "";
                case CLEAVAGE:
                    return cleavage().toString();
            }
        }
        return "";
    }

    @Override
    public String toString() {
        if (isTrump()){
            return "TrumpCard{" +
                    "title = " + name() + "; " +
                    "subtitle = " + trumpType() + "}";
        }else {
            return "PlayCard{" +
                    "Title = " + name() + "; " +
                    "Hardness = " + hardness() + "; " +
                    "Specific Gravity = " + gravity() + "; " +
                    "Clevage = " + cleavage() + "; " +
                    "Crustal Abundance = " + crustalAbundance() + "; " +
                    "Economic Value = " + economicValue() + "; " +
                    "}";
        }
    }
}