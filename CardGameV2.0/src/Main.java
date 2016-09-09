/**
 * Created by danelennon on 9/09/2016.
 */
public class Main {
    public static void main(String[] args) {
        Card cardLastPlayed = new GibbsiteCard();
        Card cardToBePlayed = new CassiteriteCard();

        CardComparisonResult comparisonResult = cardLastPlayed.compare(cardToBePlayed);

        if (cardLastPlayed.isTrump()){
            //It's a trump
        }

        GameCategory currentGameType = GameCategory.HARDNESS;
        boolean cardTrumpedForCategory = false;
        //If game state = compare hardness
        switch(currentGameType){
            case HARDNESS:
                cardTrumpedForCategory = comparisonResult.hardnessWasTrumped();
                break;
            case ECONOMIC_VALUE:
                cardTrumpedForCategory = comparisonResult.economicValueWasTrumped();
                break;
        }

        if(cardTrumpedForCategory){
            //Move to the next player
        }else{
            //Can't place that card.
        }
    }
}
