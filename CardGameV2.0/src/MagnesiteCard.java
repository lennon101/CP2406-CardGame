/**
 * Created by danelennon on 9/09/2016.
 */
public class MagnesiteCard extends Card {
    public MagnesiteCard() {
    }

    @Override
    String filename() {
        return "";
    }

    @Override
    public String name() {
        return "Magnesite";
    }

    @Override
    public Range hardness() {
        return new Range(4);
    }

    @Override
    public Range gravity() {
        return new Range(3);
    }

    @Override
    public CleavageValue cleavage() {
        return CleavageValue.THREE_PERFECT;
    }

    @Override
    public CrustalAbundanceValue crustalAbundance() {
        return CrustalAbundanceValue.LOW;
    }

    @Override
    public EconomicValue economicValue() {
        return EconomicValue.MODERATE;
    }

    @Override
    public TrumpType trumpType() {
        return TrumpType.NONE;
    }
}
