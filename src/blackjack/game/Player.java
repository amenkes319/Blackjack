package blackjack.game;

import java.util.LinkedList;
import java.util.List;

public class Player {
	
	/** 
	 * List of hands the player currently holds.
	 * Size is only greater than 1 if the player splits
	 */
	private List<Hand> hands;
	private int currentIndex;
	private int balance;
	
	/**
	 * Creates new instance of player with predetermined balance
	 * 
	 * @param balance Starting balance of player
	 */
	public Player(int balance) {
		hands = new LinkedList<Hand>();
		hands.add(new Hand());
		currentIndex = 0;
		this.balance = balance;
	}
	
	/**
	 * Creates new instance of player with balance of 0
	 */
	public Player() {
		this(0);
	}
	
	public List<Hand> hands() {
		return hands;
	}
	
	public int getBalance() {
		return balance;
	}
	
	/**
	 * Bet for current hand.
	 * 
	 * @param amount Amount to bet.
	 * @param addBet {@code true} if bet should be added to current bet, 
	 * 				 false if bet should be set to amount.
	 * @return {@code true} if the bet is valid.
	 */
	public boolean bet(int amount, boolean addBet) {
		return bet(amount, addBet, currentIndex);
	}
	
	/**
	 * Bet for given hand.
	 * 
	 * @param amount Amount to bet.
	 * @param addBet {@code true} if bet should be added to current bet, 
	 * 				 false if bet should be set to amount.
	 * @param index Index of hand in list
	 * @return {@code true} if the bet is valid.
	 */
	private boolean bet(int amount, boolean addBet, int index) {
		if (0 < amount && amount <= balance) {
			hands.get(index).setBet(amount + (addBet ? hands.get(index).getBet() : 0));
			balance -= amount;
			System.out.println(balance);
			return true;
		}
		return false;
	}
	
	/**
	 * Resets player's hand(s)
	 */
	public void resetHand() {
		hands.clear();
		hands.add(new Hand());
		currentIndex = 0;
	}
	
	/**
	 * Gets whether the current hand busted
	 * Different from {@code hasBust()}
	 * 
	 * @return {@code true} if the current hand busted
	 */
	public boolean hasCurrentBust() {
		return hands.get(currentIndex).hasBust();
	}
	
	/**
	 * Gets whether all player hands have busted.
	 * Used so if {@code true}, the dealer will not draw cards.
	 * 
	 * @return {@code true} if all hands of player have bust.
	 */
	public boolean hasBust() {
		for (Hand hand : hands) {
			if (!hand.hasBust()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Moves to the next hand.
	 * Only multiple hands if player split.
	 * 
	 * @param card Second card for the next hand
	 */
	public void nextHand(Card card) {
		currentIndex++;
		hit(card);
	}
	
	/**
	 * Gets if the player has a next hand.
	 * 
	 * @return {@code true} if the player has another hand to play.
	 */
	public boolean hasNextHand() {
		return currentIndex + 1 < hands.size();
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
	 * Attempts to split the current hand
	 *
	 * @return {@code true} if split was successful
	 */
	public boolean split() {
		if (hands.get(currentIndex).size() == 2) {
			Card c1 = hands.get(currentIndex).getCards().get(0);
			Card c2 = hands.get(currentIndex).getCards().get(1);
			if (c1.equals(c2) && currentBet() <= balance) {
				if (hands.get(currentIndex).removeCard(c1)) {
					hands.add(currentIndex + 1, new Hand(c1));
					bet(currentBet(), false, currentIndex + 1);
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
		if (hands.get(currentIndex).size() == 2 && currentBet() <= balance) {
			hit(card);
			bet(currentBet(), true);
			return true;
		}
		return false;
	}
	
	/**
	 * Get the bet on the current hand
	 * 
	 * @return Bet on current hand
	 */
	public int currentBet() {
		return hands.get(currentIndex).getBet();
	}
	
	/**
	 * Add money to player's balance
	 * 
	 * @param amount Amount to add
	 */
	public void win(int amount) {
		balance += amount;
	}
	
	/**
	 * @return {@code true} if player has blackjack
	 */
	public boolean hasBlackjack() {
		return hands.size() == 1 && hands.get(currentIndex).size() == 2 && has21();
	}
	
	/**
	 * @return {@code true} if current hand's value is 21 (Not blackjack)
	 */
	public boolean has21() {
		return hands.get(currentIndex).getValue() == 21;
	}
	
	/**
	 * Helper method for toString to reduce repetition.
	 * 
	 * @param index index of hand in list.
	 * @param multipleHands {@code true} if the player split and
	 * there are multiple hands in the list.
	 * @return String representation of hand for PTUI display.
	 */
	private String handToString(int index, boolean multipleHands) {
		String ret = "\tHand";
		if (multipleHands) {
			ret += " " + (index + 1);
		}
		ret += ": (";
		if (hands.get(index).isSoft()) {
			ret += hands.get(index).getValue() - 10 + "/";
		}
		ret += hands.get(index).getValue() + ") " + hands.get(index);
		return ret;
	}
	
	@Override
	public String toString() {
		String ret = "Player:\n";
		if (hands.size() == 1) {
			ret += handToString(0, false);
		} else {
			for (int i = 0; i < hands.size()-1; i++) {
				ret += handToString(i, true) + "\n";
			}
			ret += handToString(hands.size() - 1, true);
		}
		
		return ret;
	}
}
