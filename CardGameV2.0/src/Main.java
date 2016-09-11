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
            //break;
        }
    }

    private static void playRound(Game game) {
        setUpRound(game);
        while (!game.roundComplete()){
            Player p = game.getNextPlayer();
            if (p.isHuman()){
                humanRound(game);
            }else{
                System.out.println("AI round commences");
                game.aIRound(p);
                //break; // TODO: 11/09/2016 debug why infinite loop occurs
            }
        }
    }

    private static void setUpRound(Game game) {
        game.unPassAllPlayers();
        Player firstPlayer = game.getNextPlayer();
        System.out.println("New round commencing\n" + firstPlayer.getName() + " is the next player");
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
                    GameCategory gc = getGameCategoryFromUser();
                    game.playFirstCard(selectedCard,gc,firstPlayer);
                }
            }
            System.out.println("The trump category chosen for this round is: " + game.getCategory());
            System.out.println("And the top value of this category is: " + game.getTrumpValue());
        }else{
            //dumbAI: choose a card at random and set the game category at random
            game.setUpRound(firstPlayer);
        }
    }

    private static void humanRound(Game game) {
        Player human = game.getHuman();
        System.out.println("Your turn to select and play a card.\n" +
        "Game Category: " + game.getCategory() + " of " + game.getTrumpValue());
        System.out.println("Your hand is: ");
        human.displayHand();

        System.out.println("Enter: " +
                "\n(P) to Pass to pass (and pick up a card)" +
                "\n(C) to Play a card");
        char choice = getPlayRoundChoice(game,human);
        if (choice == 'p' || choice == 'P') {
            System.out.println("You have chosen to pass this round :( ");
            game.pickUp(human);
            human.passed(true);
        }else if (choice == 'c' || choice == 'C') {
            boolean validCardChoice = false;
            while (!validCardChoice) {
                System.out.print("Choose a card to play by entering the card number (1-" + human.getNumCards() + "): ");
                int cardNum = getNumInRange(1, human.getNumCards());
                Card selectedCard = human.getCard(cardNum - 1);

                System.out.println("You have selected card " + (cardNum) + ": " + selectedCard.name() + "\n");

                if (selectedCard.isTrump()) {
                    System.out.println("You selected a trump card!");
                    validCardChoice = true;
                    game.playCard(human, selectedCard);
                    System.out.println(human.getName() + " placed the " + selectedCard.getTrumpValueForCategory(getGameCategoryFromUser()) + " and won this round");
                    System.out.println("you must now"); // TODO: 11/09/2016 give human player ability to play trump card
                } else if (!game.cardCanBePlayed(selectedCard)) {
                    System.out.println("This cards trump value isn't higher enough\n" +
                            "Try again...");
                } else {
                    game.playCard(human, selectedCard);
                    validCardChoice = true;
                }
            }
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
                System.out.println("You don't have any cards higher enough to play, you must opt to pass");
                answer = input.next().charAt(0);
                if (answer != 'p' && answer != 'P'){
                    System.out.println("invalid choice, enter \"p\" or \"P\"");
                }else {
                    valid = true;
                }
            }
        }else {
            while (!valid) {
                answer = input.next().charAt(0);
                if (answer != 'p' && answer != 'c') {
                    System.out.println("invalid choice, enter \"p\" or \"c\"");
                } else {
                    valid = true;
                }
            }
        }
        return answer;
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
        System.out.println("The pick-up deck now has " + game.getDeckSize() + " cards.\n");
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

    public static GameCategory getGameCategoryFromUser() {
        int i = getNumInRange(1,GameCategory.values().length);
        GameCategory gc = GameCategory.values()[i-1];
        return gc;
    }
}
