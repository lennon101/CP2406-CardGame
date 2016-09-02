package dl;

/**
 * Created by danelennon on 2/09/2016.
 */
public class Game {

    private int numPlayers;
	private int dealerId;
	private Player[] players;
	private Deck deck; 

	public game(int numPlayers){
		this.numPlayers = numPlayers;
	}

	public void selectDealer(){
		//tod: randomis this selection 
		this.dealerId = 1;
	}
	
	public void dealCardsToPlayers(){
		//if numPlayers is null then return error saying set up game;
	}
}
