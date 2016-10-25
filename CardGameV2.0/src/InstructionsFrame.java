import javax.swing.*;
import java.awt.*;

/**
 * Created by danelennon on 25/10/16.
 */
public class InstructionsFrame extends JFrame{

    private final Color bg = new Color(206,149,92);

    private String intstructionsString = "How to play:\n\n" +
            "1. A dealer (randomly chosen) shuffles the cards and deals each player 8 cards. Each player can look at their cards, but should not show them to other players. The remaining card pack is placed face down on the table.\n\n" +
            "2. The player to the left of the dealer goes first by placing a mineral card on the table. The player must state the mineral name, one of the five trump categories (i.e., either Hardness, Specific Gravity, Cleavage, Crustal Abundance, or Economic Value), and the top value of that category. For example, a player placing the Glaucophane card may state “Glaucophane, Specific Gravity, 3.2”\n\n" +
            "3. The player next to the left takes the next turn. This player must play a mineral card that has a higher value in the trump category than the card played by the previous player. In the case of the example of the Glaucophane card above, the player must place a card that has a value for specific gravity above 3.2. The game continues with the next player to the left, and so on.\n\n" +
            "4. If a player does not have any mineral cards that are of higher value for the specific trump category being played, then the player must pass and pick up one card from the card pack on the table. The player then cannot play again until all but one player has passed, or until another player throws a supertrump card to change the trump category, as described below. A player is allowed to pass even if they still hold cards that could be played.\n\n" +
            "5. If the player has a supertrump card (The Miner, The Geologist, The Geophysicist, The Petrologist, The Mineralogist, The Gemmologist) they may play this card at any of their turns. By placing a supertrump card, the player changes the trump category according to the instructions on the supertrump card. The player then plays a mineral card of their choice to resume play. At this stage, any other player who had passed on the previous round is now able to play again. If a player happens to hold both The Geophysicist card and the Magnetite card in their hand, then that player can place both cards together to win the round.\n\n" +
            "6. The game continues with players taking turns to play cards until all but one player has passed. The last player then gets to lead out the next round and chooses the trump category to be played.\n\n" +
            "7. The winner of the game is the first player to lose all of their cards. The game continues until all but one player (i.e., the loser) has lost their cards.\n\n";

    public InstructionsFrame() throws HeadlessException {
        setVisible(true);
        getContentPane().setBackground(bg);
        setSize(new Dimension(500,500));

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JTextArea instructionsTextArea = new JTextArea(intstructionsString);
        instructionsTextArea.setWrapStyleWord(true);
        instructionsTextArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(instructionsTextArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(400,400));

        add(scrollPane,c);


    }
}
