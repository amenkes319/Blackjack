package blackjack.game;

import java.util.stream.Stream;

/**
 * The {@code Card} class represents a single card.
 */
public class Card {

	/**
	 * The {@code Suit} enum represents the 4 suits.
	 */
	public enum Suit {
		SPADES, HEARTS, DIAMONDS, CLUBS;
		
		/**
		 * String representation of the suit.
		 * 
		 * @return Name of the suit with only the first letter capitalized
		 */
		public String toString() {
			return name().charAt(0) + name().toLowerCase().substring(1);
		}
	}
	
	/**
	 * The {@code Rank} enum represents the ranks.
	 */
	public enum Rank {
		ACE_LOW(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE_HIGH(11);
		
		/** value of card in the hand */
		private int val;
		
		/**
		 * Create Rank enum of value {@code val}
		 * 
		 * @param val value of the rank
		 */
		private Rank(int val) {
			this.val = val;
		}
		
		/**
		 * Returns value of the rank
		 * 
		 * @return value of the rank
		 */
		public int getValue() {
			return val;
		}
		
		/**
		 * Returns instances of Rank values as a stream
		 * 
		 * @return Stream of Rank values
		 */
		public static Stream<Rank> stream() {
	        return Stream.of(Rank.values()); 
	    }
	
		/**
		 * String representation of the rank.
		 * 
		 * @return Name of the rank with only the first letter capitalized
		 */
		public String toString() {
			if (this == ACE_LOW || this == ACE_HIGH) {
				return "Ace";
			} else {
				return name().charAt(0) + name().toLowerCase().substring(1);
			}
		}
	}
	
	private int value;
	private Suit suit;
	private Rank rank;
	
	/**
	 * Applies appropriate value to card.
	 * 
	 * @param value Value of card from 1 to 13
	 * @param suit Suit of card
	 */
	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.value = rank.getValue();
		this.suit = suit;
	}
	
	/**
	 * Returns the value of the card.
	 * 
	 * @return Value of the card
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns the suit of the card.
	 * 
	 * @return Suit of the card
	 */
	public Suit getSuit() {
		return suit;
	}
	
	/**
	 * Returns the name of the card.
	 * 
	 * @return Name of the card
	 */
	public Rank getRank() {
		return rank;
	}

	/**
	 * Turns a soft ace into a hard ace.
	 * Does nothing if card is not an ace or is already hard
	 */
	public void makeHard() {
		if (rank == Rank.ACE_HIGH) {
			rank = Rank.ACE_LOW;
			value = rank.getValue();
		}
	}
	
	/**
	 * Turns a hard ace into a soft ace.
	 * Does nothing if card is not an ace or is already soft
	 */
	public void makeSoft() {
		if (rank == Rank.ACE_LOW) {
			rank = Rank.ACE_HIGH;
			value = rank.getValue();
		}
	}
	
	public boolean isSoft() {
		return rank == Rank.ACE_HIGH;
	}
	
	// General card equals
//	@Override
//	public boolean equals(Object other) {
//		if (other instanceof Card) {
//			Card c = (Card) other;
//			return getName().equals(c.getName()) &&
//				   getSuit() == c.getSuit();
//		}
//		return false;
//	}
	
	// Blackjack specific equals, only value
	@Override
	public boolean equals(Object other) {
		if (other instanceof Card) {
			Card c = (Card) other;
			Rank r = c.rank;
			return rank == r ||
				  (rank == Rank.ACE_LOW && r == Rank.ACE_HIGH) ||
				  (rank == Rank.ACE_HIGH && r == Rank.ACE_LOW);
		}
		return false;
	}

	/**
	 * Returns String representation of the object following the format:
	 * NAME of SUIT
	 * 
	 * @return String representation of card
	 */
	public String toString() {
		return rank + " of " + suit;
	}
}
