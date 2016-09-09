/**
 * Created by danelennon on 9/09/2016.
 */
public class GibbsiteCard extends Card {
    public GibbsiteCard() {
    }

    @Override
    public String name() {
        return "Gibbsite";
    }

    @Override
    public Range hardness() {
        return new Range(2.5, 3.5);
    }

    @Override
    public Range gravity() {
        return new Range(2.4);
    }

    @Override
    public CleavageValue cleavage() {
        return CleavageValue.ONE_PERFECT;
    }

    @Override
    public CrustalAbundanceValue crustalAbundance() {
        return CrustalAbundanceValue.LOW;
    }

    @Override
    public EconomicValue economicValue() {
        return EconomicValue.HIGH;
    }

    @Override
    public TrumpType trumpType() {
        return TrumpType.NONE;
    }
}
