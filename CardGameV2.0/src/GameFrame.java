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
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(10,10,10,10);
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


        //////////////////////////
        ///Set up user input panel
        //////////////////////////

        //fake label to fill space
        GridBagConstraints cNew = new GridBagConstraints();
        cNew.weightx = 1;
        cNew.fill = GridBagConstraints.HORIZONTAL;
        cNew.gridx = 0;     //first row
        cNew.gridy = 0;     //first column
        cNew.gridwidth = 2; //2 columns wide
        userInputPanel.add(new JLabel(""), cNew);

        //fake label to fill space
        cNew.fill = GridBagConstraints.HORIZONTAL;
        cNew.weightx = 1;
        cNew.gridx = 1;
        cNew.gridy = 0;
        userInputPanel.add(new JLabel(""), cNew);

        //New Game button
        cNew.fill = GridBagConstraints.HORIZONTAL;
        cNew.weightx = 0.5;
        cNew.gridx = 1;
        cNew.gridy = 1;
        userInputPanel.add(newGameButton, cNew);


        cNew.fill = GridBagConstraints.BOTH;
        cNew.ipady = 0;       //reset to default
        cNew.weighty = 1.0;   //request any extra vertical space
        cNew.weightx = 0.3;
        cNew.insets = new Insets(5,0,0,0);  //top padding
        cNew.gridx = 2;       //aligned with button 2
        cNew.gridwidth = 2;   //2 columns wide
        cNew.gridy = 1;       //second row
        userInputPanel.add(inputUserName, cNew);

        cNew.gridy = 2;        //third row
        userInputPanel.add(inputNumPlayers, cNew);
        //userInputPanel.add(newGameButton);

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
