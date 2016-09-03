/**
 * Created by danelennon on 20/08/2016.
 */

package dl;

import java.util.HashMap;
import java.util.Vector;

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
                "Title = " + _title + "; " +
                "Hardness = " + _hardness + "; " +
                "Specific Gravity = " + _specific_gravity + "; " +
                "Clevage = " + _cleavage + "; " +
                "Crustal Abundance = " + _crustal_abundance + "; " +
                "Economic Value = " + _economic_value + "; " +
                "}";
    }

    public String get_title() {
        return _title;
    }

    public String get_chemistry() {
        return _chemistry;
    }

    public String get_classification() {
        return _classification;
    }

    public String get_crystal_system() {
        return _crystal_system;
    }

    public String[] get_occurrence() {
        return _occurrence;
    }

    public String get_hardness() {
        return _hardness;
    }

    public String get_specific_gravity() {
        return _specific_gravity;
    }

    public String get_cleavage() {
        return _cleavage;
    }

    public String get_crustal_abundance() {
        return _crustal_abundance;
    }

    public String get_economic_value() {
        return _economic_value;
    }

    public HashMap getDictOfTrumpCategories() {
        HashMap trumpCategories = new HashMap();
        trumpCategories.put("hardness",this._hardness);
        trumpCategories.put("specific_gravity",this._specific_gravity);
        trumpCategories.put("cleavage",this._cleavage);
        trumpCategories.put("crustal_abundance",this._crustal_abundance);
        trumpCategories.put("economic_value",this._economic_value);
        return trumpCategories;
    }
}
