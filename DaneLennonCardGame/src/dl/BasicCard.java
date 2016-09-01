package dl;

/**
 * Created by xander on 19/08/2016.
 */
public class BasicCard implements Card {

    private String _cardType;
    private String _title;

    public BasicCard(String cardType, String title) {
        _cardType = cardType;
        _title = title;
    }

    @Override
    public String getTitle() {
        return _title;
    }

    @Override
    public String toString() {
        return "BasicCard{" +
                "title='" + _title +
                '}';
    }

}
