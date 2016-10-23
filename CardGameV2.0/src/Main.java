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

        //create a gameView
        GameView gameView = new GameView();

        gameView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameView.setSize(1000,800);
        gameView.setPreferredSize(new Dimension(1000,800));
        gameView.setVisible(true);
        gameView.getContentPane().setBackground(Color.GRAY);

        gameView.log("Welcome to Ultimate Super Trump\n" +
                "Please enter your name and the number of players:");

        gameView.newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameView.getNumPlayers() != 0 && numberIsValidForRange(gameView.getNumPlayers(),3,5)){
                    humanName = gameView.getHumanName();
                    numPlayers = gameView.getNumPlayers();

                    //setup game controller
                    g = startNewGame(gameView);

                    addMouseListeners(gameView);
                    setUpRound(gameView);
                    if (gameState.equals(GameState.PLAY)){
                        playRound(gameView); //playRound breaks loop when it gets to human
                        humanRound(gameView);
                    }
                }else {
                    JOptionPane.showMessageDialog(gameView, "must enter a valid number between 3-5");
                }
            }
        });

        gameView.pickUpDeckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameView.log("You have chosen to pass this round :( ");
                gameView.log("Picking up a cardPanel from the pick-up deck...");
                g.pickUp(g.getHumanPlayer());
                g.getHumanPlayer().passed(true);
                gameView.displayCards(g);
                playRound(gameView);
            }
        });

        /*
        while (!game.complete()){
            Player playerToRemove=null;

            for (Player p:game.getPlayers()){
                if (p.getNumCards() <=0){
                    System.out.println("\n\n=====" + p.getName() + " has won game " + game.get_gameNumber() + "=====");
                    game.incrementGameNumber();
                    playerToRemove = p;
                    game.unPassAllPlayers();
                    break;
                }
            }
            try {
                game.removePlayer(playerToRemove);
            }catch (Throwable t){}

            if (game.complete()){
                break;
            }

            System.out.println("\n\n===== New round commencing =====\n\n");
            playRound(game);
        }

        if (!game.withDrawing()){
            System.out.println(game.getNextAvailablePlayer().getName() + " is the last player left.\n" +
                    "and is the loser of the game.\n\n" +
                    "Game complete!");
        }*/

    }



    private static void addMouseListeners(GameView gv) {
        gv.handPanel.addPanelMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Card selectedCard = ((CardPanel) e.getSource()).getCard();

                gv.log("You have selected card " + selectedCard.name() + "\n");

                if (gameState.equals(GameState.SETUP)){
                    if (!selectedCard.isTrump()){
                        playFirstCard(g,gv,selectedCard,getGameCategoryFromHuman(gv));
                        gameState = GameState.PLAY;
                        playRound(gv); //playRound breaks loop when it gets to human
                        humanRound(gv);
                    }else {
                        gv.log("You must select a card other than a trump to start the round");
                    }
                }else if (gameState.equals(GameState.PLAY)){
                    playCard(selectedCard,gv);
                }else if (gameState.equals(GameState.AFTER_TRUMP)){
                    GameCategory gc;
                    switch (g.getLastCard().trumpType()) {
                        case ANY:
                            System.out.println("-- choosing trump category");
                            gc = getGameCategoryFromHuman(gv);
                            System.out.println("-- you chose: " + gc);
                            break;
                        default:
                            gc = g.getGameCategoryFromTrumpCategory(g.getLastCard().trumpType());
                            break;
                    }
                    playFirstCard(g,gv,selectedCard,gc);
                    gameState = GameState.PLAY;
                    g.incrementPlayer();
                    playRound(gv); //playRound breaks loop when it gets to human
                    humanRound(gv);

                }
            }
        });
    }

    private static GameCategory getGameCategoryFromHuman(GameView gv){
        return (GameCategory) JOptionPane.showInputDialog(gv,
                "Select a trump category for this round:",
                "Trump Category",
                JOptionPane.QUESTION_MESSAGE,
                null,
                GameCategory.values(),
                GameCategory.values()[0]);
    }

    private static void playFirstCard(Game g, GameView gv, Card c,GameCategory gc) {
        g.getHumanPlayer().playFirstCard(g,c,gc,gv);
        gv.log("The trump category for this round is: " + g.getGameCategory());
        gv.log("And the top value of this category is: " + g.getTrumpValue());

        g.incrementPlayer();
    }

    private static void playCard(Card c, GameView gv){
        Player human = g.getHumanPlayer();
        if (human.hasCombo()) {
            System.out.println("You have the The Geophysicist and the Magnesite cards\n" +
                    "Would you like to play them both and win this round?: (y/n)");
            if (getYesNoChoice() == 'y') {
                human.playCombo(g,gv);
            }
        }

        if (c.isTrump()) {
            gv.log("You selected a trump card!");
            human.playCard(g, c, gv);
            addMouseListeners(gv);

            gv.log(human.getName() + " placed the " + c.name() + " with a trump category of: " + c.trumpType());
            gv.log("Select another card to play after your trump card");
            gameState = GameState.AFTER_TRUMP;

        } else if (!g.cardCanBePlayed(c)) {
            gv.log("This cards trump value isn't higher enough\n" +
                    "Try again...");
        } else {
            human.playCard(g, c, gv);
            playRound(gv);
            addMouseListeners(gv);
        }
    }


    private static void playRound(GameView gv) {
        while (!g.roundComplete()){
            Player p = g.getNextAvailablePlayer();

            gv.log("There are " + g.numPlayersLeftInRound() + " players left in this round\n" +
            "and " + g.getNumPlayersPassed() + " have passed.");
            g.displayAllPlayers();
            if (p.isHuman()){
                gv.log("its your turn now!");
                addMouseListeners(gv);
                break;
            }else{
                System.out.println("AI round commencing...");
                g.aIRound(p,gv);
            }
        }
        if (g.withDrawing()){
            System.out.println("Game closing");
        }else{
            Player roundWinner = g.getRoundWinner();
            if (roundWinner != null){
                gv.log(roundWinner.getName() + " has won this round!" +
                        "\n\n===== New round commencing =====\n\n" +
                        roundWinner.getName() + " leading out the new round.");
                g.newRound();
                if (roundWinner.isHuman()){
                    gameState = GameState.SETUP;
                    addMouseListeners(gv);
                }else{
                    g.setUpAiRound(roundWinner,gv);
                    playRound(gv);
                }

            }
        }
    }

    private static void setUpRound(GameView gv) {
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
        }
    }

    private static void humanRound(GameView gv) {
        System.out.println("Your hand is: ");

        gv.log("Your turn to select and play a card.\n" +
                "Game Category: " + g.getGameCategory() + " of " + g.getTrumpValue());

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


    private static Game startNewGame(GameView gv) {
        String xmlFile = System.getProperty("user.dir")  + "/" + CARD_XML_FILE;
        Deck deck = new XMLDeckBuilder(xmlFile).deck();

        Game game = new Game(humanName,numPlayers,deck);

        game.displayAllPlayers(gv);

        gv.log("\nThere are " + game.getDeckSize() + " cards in the pick-up deck.");

        Player dealer = game.getDealer();
        gv.log(dealer.getName() + " is the Dealer");

        if (dealer.isHuman()) {
            gv.log("would you like to shuffle and deal the cards now? (y/n): ");
            while (!forcedYes()){
                gv.log("Your friends are getting board, shuffle and deal the cards now? (y/n): ");
            }
        }
        game.shuffleDeck();
        gv.log(dealer.getName() + " is dealing the cards...");
        game.dealCardsToPlayers();

        gv.displayCards(game);
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
