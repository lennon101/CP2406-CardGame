package dl;

import java.util.Scanner;

public class Main {

    private static final String CARD_XML_FILE = "MstCards_151021.plist";

    public static void main(String[] args) {
        char choice;
        boolean complete = false;
        Game game;
        int numPlayers = 0;

        String xmlFile = System.getProperty("user.dir") + "/" + CARD_XML_FILE;

        Deck deck = new XMLDeckBuilder(xmlFile).deck();

        showSplashScreen();

        while (!complete){
            showMenu();
            choice = getUserMenuChoice();
            if (choice == 'a' || choice == 'A'){
                displayAbout();
            }else if (choice == 'i' || choice == 'I') {
                displayInstructions();
            } else if (choice == 'q' || choice == 'Q'){
                complete = true;
            } else if (choice == 'p' || choice == 'P'){
                numPlayers = getNumPlayers();
                game = new Game(numPlayers,deck);
                System.out.println("\nThere are " + game.get_deck().size() + " cards in the pick-up deck.");
                System.out.println("would you like to shuffle and deal the cards now? (y/n): ");
                char yes_no = getYesNoChoice();
                while (yes_no != 'y'){
                    System.out.println("Your friends are getting board, shuffle and deal the cards now? (y/n): ");
                    yes_no = getYesNoChoice();
                }
                game.shuffleDeck();

                System.out.println("Dealing the cards...");
                game.dealCardsToPlayers();
                System.out.println("The pick-up deck now has " + game.get_deck().size() + " cards.");

                game.getPlayer(1).setTrumpChooser(true);

                boolean gameComplete = false;
                while (!gameComplete){
                    //insert logic to find out if a player has no cards.
                    //game is then complete -->start a new game or quit?
                    //if (game.getPlayer(1).get_playerDeck().getNumCards() > 0){

                    playRound(game);
                }
            }
        }

    }

    private static void playRound(Game game) {
        //setup the round. this needs to occur once at the start of each round

        Player player = game.getPlayer(1);
        System.out.println("Your hand is: ");
        game.getPlayer(1).displayHand();

        boolean validCardChoice = false;
        while (!validCardChoice){
            System.out.print("\nChoose a card to play by entering the card number (1-" + player.getNumCardsInHand() + "): ");
            int cardIndex = getCardIndexToPlay(player.getNumCardsInHand());
            System.out.println("Card being played is card " + (cardIndex + 1) + ": " + player.getPlayerCard(cardIndex).getTitle() + "\n");
            Card playedCard = player.getPlayerCard(cardIndex);
            if (!playedCard.getClass().equals(PlayCard.class)) {
                System.out.println("You must enter a PlayCard to start the round off");
            }else {
                PlayCard pc = (PlayCard) playedCard; //cast this card to PlayCard type so PlayCard methods are exposed
                playPlayCard(game, player, pc);
                validCardChoice = true;
            }
        }

        while (!game.roundComplete()){
            if (!game.getPlayer(1).isPassed()) {
                System.out.println("Your hand is: ");
                game.getPlayer(1).displayHand();

                System.out.println("Enter: " +
                        "\n(P) to Pass to pass (and pick up a card)" +
                        "\n(C) to Play a card");
                char choice = getPlayARoundChoice();
                if (choice == 'p' || choice == 'P') {
                    System.out.println("You have chosen to pass this round :( ");
                    game.pickUp(game.getPlayer(1), 1);
                    game.getPlayer(1).setPassed(true);
                    //game.AIPlay();
                } else if (choice == 'c' || choice == 'C') {
                    playAHand(game, game.getPlayer(1));
                }
            }
            //game.AIPlay();

        }
    }

    private static char getPlayARoundChoice() {
        Scanner input = new Scanner(System.in);
        boolean valid = false;
        char answer = 'x';
        while (!valid){
            answer = input.next().charAt(0);
            if (answer != 'p' && answer != 'c'){
                System.out.println("invalid choice, enter \"p\" or \"c\"");
            }else {
                valid = true;
            }
        }
        return answer;
    }

