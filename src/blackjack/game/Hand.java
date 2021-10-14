package blackjack.game;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class Hand {

	/** The stack of cards of the hand. */
	private Deque<Card> cards;
	
	/**
	 * The total value of the hand.
	 * Will represent the higher value of a soft hand.
	 */
	private int value;
	
	public Hand() {
		cards = new ArrayDeque<>();
		value = 0;
	}
	
	/**
	 * Stack of cards that make up the hand.
	 * @return Deque of cards
	 */
	public Deque<Card> getCards() {
		return cards;
	}
	
	/**
	 * Give total value of the hand.
	 * 
	 * @return Value of the hand
	 */
	public int getValue() {
		return value;	
	}
	
	/**
	 * Returns whether the hand is soft or not
	 * 
	 * @return True if the hand is soft, false otherwise
	 */
	public boolean isSoft() {
		return checkSoft() != null;
	}
	
	/**
	 * Adds a card to the hand
	 * 
	 * @param card Card to be added
	 */
	public void addCard(Card card) {
		cards.push(card);
		value += card.getValue();
		
		if (value > 21) {
			Card softCard = checkSoft();
			if (softCard != null) {
				softCard.makeHard();
				updateValue();
			}
		}
	}
	
	/**
	 * Removes all cards from the hand.
	 */
	public void clearHand() {
		cards.clear();
		updateValue();
	}
	
	/**
	 * Updates value of hand by iterating through hand and adding the current
	 * value of each card to the total.
	 */
	private void updateValue() {
		value = 0;
		cards.forEach(card -> value += card.getValue());
	}
	
	/**
	 * Checks if the hand is soft by iterating through all the cards
	 * in the hand starting at the last in.
	 * 
	 * @return Most recently added soft card if hand is soft, null otherwise
	 */
	private Card checkSoft() {
		Card card;
		
		Iterator<Card> it = cards.iterator();
		while (it.hasNext()) {
			if ((card = it.next()).isSoft()) {
				return card;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return cards.toString();
	}
}
