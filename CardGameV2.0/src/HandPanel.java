import javax.swing.*;
import java.awt.*;

/**
 * Created by danelennon on 22/10/16.
 */
public class HandPanel extends JPanel {

    private CardPanel[] cardPanels;

    public HandPanel(BasicDeck hand) {
        this.cardPanels = new CardPanel[hand.getNumCards()];

        setLayout(new GridLayout(1,0)); //an infinite number of columns needed

        //add the cardPanels to the handPanel
        for (int i = 0; i<hand.getNumCards();++i) {
            ImageIcon cardIcon = new ImageIcon("images/" + hand.getCard(i).filename());
            cardPanels[i] = new CardPanel(hand.getCard(i),cardIcon);
            add(cardPanels[i]);
        }
    }
}
