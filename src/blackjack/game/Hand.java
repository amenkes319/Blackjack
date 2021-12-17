package blackjack.game;

import java.util.ArrayList;
import java.util.List;

public class Hand {

	/** The stack of cards of the hand. */
	private List<Card> cards;
	
	/**
	 * The total value of the hand.
	 * Will represent the higher value of a soft hand.
	 */
	private int value;
	
	/**
	 * Create new empty hand object
	 */
	public Hand() {
		cards = new ArrayList<>();
		value = 0;
	}
	
	/**
	 * Create new hand with cards in it
	 * @param cardArr cards to add to hand
	 */
	public Hand(Card... cardArr) {
		this();
		for (Card card : cardArr) {
			addCard(card);
		}
	}
	
	/**
	 * Stack of cards that make up the hand.
	 * @return Deque of cards
	 */
	public List<Card> getCards() {
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
	 * Returns size of hand
	 * @return size of hand
	 */
	public int size() {
		return cards.size();
	}
	
	/**
	 * Returns whether the hand is soft or not
	 * 
	 * @return {@code true} if the hand is soft
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
		// Reset if hard ace was moved from one hand to this hand
		card.makeSoft();
		
		cards.add(card);
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
	 * Removes card from hand
 	 * @param card card to remove
	 * @return {@code true} if card is contained in the list
	 */
	public boolean removeCard(Card card) {
		boolean result = cards.remove(card);
		updateValue();
		return result;
	}
	
	/**
	 * Removes all cards from the hand.
	 */
	public void clearHand() {
		cards.clear();
		updateValue();
	}

	/**
	 * Returns whether the value of the hand is over 21
	 * 
	 * @return True if the value is over 21, false otherwise
	 */
	public boolean isBust() {
		return getValue() > 21;
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
		for (Card card : cards) {
			if (card.isSoft()) {
				return card;
			}
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		String ret = "";
		for (Card c : cards.stream().filter(card -> !card.isFaceDown()).toList()) {
			ret += c + ", ";
		}
		return ret.length() > 0 ? ret.substring(0, ret.length() - 2) : "No cards face up!";
	}
}
