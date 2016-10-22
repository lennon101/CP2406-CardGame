/**
 * Created by danelennon on 9/09/2016.
 */
public class NormalCard extends Card {
    private String _filname;
    private TrumpType _trumpType;
    private String _name;
    private Range _hardness;
    private Range _specific_gravity;
    private CleavageValue _cleavage;
    private CrustalAbundanceValue _crustal_abundance;
    private EconomicValue _economic_value;

    public NormalCard(TrumpType _trumpType, String _name, Range _hardness, Range _specific_gravity, CleavageValue _cleavage, CrustalAbundanceValue _crustal_abundance, EconomicValue _economic_value, String filename) {
        this._trumpType = _trumpType;
        this._name = _name;
        this._hardness = _hardness;
        this._specific_gravity = _specific_gravity;
        this._cleavage = _cleavage;
        this._crustal_abundance = _crustal_abundance;
        this._economic_value = _economic_value;
        this._filname = filename;
    }

    @Override
    String filename() {
        return _filname;
    }

    @Override
    String name() {
        return _name;
    }

    @Override
    Range hardness() {
        return _hardness;
    }

    @Override
    Range gravity() {
        return _specific_gravity;
    }

    @Override
    CleavageValue cleavage() {
        return _cleavage;
    }

    @Override
    CrustalAbundanceValue crustalAbundance() {
        return _crustal_abundance;
    }

    @Override
    EconomicValue economicValue() {
        return _economic_value;
    }

    @Override
    TrumpType trumpType() {
        return _trumpType;
    }

}
