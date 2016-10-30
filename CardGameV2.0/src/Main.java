import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

/**
 * Created by danelennon on 9/09/2016.
 */
public class Main {
    private static final String CARD_XML_FILE = "MstCards_151021.plist";
    private static int numPlayers;
    private static String humanName;
    private static Game g;
    private static GameState gameState;

    public static void main(String[] args) {

        //create a gameFrame
        GameFrame gameFrame = new GameFrame();

        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(1400,850);
        gameFrame.setVisible(true);
        gameFrame.getContentPane().setBackground(new Color(206,149,92));

        gameFrame.log("Welcome to Ultimate Super Trump\n" +
                "Please enter your name and the number of players:");

        gameFrame.newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (numberIsValidForRange(gameFrame.getNumPlayers(), 3, 5)) {
                    humanName = gameFrame.getHumanName();
                    numPlayers = gameFrame.getNumPlayers();

                    //setup game controller
                    g = startNewGame(gameFrame);

                    //addMouseListeners(gameFrame);
                    setUpRound(gameFrame);
                    if (gameState.equals(GameState.PLAY)) {
                        playRound(gameFrame); //playRound breaks loop when it gets to human
                    }
                } else {
                    JOptionPane.showMessageDialog(gameFrame, "must enter a valid number between 3-5");
                }
            }

        });

        gameFrame.instructButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InstructionsFrame instructionsFrame = new InstructionsFrame();
            }
        });

        gameFrame.pickUpDeckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    public void run() {
                        if (gameState.equals(GameState.SETUP)){
                            JOptionPane.showMessageDialog(null, "You are the first player of this round and must play the first card");
                        }else if (gameState.equals(GameState.AFTER_TRUMP)){
                            JOptionPane.showMessageDialog(null, "You must play a card after your trump card");
                        }else {
                            gameFrame.log("You have chosen to pass this round :( ");
                            gameFrame.log("Picking up a cardPanel from the pick-up deck...");
                            g.pickUp(g.getHumanPlayer());
                            g.getHumanPlayer().passed(true);
                            gameFrame.updateCardsView(g);
                            addMouseListeners(gameFrame);
                            playRound(gameFrame);
                        }
                    }
                }.start();
            }
        });
    }

    private static void addMouseListeners(GameFrame gf) {
        gf.handPanel.addPanelMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Thread() {
                    public void run() {
                        Card selectedCard = ((CardPanel) e.getSource()).getCard();

                        gf.log("You have selected card " + selectedCard.name() + "\n");

                        if (gameState.equals(GameState.SETUP)){
                            if (!selectedCard.isTrump()){
                                GameCategory gc = getGameCategoryFromHuman(gf);
                                playFirstCard(g,gf,selectedCard,gc);
                                gameState = GameState.PLAY;
                                playRound(gf); //playRound breaks loop when it gets to human
                            }else {
                                gf.log("You must select a card other than a trump to start the round");
                            }
                        }else if (gameState.equals(GameState.PLAY)){
                            playCard(selectedCard,gf);
                        }else if (gameState.equals(GameState.AFTER_TRUMP)){
                            if (selectedCard.isTrump()){
                                gf.log("You must select a card other than a trump after playing a trump");
                            }else {
                                GameCategory gc;
                                switch (g.getLastCard().trumpType()) {
                                    case ANY:
                                        System.out.println("-- choosing trump category");
                                        gc = getGameCategoryFromHuman(gf);
                                        System.out.println("-- you chose: " + gc);
                                        break;
                                    default:
                                        gc = g.getGameCategoryFromTrumpCategory(g.getLastCard().trumpType());
                                        break;
                                }
                                playFirstCard(g, gf, selectedCard, gc);
                                gameState = GameState.PLAY;
                                playRound(gf); //playRound breaks loop when it gets to human
                            }
                        }

                    }
                }.start();
            }
        });
    }

    private static GameCategory getGameCategoryFromHuman(GameFrame gf){
        return (GameCategory) JOptionPane.showInputDialog(gf,
                "Select a trump category for this round:",
                "Trump Category",
                JOptionPane.QUESTION_MESSAGE,
                null,
                GameCategory.values(),
                GameCategory.values()[0]);
    }

    private static void playFirstCard(Game g, GameFrame gf, Card c, GameCategory gc) {
        g.getHumanPlayer().playFirstCard(g,c,gc,gf);
        addMouseListeners(gf);
        gf.log("The trump category for this round is: " + g.getGameCategory());
        gf.log("And the top value of this category is: " + g.getTrumpValue());

        g.incrementPlayer();
    }

    private static void playCard(Card c, GameFrame gf){
        Player human = g.getHumanPlayer();
        if (human.hasCombo()) {
            System.out.println("You have the The Geophysicist and the Magnesite cards\n" +
                    "Would you like to play them both and win this round?: (y/n)");
            if (getYesNoChoice() == 'y') {
                human.playCombo(g,gf);
            }
        }

        if (c.isTrump()) {
            gf.log("You selected a trump card!");
            human.playCard(g, c, gf);
            gf.updateCardsView(g);
            addMouseListeners(gf);

            gf.log(human.getName() + " placed the " + c.name() + " with a trump category of: " + c.trumpType());
            gf.log("Select another card to play after your trump card");
            gameState = GameState.AFTER_TRUMP;


        } else if (!g.cardCanBePlayed(c)) {
            gf.log("This cards trump value isn't higher enough\n" +
                    "Try again...");
        } else {
            human.playCard(g, c, gf);
            playRound(gf);
            if (gameState.equals(GameState.AFTER_TRUMP)){
                //do nothing
            }else {
                addMouseListeners(gf);
            }
        }
    }


    private static void playRound(GameFrame gf) {
        while (!g.roundComplete()){
            Player p = g.getNextAvailablePlayer();

            gf.log("There are " + g.numPlayersLeftInRound() + " players left in this round " +
                    "and " + g.getNumPlayersPassed() + " have passed.");
            g.displayAllPlayers();
            if (p.isHuman()){
                notifyHumanToPlay(gf);
                break;
            }else{
                System.out.println("AI round commencing...");
                g.aIRound(p,gf);
            }
        }
        closeTheRound(gf);
    }

    private static void closeTheRound(GameFrame gf) {
        gf.updateCardsView(g);
        addMouseListeners(gf);
        if (g.withDrawing()){
            System.out.println("Game closing");
        }else{
            Player roundWinner = g.getRoundWinner();
            if (roundWinner != null){
                gf.log(roundWinner.getName() + " has won this round!" +
                        "\n\n===== New round commencing =====\n\n");

                g.newRound();
                if (roundWinner.getNumCards() > 0 ){
                    gf.log(roundWinner.getName() + " leading out the new round.");
                    if (roundWinner.isHuman()){
                        gameState = GameState.SETUP;
                    }else {
                        g.setUpAiRound(roundWinner,gf);
                        addMouseListeners(gf);
                        playRound(gf);
                    }
                } else {
                    gf.log(roundWinner.getName() + " has won the game! \n");
                    JOptionPane.showMessageDialog(null, roundWinner.getName() + " won the game and is now leaving. ");
                    g.removePlayer(roundWinner);

                    Player p = g.getNextAvailablePlayer();
                    gf.log(p.getName() + " leading out the new round.");

                    gf.log("There are " + g.numPlayersLeftInRound() + " players left in this round\n" +
                            "and " + g.getNumPlayersPassed() + " have passed.");
                    g.displayAllPlayers();
                    if (p.isHuman()){
                        gf.log("its your turn now!");
                        gameState = GameState.SETUP;
                    }else{
                        g.setUpAiRound(p,gf);
                        addMouseListeners(gf);
                        playRound(gf);
                    }
                }



            }
        }
    }

    private static void setUpRound(GameFrame gv) {
        Player firstPlayer = g.getNextAvailablePlayer();

        g.newRound();
        gv.log(firstPlayer.getName() + " is the next player\n");
        if (firstPlayer.isHuman()){
            gameState = GameState.SETUP;
            gv.log("Select a card from your hand to play the first card of the round.");

        }else{
            //dumbAI: choose a cardPanel at random and set the game category at random
            gameState = GameState.PLAY;
            g.setUpAiRound(firstPlayer,gv);
            addMouseListeners(gv);
        }
    }

    private static void notifyHumanToPlay(GameFrame gv) {

        gv.log("Your turn to select and play a card.\n");

        if (g.getHumanPlayer().canPlay(g)){
            gv.log("Options:\n" +
                    "-- Pass -> pick up a card from the pickup deck \n" +
                    "-- Play a card -> Select a card from your hand \n");
        }else{
            gv.log("You don't have any cards high enough to play and therefore MUST pass.\n" +
                    "Options:\n" +
                    "-- Pass -> pick up a card from the pickup deck \n");
        }
    }


    private static Game startNewGame(GameFrame gv) {
        String xmlFile = System.getProperty("user.dir")  + "/" + CARD_XML_FILE;
        Deck deck = new XMLDeckBuilder(xmlFile).deck();

        Game game = new Game(humanName,numPlayers,deck);

        game.displayAllPlayers(gv);

        gv.log("\nThere are " + game.getDeckSize() + " cards in the pick-up deck.");

        Player dealer = game.getDealer();
        gv.log(dealer.getName() + " is the Dealer");

        if (dealer.isHuman()) {
            while (!forcedYes()){
                gv.log("Your friends are getting board, shuffle and deal the cards now? (y/n): ");
            }
        }
        game.shuffleDeck();
        gv.clearLog();
        gv.log(dealer.getName() + " is dealing the cards...");
        game.dealCardsToPlayers();

        gv.updateCardsView(game);
        addMouseListeners(gv);
        gv.log("The pick-up deck now has " + game.getDeckSize() + " cards.\n");

        return game;
    }

    private static boolean forcedYes() {
        if (JOptionPane.showConfirmDialog(null, "deal now?", "WARNING",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean numberIsValidForRange(int i, int lowerInclusive, int upperInclusive) {
        if (i >= lowerInclusive && i <= upperInclusive){
            return true;
        }else{
            return false;
        }
    }


    private static char getYesNoChoice() {
        boolean valid = false;
        char answer = 'x';
        while (!valid){
            if (answer != 'n' && answer != 'y'){
                System.out.println("invalid choice, enter \"y\" or \"n\"");
            }else {
                valid = true;
            }
        }
        return answer;
    }


    public static int getNumInRange(int start, int stop) {
        Scanner input = new Scanner(System.in);
        boolean valid = false;
        int index = -1;
        String indexStr = "";
        while (!valid){
            indexStr = input.next();
            try{
                index = Integer.parseInt(indexStr);
            } catch (Throwable t){
                System.out.println("Not a valid number.");
            }
            if (index<start || index>stop){
                System.out.println("This is outside the valid range");
                System.out.print("Try again: ");
            }else {
                valid = true;
            }
        }
        return index;
    }


}
