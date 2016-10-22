import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by danelennon on 22/10/16.
 */
public class CardPanel extends JPanel {

    private Card card;
    public JButton button;

    public CardPanel(Card card,ImageIcon cardIcon) {
        this.card = card;
        setLayout(new BorderLayout());

        button = new JButton();
        add(button);
        cardIcon = getScaledImage(cardIcon,100,150);
        button.setIcon(cardIcon);

    }

    private ImageIcon getScaledImage(ImageIcon srcImg, int w, int h){
        Image image = srcImg.getImage(); // transform it
        Image newImg = image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        ImageIcon resizedImg = new ImageIcon(newImg);  // transform it back

        return resizedImg;
    }
}
