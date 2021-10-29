package blackjack.game;

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	/** 
	 * List of hands the player currently holds.
	 * Size is only greater than 1 if the player splits
	 * */
	private List<Hand> hands;

	public Player() {
		hands = new ArrayList<Hand>();
		hands.add(new Hand());
	}

	// TODO Splitting
}
