package blackjack.game;

import java.util.LinkedList;

public class Player {
	
	/** 
	 * List of hands the player currently holds.
	 * Size is only greater than 1 if the player splits
	 * */
	private LinkedList<Hand> hands;
	private int currentIndex;
	private int balance;

	/**
	 * Creates new instance of player with balance of 0
	 */
	public Player() {
		hands = new LinkedList<Hand>();
		hands.add(new Hand());
		hands.iterator().next();
		currentIndex = 0;
		balance = 0;
	}
	
	/**
	 * Creates new instance of player with predetermined balance
	 * 
	 * @param balance Starting balance of player
	 */
	public Player(int balance) {
		this();
		this.balance = balance;
	}
	
	/**
	 * Resets player's hand(s)
	 */
	public void resetHand() {
		hands.clear();
		hands.add(new Hand());
	}
	
	/**
	 * Gets whether the current hand busted
	 * 
	 * @return {@code true} if the current hand busted
	 */
	public boolean hasBust() {
		return hands.get(currentIndex).isBust();
	}
	
	/**
	 * Moves to the next hand
	 * Only multiple hands if player split
	 * 
	 * @return {@code true} if there is a next hand
	 */
	public boolean nextHand() {
		currentIndex++;
		return currentIndex < hands.size()-1;
	}
	
	/**
	 * Give player a card to their current hand
	 * 
	 * @param card card player receives
	 */
	public void hit(Card card) {
		hands.get(currentIndex).addCard(card);
	}
	
	/**
	 * Attempts to split {@code currentHand}
	 *
	 * @return {@code true} if split was successful
	 */
	public boolean split() {
		if (hands.get(currentIndex).size() == 2) {
			Card c1 = hands.get(currentIndex).getCards().get(0);
			Card c2 = hands.get(currentIndex).getCards().get(1);
			if (c1.equals(c2)) {
				if (hands.get(currentIndex).removeCard(c2)) {
					hands.add(new Hand(c2));
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Double down on the current hand by doubling the bet
	 * 
	 * @param card Card to be added
	 * @return {@code true} if the double was successful
	 */
	public boolean doubleDown(Card card) {
		hit(card);
		return true;
	}
	
	@Override
	public String toString() {
		String ret = "Player:\n";
		if (hands.size() == 1) {
			ret += "Hand: " + hands.get(0);
		} else {
			for (int i = 0; i < hands.size()-1; i++) {
				ret += "Hand " + (i+1) + ": " + hands.get(i) + "\n";
			}
			ret += "Hand " + (hands.size() - 1) + ": " + hands.get(hands.size() - 1);
		}
		
		return ret;
	}
}
