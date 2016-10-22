/**
 * Created by danelennon on 10/09/2016.
 */
public class TrumpCard extends Card{
    private TrumpType _trumpType;
    private String _title;
    private String _filename;

    public TrumpCard(TrumpType trumpType, String title, String filename) {
        this._trumpType = trumpType;
        this._title = title;
        this._filename = filename;
    }

    @Override
    String filename() {
        return _filename;
    }

    @Override
    String name() {
        return _title;
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
