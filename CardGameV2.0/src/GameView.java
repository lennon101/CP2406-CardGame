import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by danelennon on 21/10/16.
 */
public class GameView extends JFrame {

    private JLabel lastCardPlayedLabel = new JLabel();
    public JButton pickUpDeckButton = new JButton("the pick up deck");
    private JTextArea logTextArea = new JTextArea();
    private JTextArea inputUserName = new JTextArea("player1");
    private JTextArea inputNumPlayers = new JTextArea("3");
    public JButton newGameButton = new JButton("New Game");

    private JPanel handPanelContainer;

    private HandPanel handPanel;
    private JPanel splashPanel;

    public GameView() {
        JPanel lastCardPlayedPanel = new JPanel();
        JPanel pickUpDeckPanel = new JPanel(new BorderLayout());
        JPanel logPanel = new JPanel(new BorderLayout());
        JPanel userInputPanel = new JPanel(new GridLayout(3,2));
        handPanelContainer = new JPanel(new BorderLayout());

        setLayout(new GridBagLayout());

        JPanel upperPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        add(upperPanel,c);
        c.gridy = 1;
        c.weighty = 0.2;
        add(userInputPanel,c);
        c.gridy = 3;
        c.weighty = 1;
        add(handPanelContainer,c);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 1;
        upperPanel.add(lastCardPlayedPanel,c);
        c.gridx = 2;
        c.gridwidth = 1;
        c.weightx = 0.5;
        upperPanel.add(pickUpDeckPanel,c);

        c.gridx = 3;
        c.gridwidth = 2;
        c.weightx = 1;
        upperPanel.add(logPanel,c);

        logTextArea.setEditable(false);
        logTextArea.setLineWrap(true);
        logTextArea.setWrapStyleWord(true);

        JScrollPane logScrollPane = new JScrollPane(logTextArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        logScrollPane.setPreferredSize(new Dimension(300,250));
        logPanel.add(logScrollPane,BorderLayout.CENTER);

        lastCardPlayedPanel.add(lastCardPlayedLabel);
        pickUpDeckPanel.add(pickUpDeckButton);

        userInputPanel.add(new JLabel("Player name: ", SwingConstants.RIGHT));
        userInputPanel.add(inputUserName);
        userInputPanel.add(new JLabel("Num players: ",SwingConstants.RIGHT));
        userInputPanel.add(inputNumPlayers);
        userInputPanel.add(new JLabel(""));
        userInputPanel.add(newGameButton);

        ImageIcon splashImage = new ImageIcon("images/Slide65.jpg");
        splashImage = getScaledImage(splashImage,250,300);
        lastCardPlayedLabel.setIcon(splashImage);

        //create a fake deck for the splash screen
        splashPanel = new JPanel(new GridLayout(1,0));
        handPanelContainer.add(splashPanel);
        for (int i = 0; i<8;++i){
            JButton button = new JButton();
            splashImage = getScaledImage(splashImage,100,150);
            button.setIcon(splashImage);
            splashPanel.add(button);
        }

        pickUpDeckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handPanel.add(new JButton("New Button"));
                pack();
            }
        });
    }

    private ImageIcon getScaledImage(ImageIcon srcImg, int w, int h){
        Image image = srcImg.getImage(); // transform it
        Image newImg = image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        ImageIcon resizedImg = new ImageIcon(newImg);  // transform it back

        return resizedImg;
    }

    public void log(String logText){
        logTextArea.append(logText + "\n");
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
    }

    public int getNumPlayers(){
        try {
            int numPlayers = Integer.parseInt(inputNumPlayers.getText());
            return numPlayers;
        }catch (Throwable t){
            return 0;
        }
    }

    public String getHumanName() {
        return inputUserName.getText();
    }

    public void displayCards(Game game){
        handPanelContainer.remove(splashPanel);

        //display the lastcard played
        if (game.getLastCard() != null){
            ImageIcon lastCardPlayedImage = new ImageIcon("images/" + game.getLastCard().filename());

            lastCardPlayedImage = getScaledImage(lastCardPlayedImage,lastCardPlayedLabel.getWidth(),lastCardPlayedLabel.getHeight());
            lastCardPlayedLabel.setIcon(lastCardPlayedImage);
        }

        //display the players hand
        BasicDeck d = game.getHumanPlayer().get_playerDeck();
        handPanel = new HandPanel(d);
        handPanel.revalidate();
        handPanelContainer.add(handPanel);
        handPanelContainer.revalidate();

    }



}