    private static void playAHand(Game game, Player player) {
        try {
            if (game.get_trumpCategory() != null){
                System.out.println("The current trump category is: " + game.get_trumpCategory() + ": " + game.getTrumpValue());
                //System.out.println("And the current trump value is: " + game.getTrumpValue());
                System.out.println("You must choose a card that is higher in value than this...\n");
            }
        }catch (Throwable t){}

        boolean validCardChoice = false;
        while (!validCardChoice){
            System.out.print("\nChoose a card to play by entering the card number (1-" + player.getNumCardsInHand() + "): ");
            int cardIndex = getCardIndexToPlay(player.getNumCardsInHand());
            System.out.println("Card being played is card " + (cardIndex + 1) + ": " + player.getPlayerCard(cardIndex).getTitle() + "\n");

            Card playedCard = player.getPlayerCard(cardIndex);
            if (playedCard.getClass().equals(PlayCard.class)) {
                PlayCard pc = (PlayCard) playedCard; //cast this card to PlayCard type so PlayCard methods are exposed
                if (game.get_trumpCategory() != null) {
                    if (!game.playedCardHasHigherTrumpValue(pc)) {
                        System.out.println("This cards Hardness value isn't higher enough");
                        System.out.println("Try again...");
                    } else {
                        playPlayCard(game, player, pc);
                        validCardChoice = true;
                    }
                }
            }else if (playedCard.getClass().equals(TrumpCard.class)){
                playTrumpCard();
                validCardChoice = true;
            }else{
                System.out.println("Error: card not matched to a class");
            }
        }

    }

    private static void playTrumpCard() {
        System.out.println("You have selected a trump card! Bold move...");
        //needs work to figure out all the different options for the different types of trump cards
    }

    private static void playPlayCard(Game game, Player player, PlayCard pc) {
        System.out.println("This card's trump categories are:");
        System.out.println(pc.getDictOfTrumpCategories() + "\n");

        if (player.isTrumpChooser()){
            player.get_playerDeck().remove(pc);

            System.out.print("select a trump category for this round: ");
            displayTrumpCategories();

            Trump_Categories trumpCategory = getValidTrumpCategory();
            game.set_trumpCategory(trumpCategory);
            game.playedCardHasHigherTrumpValue(pc);

            System.out.println("The trump category chosen for this round is: " + trumpCategory);
            System.out.println("And the top value of the " + trumpCategory + " is: " + pc.getDictOfTrumpCategories().get(trumpCategory));
            player.setTrumpChooser(false);
        }else {
            System.out.println("This card has a higher trump value and has been placed down");
            player.get_playerDeck().remove(pc);
        }

    }

    private static void displayTrumpCategories(){
        System.out.println("The categories are: ");
        int i = 1;
        for (Trump_Categories trumpCat:Trump_Categories.values()){
            System.out.println("Category " + i++ +": " + trumpCat);
        }
    }

