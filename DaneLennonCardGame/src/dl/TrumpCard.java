package dl;

/**
 * Created by danelennon on 1/09/2016.
 */
public class TrumpCard implements Card {
    private String _title;
    private String _subTitle;

    public TrumpCard(String _title, String _subTitle) {
        this._title = _title;
        this._subTitle = _subTitle;
    }

    @Override
    public String getTitle() {
        return _title;
    }

    @Override
    public String toString() {
        return "TrumpCard{" +
                "title = " + _title + "; " +
                "subtitle = " + _subTitle + "}";
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_subTitle() {
        return _subTitle;
    }

    public void set_subTitle(String _subTitle) {
        this._subTitle = _subTitle;
    }
}
