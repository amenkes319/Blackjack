package blackjack.game;

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	/** 
	 * List of hands the player currently holds.
	 * Size is only greater than 1 if the player splits
	 * */
	public List<Hand> hands;
	private Hand currentHand;
	private double balance;

	public Player() {
		currentHand = new Hand();
		hands = new ArrayList<Hand>();
		hands.add(currentHand);
		balance = 0.0;
	}
	
	/**
	 * Resets player's hand(s)
	 */
	public void resetHand() {
		hands.clear();
		hands.add(new Hand());
	}
	
	/**
	 * Give player a card to their current hand
	 * @param card card player receives
	 */
	public void hit(Card card) {
		currentHand.addCard(card);
	}

	/**
	 * Split given hand
	 * @param hand to be split
	 * @return {@code true} if split was successful
	 */
	public boolean split(Hand hand) {
		if (hands.contains(hand) && hand.size() == 2) {
			Card c1 = hand.getCards().get(0);
			Card c2 = hand.getCards().get(1);
			if (c1.equals(c2)) {
				if (hand.removeCard(c2)) {
					hands.add(new Hand(c2));
					return true;
				}
			}
		}
		return false;
	}
}
