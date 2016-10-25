import javax.swing.*;
import java.awt.*;

/**
 * Created by danelennon on 21/10/16.
 */
public class GameFrame extends JFrame {

    private JLabel lastCardPlayedLabel = new JLabel();
    private JTextArea trumpText = new JTextArea("...");
    public JButton pickUpDeckButton = new JButton();
    private JTextArea logTextArea = new JTextArea();
    private JTextFieldWithPrompt inputUserName = new JTextFieldWithPrompt("Enter your name");
    private JTextFieldWithPrompt inputNumPlayers = new JTextFieldWithPrompt("Enter # players");

    public JButton newGameButton = new JButton("New Game");

    private JPanel handPanelContainer;

    public HandPanel handPanel;
    private JPanel splashPanel;

    private final Color bg = new Color(206,149,92);

    public GameFrame() {

        JPanel lastCardPlayedPanel = new JPanel(new BorderLayout());
        lastCardPlayedPanel.setPreferredSize(new Dimension(100,100));
        JPanel pickUpDeckPanel = new JPanel(new BorderLayout());
        JPanel logPanel = new JPanel(new BorderLayout());
        JPanel userInputPanel = new JPanel(new GridBagLayout());
        handPanelContainer = new JPanel(new BorderLayout());
        handPanelContainer.setPreferredSize(new Dimension(1200,100));

        lastCardPlayedPanel.setBackground(bg);
        pickUpDeckPanel.setBackground(bg);
        logPanel.setBackground(bg);
        userInputPanel.setBackground(bg);
        handPanelContainer.setBackground(bg);

        setLayout(new GridBagLayout());

        JPanel upperPanel = new JPanel(new GridBagLayout());
        upperPanel.setBackground(bg);

        logTextArea.setEditable(false);
        logTextArea.setLineWrap(true);
        logTextArea.setWrapStyleWord(true);

        trumpText.setEditable(false);
        trumpText.setLineWrap(true);
        trumpText.setWrapStyleWord(true);
        trumpText.setPreferredSize(new Dimension(200,50));

        ///////////////////////////
        ///Main Frame constraints
        //////////////////////////
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(10,10,10,10);

        //upper panel
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        add(upperPanel,c);

        //lower panel (hand panel container)
        c.gridy = 3;
        c.weighty = 0.3;
        add(handPanelContainer,c);

        ///////////////////////////
        ///Upper panel constraints
        //////////////////////////
        c.fill = GridBagConstraints.NONE;

        //last card played panel
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        upperPanel.add(lastCardPlayedPanel,c);

        //pick-up deck panel --> first row, second column
        c.gridx = 1;
        upperPanel.add(pickUpDeckPanel,c);

        //log panel --> first row, starting at 3 column, spanning 2 rows
        c.gridx = 2;
        c.gridwidth = 2;
        upperPanel.add(logPanel,c);

        //second row:
        c.weighty = 0.1;

        //Category label --> second row, first column, below the last card played label
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0.3;
        c.fill = GridBagConstraints.NONE;
        upperPanel.add(trumpText,c);

        //pick-up deck label --> second row, second column, below the pick-up deck image
        c.weightx = 0;
        c.gridx = 1;
        upperPanel.add(new JLabel("Pick up deck"),c);

        //new game button --> second row, third column
        c.gridx = 2;
        c.fill = GridBagConstraints.BOTH;
        upperPanel.add(newGameButton,c);

        //user name --> second row, forth column
        c.gridx = 3;
        upperPanel.add(inputUserName,c);

        //blank space for now to fill gap where trump value label should go
        c.gridx = 0;
        c.gridy = 2;
        upperPanel.add(new JLabel("blank"),c);

        //another blank label to fill gap underneath pick-up deck label
        c.gridx = 1;
        upperPanel.add(new JLabel("blank"),c);

        //Instructions button --> third row, third column
        c.gridx = 2;
        upperPanel.add(new JButton("Instructions"),c);

        //number of players input --> third row, forth column
        c.gridx = 3;
        c.gridy = 2;
        upperPanel.add(inputNumPlayers,c);


        //////////////////////
        ///Log panel attributes
        ///////////////////////

        JScrollPane logScrollPane = new JScrollPane(logTextArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        logScrollPane.setPreferredSize(new Dimension(300,250));
        logPanel.add(logScrollPane,BorderLayout.CENTER);

        //////////////////////


        lastCardPlayedPanel.add(lastCardPlayedLabel,BorderLayout.CENTER);

        ImageIcon deckOfCardsIcon = new ImageIcon("images/deck-of-cards.jpg");
        deckOfCardsIcon = getScaledImage(deckOfCardsIcon,80,100);
        pickUpDeckButton.setIcon(deckOfCardsIcon);
        pickUpDeckPanel.add(pickUpDeckButton);


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

    public void updateCardsView(Game game){
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
        handPanelContainer.add(handPanel);
        handPanelContainer.revalidate();
        handPanel.repaint();

    }



}
