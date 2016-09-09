
/**
 * Created by danelennon on 9/09/2016.
 */
public class CassiteriteCard extends Card {
    public CassiteriteCard() {
    }

    @Override
    public String name() {
        return "Cassiterite";
    }

    @Override
    public Range hardness() {
        return new Range(6.7, 7.1);
    }

    @Override
    public Range gravity() {
        return new Range(6.9, 7.1);
    }

    @Override
    public CleavageValue cleavage() {
        return CleavageValue.ONE_GOOD_ONE_POOR;
    }

    @Override
    public CrustalAbundanceValue crustalAbundance() {
        return CrustalAbundanceValue.TRACE;
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