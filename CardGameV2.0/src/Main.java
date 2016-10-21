import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

/**
 * Created by danelennon on 9/09/2016.
 */
public class Main {
    private static final String CARD_XML_FILE = "MstCards_151021.plist";
    private static int numPlayers;
    private static String humanName;
    private static Game game;

    public static void main(String[] args) {

        //create a gameView
        GameView gameView = new GameView();

        gameView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameView.setSize(1000,600);
        gameView.setPreferredSize(new Dimension(1000,600));
        gameView.setVisible(true);

        gameView.log("Welcome to Ultimate Super Trump\n" +
                "Please enter your name and the number of players:");

        gameView.newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameView.getNumPlayers() != 0 && numberIsValidForRange(gameView.getNumPlayers(),3,5)){
                    humanName = gameView.getHumanName();
                    numPlayers = gameView.getNumPlayers();

                    //setup game controller
                    game = startNewGame(gameView);
                    setUpRound(game);

                }else {
                    JOptionPane.showMessageDialog(gameView, "must enter a valid number between 3-5");
                }
            }
        });


        /*while (!game.complete()){
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

    private static void playRound(Game game) {
        setUpRound(game);
        while (!game.roundComplete()){
            Player p = game.getNextAvailablePlayer();

            System.out.println("There are " + game.numPlayersLeftInRound() + " players left in this round\n" +
            "and " + game.getNumPlayersPassed() + " have passed.");
            game.displayAllPlayers();
            if (p.isHuman()){
                humanRound(game,p);
            }else{
                System.out.println("AI round commencing...");
                game.aIRound(p);
            }
        }
        if (game.withDrawing()){
            System.out.println("Game closing");
        }else{
            Player roundWinner = game.getRoundWinner();
            System.out.println(roundWinner.getName() + " has won this round!");
        }
    }

    private static void setUpRound(Game game) {
        Player firstPlayer = game.getNextAvailablePlayer();

        game.newRound();
        System.out.println(firstPlayer.getName() + " is the next player\n");
        if (firstPlayer.isHuman()){
            setUpHumanRound(firstPlayer,game);
        }else{
            //dumbAI: choose a cardPanel at random and set the game category at random
            game.setUpAiRound(firstPlayer);
        }
    }

    private static void setUpHumanRound(Player p,Game g) {
        System.out.println("Your hand is: ");

        p.displayHand();
        Card c = g.getValidFirstCard(p);

        c.displayCategories();
        System.out.println("\nSelect a trump category for this round by entering the number of the category: ");
        GameCategory gc = g.getGameCategoryFromUser();

        g.playFirstCard(c,gc,p);
        System.out.println("The trump category for this round is: " + g.getCategory());
        System.out.println("And the top value of this category is: " + g.getTrumpValue());
    }

    private static void humanRound(Game game,Player human) {
        System.out.println("Your hand is: ");
        human.displayHand();

        System.out.println("Your turn to select and play a cardPanel.\n" +
                "The cardPanel to beat is: " + "\n\t" + game.getLastCard() + "\n\n" +
                "Game Category: " + game.getCategory() + " of " + game.getTrumpValue());

        System.out.println("Enter: \n" +
                "(P) to Pass to pass (and pick up a cardPanel) \n" +
                "(C) to Play a cardPanel \n" +
                "(W) to Withdraw from the game");
        char choice = getPlayRoundChoice(game,human);
        if (choice == 'p' || choice == 'P') {
            System.out.println("You have chosen to pass this round :( ");
            System.out.println("Picking up a cardPanel from the pick-up deck...");
            game.pickUp(human);
            human.passed(true);
        }else if (choice == 'c' || choice == 'C') {
            boolean validCardChoice = false;

            if (human.hasCombo()) {
                System.out.println("You have the The Geophysicist and the Magnesite cards\n" +
                        "Would you like to play them both and win this round?: (y/n)");
                if (getYesNoChoice() == 'y') {
                    human.playCombo(game);
                    validCardChoice = true;
                }
            }
            while (!validCardChoice) {
                System.out.print("Choose a cardPanel to play by entering the cardPanel number (1-" + human.getNumCards() + "): ");
                int cardNum = getNumInRange(1, human.getNumCards());
                Card selectedCard = human.getCard(cardNum - 1);

                System.out.println("You have selected cardPanel " + (cardNum) + ": " + selectedCard.name() + "\n");

                if (selectedCard.isTrump()) {
                    System.out.println("You selected a trump cardPanel!");
                    validCardChoice = true;
                    human.playCard(game, selectedCard);

                    System.out.println(human.getName() + " placed the " + selectedCard.name() + " with a trump category of: " + selectedCard.trumpType() + " and won this round");
                    human.playCard(game, selectedCard);
                    game.playAfterTrump(human);

                } else if (!game.cardCanBePlayed(selectedCard)) {
                    System.out.println("This cards trump value isn't higher enough\n" +
                            "Try again...");
                } else {
                    human.playCard(game, selectedCard);
                    validCardChoice = true;
                }
            }
        } else if (choice == 'w' || choice == 'W') {
            game.withDraw();
        }
    }

    private static char getPlayRoundChoice(Game g, Player player) {
        Scanner input = new Scanner(System.in);
        char answer = 'x';
        boolean valid = false;
        //search through all of players cards to see if they can actually play any cards
        boolean canPlay = false;
        for (Card c:player.get_playerDeck().cards()){
            if (g.cardCanBePlayed(c)) {
                canPlay = true;
            }
        }

        if (!canPlay){
            while (!valid){
                System.out.println("You don't have any cards higher enough to play;\n" +
                        "You must opt to pass your turn or withdraw from the game.");
                answer = input.next().charAt(0);
                if (answer != 'p' && answer != 'P'
                        && answer != 'w' && answer != 'W'){
                    System.out.println("invalid choice, enter:\n " +
                            "\t\"p\" or \"P\"; or\n" +
                            "\t\"w\" or \"W\"");
                }else {
                    valid = true;
                }
            }
        }else {
            while (!valid) {
                answer = input.next().charAt(0);
                if (answer != 'p' && answer != 'P'
                        && answer != 'c' && answer != 'C'
                        && answer != 'w' && answer != 'W') {
                    System.out.println("invalid choice, enter:\n " +
                            "\t\"p\" or \"P\"; or\n" +
                            "\t\"c\" or \"C\"; or\n" +
                            "\t\"w\" or \"W\"");
                } else {
                    valid = true;
                }
            }
        }
        return answer;
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
            char yes_no = getYesNoChoice();
            while (yes_no != 'y'){
                System.out.println("Your friends are getting board, shuffle and deal the cards now? (y/n): ");
                yes_no = getYesNoChoice();
            }
        }
        game.shuffleDeck();
        gv.log(dealer.getName() + " is dealing the cards...");
        game.dealCardsToPlayers();
        gv.log("The pick-up deck now has " + game.getDeckSize() + " cards.\n");
        return game;
    }

    private static boolean numberIsValidForRange(int i, int lowerInclusive, int upperInclusive) {
        if (i >= lowerInclusive && i <= upperInclusive){
            return true;
        }else{
            return false;
        }
    }


    private static char getYesNoChoice() {
        Scanner input = new Scanner(System.in);
        boolean valid = false;
        char answer = 'x';
        while (!valid){
            answer = input.next().charAt(0);
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
