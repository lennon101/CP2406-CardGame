import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by danelennon on 21/10/16.
 */
public class GameView extends JFrame {

    private JLabel lastCardPlayedLabel = new JLabel("last card played label");
    private JButton pickUpDeckButton = new JButton("the pick up deck");
    private JTextArea logTextArea = new JTextArea();
    private JTextArea inputUserName = new JTextArea("player1");
    private JTextArea inputNumPlayers = new JTextArea("3");
    public JButton newGameButton = new JButton("New Game");

    public GameView() {
        JPanel lastCardPlayedPanel = new JPanel();
        JPanel pickUpDeckPanel = new JPanel(new BorderLayout());
        JPanel logPanel = new JPanel(new BorderLayout());
        JPanel userInputPanel = new JPanel(new GridLayout(3,2));
        JPanel playerHandPanel = new JPanel(new GridLayout(1,0));

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
        add(playerHandPanel,c);

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

        JScrollPane logScrollPane = new JScrollPane(logTextArea);
        logPanel.add(logScrollPane,BorderLayout.CENTER);


        // TODO: 21/10/16 remove test card image
        //ImageIcon image = new ImageIcon("images/Slide01.jpg");
        //image = getScaledImage(image,120,120);

        logTextArea.setEditable(false);
        logTextArea.setWrapStyleWord(true);
        logTextArea.setPreferredSize(new Dimension(300,250));
        //last.setIcon(image);

        lastCardPlayedPanel.add(lastCardPlayedLabel);
        pickUpDeckPanel.add(pickUpDeckButton);

        userInputPanel.add(new JLabel("Player name: ", SwingConstants.RIGHT));
        userInputPanel.add(inputUserName);
        userInputPanel.add(new JLabel("Num players: ",SwingConstants.RIGHT));
        userInputPanel.add(inputNumPlayers);
        userInputPanel.add(new JLabel(""));
        userInputPanel.add(newGameButton);

        JPanel cardPanel = new JPanel(new BorderLayout());

        playerHandPanel.add(cardPanel);
        playerHandPanel.add(new JButton("Button 2"));
        playerHandPanel.add(new JButton("Button 3"));
        playerHandPanel.add(new JButton("Button 4"));
        playerHandPanel.add(new JButton("Button 5"));
        playerHandPanel.add(new JButton("Button 6"));
        playerHandPanel.add(new JButton("Button 7"));
        playerHandPanel.add(new JButton("Button 8"));

        cardPanel.add(new JButton("Button 1"));

        pickUpDeckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerHandPanel.add(new JButton("New Button"));
                pack();
            }
        });

    }

    // TODO: 21/10/16 move this to gameView controller perhaps?
    private ImageIcon getScaledImage(ImageIcon srcImg, int w, int h){
        Image image = srcImg.getImage(); // transform it
        Image newImg = image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        ImageIcon resizedImg = new ImageIcon(newImg);  // transform it back

        return resizedImg;
    }

    public void log(String logText){
        logTextArea.append(logText + "\n");
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
}
