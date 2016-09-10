import java.util.Random;
import java.util.Scanner;

/**
 * Created by danelennon on 9/09/2016.
 */
public class Main {
    private static final String CARD_XML_FILE = "MstCards_151021.plist";

    public static void main(String[] args) {

        Game game = startNewGame();

        //todo: game.getPlayer(1).setTrumpChooser(true);

        while (!game.complete()){
            // TODO: 9/09/2016  insert logic to find out if player has no cards.
            playRound(game);
        }
    }

    private static void playRound(Game game) {
        setUpRound(game);
        int indexOf2ndPlayer;
        if (game.getDealerId()+2 >game.getNumPlayersLeft()){
            indexOf2ndPlayer = 0;
        }else{
            indexOf2ndPlayer = game.getDealerId() + 2; //The dealer deals, 1st player sets up round, 2nd player starts actual game
        }

        for (int i=indexOf2ndPlayer;i<game.getNumPlayersLeft();++i){

            if (game.getPlayers().get(i).isHuman()){
                humanRound(game);
            }else{
                //game.aIRound(); // TODO: 9/09/2016 make AI round
                System.out.println("AI round commences");
            }
        }
    }

    private static void setUpRound(Game game) {
        Player firstPlayer = game.getFirstPlayer();
        System.out.println(firstPlayer.getName() + " is the next player to the left of the dealer");
        if (firstPlayer.isHuman()){
            System.out.println("Your hand is: ");
            firstPlayer.displayHand();
            boolean validCardChoice = false;
            while (!validCardChoice){
                System.out.print("Choose a card to play by entering the card number (1-" + firstPlayer.getNumCards() + "): ");
                int cardNum = getNumInRange(1,firstPlayer.getNumCards());
                Card selectedCard = firstPlayer.getCard(cardNum-1);

                System.out.println("You have selected card " + (cardNum) + ": " + selectedCard.name() + "\n");

                if (selectedCard.isTrump()){
                    System.out.println("You must select a card other than a trump to start the round");
                }else {
                    validCardChoice = true;
                    selectedCard.displayCategories();

                    System.out.println("\nSelect a trump category for this round by entering the number of the category: ");
                    GameCategory gc = getGameCategory();
                    game.playFirstCard(selectedCard,gc,firstPlayer);
                }
            }
            System.out.println("The trump category chosen for this round is: " + game.getCategory());
            System.out.println("And the top value of this category is: " + game.getTrumpValue());
        }else{
            //dumbAI: choose a card at random and set the game category at random
            game.setUpRound();
        }
    }

    private static void humanRound(Game game) {
        Player human = game.getHuman();
        boolean validCardChoice = false;

        while (!validCardChoice) {
            System.out.print("Choose a card to play by entering the card number (1-" + human.getNumCards() + "): ");
            int cardNum = getNumInRange(0, human.getNumCards());
            Card selectedCard = human.getCard(cardNum - 1);

            System.out.println("You have selected card " + (cardNum) + ": " + selectedCard.name() + "\n");

            if (!game.cardCanBePlayed(selectedCard)){
                System.out.println("This cards trump value isn't higher enough\n" +
                        "Try again...");
            } else {
                game.playCard(human,selectedCard);
                validCardChoice = true;
            }
        }
    }

    private static Game startNewGame() {
        Scanner input = new Scanner(System.in);
        String xmlFile = System.getProperty("user.dir") + "/" + CARD_XML_FILE;
        // TODO: 10/09/2016 remove the above line from this function and set up logic in deck to start new deck

        Deck deck = new XMLDeckBuilder(xmlFile).deck();
        System.out.print("Welcome to Ultimate Super Trump\n" +
                "Please enter you name:");
        String humanName = input.next();
        System.out.print("Enter the number of players to player the game (3-5): ");
        int numPlayers = getNumInRange(3,5);
        Game game = new Game(humanName,numPlayers,deck);

        game.displayAllPlayers();

        System.out.println("\nThere are " + game.getDeckSize() + " cards in the pick-up deck.");

        Player dealer = game.getDealer();
        System.out.println(dealer.getName() + " is the Dealer");

        if (dealer.isHuman()) {
            System.out.println("would you like to shuffle and deal the cards now? (y/n): ");
            char yes_no = getYesNoChoice();
            while (yes_no != 'y'){
                System.out.println("Your friends are getting board, shuffle and deal the cards now? (y/n): ");
                yes_no = getYesNoChoice();
            }
        }
        game.shuffleDeck();
        System.out.println(dealer.getName() + " is dealing the cards...");
        game.dealCardsToPlayers();
        System.out.println("The pick-up deck now has " + game.getDeckSize() + " cards.");
        return game;
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
        int index = 0;
        while (!valid){
            try{
                index = input.nextInt();
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

    public static GameCategory getGameCategory() {
        int i = getNumInRange(1,GameCategory.values().length);
        GameCategory gc = GameCategory.values()[i-1];
        return gc;
    }
}