    private static Trump_Categories getValidTrumpCategory() {
        Scanner input = new Scanner(System.in);
        Integer trumpNum = 0;
        boolean valid = false;
        while (!valid){
            try {
                System.out.print("\nEnter a number between 1 and " + (Trump_Categories.values().length) + " to chose a trump category: ");
                String trumpNumStr = input.next();
                trumpNum = Integer.parseInt(trumpNumStr);
                if (trumpNum<1 || trumpNum>Trump_Categories.values().length){
                    System.out.println("\nEnter number in the valid range\n");
                }else{
                    valid = true;
                }
            } catch (Throwable t){
                System.out.println("\ninvalid input\n");
            }
        }
        return Trump_Categories.values()[trumpNum-1];
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

    private static int getCardIndexToPlay(int numCardsInHand) {
        Scanner input = new Scanner(System.in);
        boolean valid = false;
        int index = 0;
        while (!valid){
            try{
                index = input.nextInt();
                if (index>numCardsInHand || index<=0){
                    System.out.println("This is outside the range of cards in the hand");
                }else {
                    valid = true;
                }
            } catch (Throwable t){
                System.out.println("Not a valid number.");
            }
        }
        return index - 1;
    }

    private static void displayInstructions() {
        System.out.println("Number of players: 3 to 5\n\n" +
                "Objective: To be the first player to lose all of your cards\n\n" +
                "How to play:\n" +
                "\t1. A dealer (randomly chosen) shuffles the cards and deals each player 8 cards. \n" +
                "\t\tEach player can look at their cards, but should not show them to other players. \n" +
                "\t\tThe remaining card pack is placed face down on the table.\n\n" +

                "\t2. The player to the left of the dealer goes first by placing a mineral card on \n" +
                "\t\tthe table. The player must state the mineral name, one of the five trump categories \n" +
                "\t\t(i.e., either Hardness, Specific Gravity, Cleavage, Crustal Abundance, or Economic Value), \n" +
                "\t\tand the top value of that category. For example, a player placing the Glaucophane card may \n" +
                "\t\tstate \"Glaucophane, Specific Gravity, 3.2\"\n\n" +

                "\t3. The player next to the left takes the next turn. This player must play a mineral card \n" +
                "\t\tthat has a higher value in the trump category than the card played by the previous player. \n" +
                "\t\tIn the case of the example of the Glaucophane card above, the player must place a card that \n" +
                "\t\thas a value for specific gravity above 3.2. The game continues with the next player to the left, and so on.\n\n" +

                "\t4. If a player does not have any mineral cards that are of higher value for the specific \n" +
                "\t\ttrump category being played, then the player must pass and pick up one card from the card \n" +
                "\t\tpack on the table. The player then cannot play again until all but one player has passed, \n" +
                "\t\tor until another player throws a supertrump card to change the trump category, as described \n" +
                "\t\tbelow. A player is allowed to pass even if they still hold cards that could be played.\n\n" +

                "\t5. If the player has a supertrump card (The Miner, The Geologist, The Geophysicist, \n" +
                "\t\tThe Petrologist, The Mineralogist, The Gemmologist) they may play this card at any of their \n" +
                "\t\tturns. By placing a supertrump card, the player changes the trump category according to the \n" +
                "\t\tinstructions on the supertrump card. The player then plays a mineral card of their choice to resume \n" +
                "\t\tplay. At this stage, any other player who had passed on the previous round is now able to play again. \n" +
                "\t\tIf a player happens to hold both The Geophysicist card and the Magnetite card in their hand, then that \n" +
                "\t\tplayer can place both cards together to win the round.\n\n" +

                "\t6. The game continues with players taking turns to play cards until all but one player has passed. \n" +
                "\t\tThe last player then gets to lead out the next round and chooses the trump category to be played.\n\n" +

                "\t7. The winner of the game is the first player to lose all of their cards. The game continues until \n" +
                "\t\tall but one player (i.e., the loser) has lost their cards.\n\n");
    }

    private static void displayAbout() {
        System.out.println("MINERAL SUPERTRUMPS: Details and rules of the game\n" +
                "Mineral Supertrumps is a game designed to help players learn about\n" +
                "the properties and uses of economically-significant and common rock-forming \n" +
                "minerals. \n" +
                "The pack consists of 54 mineral cards and 6 \"supertrump\" \n" +
                "cards. Each mineral card includes information about the mineral such as \n" +
                "the chemical formula, the classification, crystal system, the geological environment where \n" +
                "the mineral is commonly found or formed (igneous, metamorphic, \n" +
                "sedimentary, or the mantle), as well as information in the five playing \n" +
                "categories (or trumps) of: \n\t-Hardness, \n\t-Specific Gravity, \n\t-Cleavage, \n\t-Crustal Abundance, and " +
                "\n\tEconomic Value. \n" +
                "The first three trump categories relate to distinct physical properties of the mineral, \n" +
                "while the last two categories rate the importance of the mineral in terms of abundance in the Earths \n" +
                "crust (continental and oceanic) and value to modern societies.\n\n" +
                "The cards are also color-coded by mineral groups: silicates = light green, \n" +
                "oxides = blue, sulfides = orange, carbonates = light brown, phosphates = \n" +
                "purple, sulfates = light blue, halides = pink, native elements = white.\n\n");
    }

    private static int getNumPlayers() {
        Scanner input = new Scanner(System.in);
        boolean valid = false;
        Integer number = 0;
        while (!valid){
            System.out.print("Enter the number of players to player the game (3-5): ");
            try {
                String numberStr = input.next();
                number = Integer.parseInt(numberStr);
                if (number <3 || number > 5){
                    System.out.println("Number must be between 3 and 5");
                } else valid = true;
            } catch (Throwable t) {
                System.out.println("Invalid.");
            }
        }
        return number;
    }

    private static char getUserMenuChoice() {
        Scanner input = new Scanner(System.in);
        char choice = input.next().charAt(0);
        while (choice != 'A' && choice != 'a' && choice != 'i' && choice != 'I' &&  choice != 'p' && choice != 'P' && choice != 'q' && choice != 'Q'){
            System.out.println("Incorrect choice");
            showMenu();
            choice = input.next().charAt(0);
        }
        return choice;
    }

    private static void showMenu() {
        System.out.println("What do you want to do? Enter:\n" +
                "(A) for an About blurb of the game\n" +
                "(I) for Instructions\n" +
                "(P) to start playing the game\n" +
                "(Q) to quit the game\n" +
                ">> ");
    }

    private static void showSplashScreen() {
        System.out.println("Welcome to the ultimate Super Trump Game\nA game that will make you an expert in elements from around the world\n");
    }

}
