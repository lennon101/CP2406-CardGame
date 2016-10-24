import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

/**
 * Created by danelennon on 22/10/16.
 */
public class HandPanel extends JPanel {

    public CardPanel[] cardPanels;

    public HandPanel(BasicDeck hand,Color bg) {
        this.cardPanels = new CardPanel[hand.getNumCards()];

        setLayout(new GridLayout(0,8,5,5)); //an infinite number of columns needed
        setPreferredSize(new Dimension(1000,200));
        setBackground(bg);

        //add the cardPanels to the handPanel
        for (int i = 0; i<hand.getNumCards();++i) {
            ImageIcon cardIcon = new ImageIcon("images/" + hand.getCard(i).filename());
            cardPanels[i] = new CardPanel(hand.getCard(i),cardIcon);
            cardPanels[i].setBackground(bg);
            add(cardPanels[i]);
        }
    }

    public void addPanelMouseListener(MouseAdapter mouseAdapter) {
        for (JPanel panel : cardPanels) {
            panel.addMouseListener(mouseAdapter);
        }
    }
}
