/**
 * Created by danelennon on 20/08/2016.
 */

package dl;

public class PlayCard implements Card {
    private String _title;
    private String _chemistry;
    private String _classification;
    private String _crystal_system;
    private String[] _occurrence;
    private String _hardness;
    private String _specific_gravity;
    private String _cleavage;
    private String _crustal_abundance;
    private String _economic_value;

    public PlayCard(String _title, String _chemistry, String _classification, String _crystal_system, String[] _occurrence, String _hardness, String _specific_gravity, String _cleavage, String _crustal_abundance, String _economic_value) {

        this._title = _title;
        this._chemistry = _chemistry;
        this._classification = _classification;
        this._crystal_system = _crystal_system;
        this._occurrence = _occurrence;
        this._hardness = _hardness;
        this._specific_gravity = _specific_gravity;
        this._cleavage = _cleavage;
        this._crustal_abundance = _crustal_abundance;
        this._economic_value = _economic_value;
    }

    @Override
    public String getTitle() {
        return _title;
    }

    @Override
    public String toString() {
        return "PlayCard{" +
                "title = " + _title + "; " +
                "chemistry = " + _chemistry + "}";
    }

    public String get_chemistry() {
        return _chemistry;
    }

    public void set_chemistry(String _chemistry) {
        this._chemistry = _chemistry;
    }

    public String get_classification() {
        return _classification;
    }

    public void set_classification(String _classification) {
        this._classification = _classification;
    }

    public String get_crystal_system() {
        return _crystal_system;
    }

    public void set_crystal_system(String _crystal_system) {
        this._crystal_system = _crystal_system;
    }

    public String[] get_occurrence() {
        return _occurrence;
    }

    public void set_occurrence(String[] _occurrence) {
        this._occurrence = _occurrence;
    }

    public String get_hardness() {
        return _hardness;
    }

    public void set_hardness(String _hardness) {
        this._hardness = _hardness;
    }

    public String get_specific_gravity() {
        return _specific_gravity;
    }

    public void set_specific_gravity(String _specific_gravity) {
        this._specific_gravity = _specific_gravity;
    }

    public String get_cleavage() {
        return _cleavage;
    }

    public void set_cleavage(String _cleavage) {
        this._cleavage = _cleavage;
    }

    public String get_crustal_abundance() {
        return _crustal_abundance;
    }

    public void set_crustal_abundance(String _crustal_abundance) {
        this._crustal_abundance = _crustal_abundance;
    }

    public String get_economic_value() {
        return _economic_value;
    }

    public void set_economic_value(String _economic_value) {
        this._economic_value = _economic_value;
    }
}
