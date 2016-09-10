/**
 * Created by danelennon on 10/09/2016.
 */
public class TrumpCard extends Card{
    private TrumpType _trumpType;
    private String _name;

    public TrumpCard(TrumpType trumpType, String name) {
        this._trumpType = trumpType;
        this._name = name;
    }

    @Override
    String name() {
        return _name;
    }

    @Override
    Range hardness() {
        return null;
    }

    @Override
    Range gravity() {
        return null;
    }

    @Override
    CleavageValue cleavage() {
        return null;
    }

    @Override
    CrustalAbundanceValue crustalAbundance() {
        return null;
    }

    @Override
    EconomicValue economicValue() {
        return null;
    }

    @Override
    TrumpType trumpType() {
        return _trumpType;
    }
}
