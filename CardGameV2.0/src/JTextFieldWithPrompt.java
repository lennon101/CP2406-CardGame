import javax.swing.*;
import java.awt.*;

/**
 * Created by danelennon on 26/10/16.
 */
public class JTextFieldWithPrompt extends JTextField {

    private String prompt;

    public JTextFieldWithPrompt(String prompt) {
        this.prompt = prompt;
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        if(getText().isEmpty() && ! (FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)){
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setBackground(Color.gray);
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            g2.drawString(prompt, 10, getHeight()/2); //figure out x, y from font's FontMetrics and size of component.
            g2.dispose();
        }
    }
}
