import javax.swing.*;
import java.awt.*;

/**
 * Created by danelennon on 21/10/16.
 */
public class GameView extends JFrame {

    private JLabel lastCardPlayedLabel = new JLabel();
    private JTextArea trumpText = new JTextArea("...");
    public JButton pickUpDeckButton = new JButton();
    private JTextArea logTextArea = new JTextArea();
    private JTextArea inputUserName = new JTextArea("player1");
    private JTextArea inputNumPlayers = new JTextArea("3");
    public JButton newGameButton = new JButton("New Game");

    private JPanel handPanelContainer;

    public HandPanel handPanel;
    private JPanel splashPanel;

    private final Color bg = new Color(206,149,92);

    public GameView() {

        JPanel lastCardPlayedPanel = new JPanel(new BorderLayout());
        lastCardPlayedPanel.setPreferredSize(new Dimension(100,100));
        JPanel pickUpDeckPanel = new JPanel(new BorderLayout());
        JPanel logPanel = new JPanel(new BorderLayout());
        JPanel userInputPanel = new JPanel(new GridLayout(3,2,5,5));
        handPanelContainer = new JPanel(new BorderLayout());


        lastCardPlayedPanel.setBackground(bg);
        pickUpDeckPanel.setBackground(bg);
        logPanel.setBackground(bg);
        userInputPanel.setBackground(bg);
        handPanelContainer.setBackground(bg);
        handPanelContainer.setPreferredSize(new Dimension(1200,100));

        setLayout(new GridBagLayout());

        JPanel upperPanel = new JPanel(new GridBagLayout());
        upperPanel.setBackground(bg);
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.7;
        add(upperPanel,c);
        c.gridy = 1;
        c.weighty = 0.1;
        add(userInputPanel,c);
        c.gridy = 3;
        c.weighty = 0.3;
        add(handPanelContainer,c);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.weightx = 0.3;
        upperPanel.add(lastCardPlayedPanel,c);
        c.gridx = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.2;
        upperPanel.add(pickUpDeckPanel,c);

        ImageIcon deckOfCardsIcon = new ImageIcon("images/deck-of-cards.jpg");
        deckOfCardsIcon = getScaledImage(deckOfCardsIcon,80,100);
        pickUpDeckButton.setIcon(deckOfCardsIcon);

        c.gridx = 3;
        c.gridwidth = 3;
        c.gridheight = 2;
        c.weightx = 1;
        c.ipadx = 20;
        upperPanel.add(logPanel,c);

        logTextArea.setEditable(false);
        logTextArea.setLineWrap(true);
        logTextArea.setWrapStyleWord(true);

        JScrollPane logScrollPane = new JScrollPane(logTextArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        logScrollPane.setPreferredSize(new Dimension(300,250));
        logPanel.add(logScrollPane,BorderLayout.CENTER);

        lastCardPlayedPanel.add(lastCardPlayedLabel,BorderLayout.CENTER);
        trumpText.setEditable(false);
        trumpText.setLineWrap(true);
        trumpText.setWrapStyleWord(true);
        //trumpText.setPreferredSize();
        lastCardPlayedPanel.add(trumpText,BorderLayout.SOUTH);
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
        lastCardPlayedLabel.setHorizontalAlignment(JLabel.CENTER);

        //create a fake deck for the splash screen
        splashPanel = new JPanel(new GridLayout(0,8,5,0));
        splashPanel.setBackground(bg);
        handPanelContainer.add(splashPanel);
        for (int i = 0; i<8;++i){
            JLabel label = new JLabel();
            splashImage = getScaledImage(splashImage,150,200);
            label.setIcon(splashImage);
            splashPanel.add(label);
        }

        repaint();
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
        if (handPanel != null){
            handPanelContainer.remove(handPanel);
        }

        //display the lastcard played and its trump category
        if (game.getLastCard() != null){
            ImageIcon lastCardPlayedImage = new ImageIcon("images/" + game.getLastCard().filename());

            lastCardPlayedImage = getScaledImage(lastCardPlayedImage,200,250);
            lastCardPlayedLabel.setIcon(lastCardPlayedImage);

            trumpText.setText("Category: " + game.getGameCategory().toString() +
                "\nTrump Value: " + game.getTrumpValue());
        }

        //display the players hand
        BasicDeck d = game.getHumanPlayer().get_playerDeck();
        handPanel = new HandPanel(d,bg);
        handPanel.revalidate();
        handPanelContainer.add(handPanel);
        handPanelContainer.revalidate();
        handPanel.repaint();

    }



}
