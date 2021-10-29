package blackjack.game;

/**
 * The {@code Card} class represents a single card.
 */
public class Card {

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
	
	private int value;
	private Suit suit;
	private boolean soft;
	private String name;
	
	/**
	 * Applies appropriate value to card.
	 * 
	 * @param value Value of card from 1 to 13
	 * @param suit Suit of card
	 */
	public Card(int value, Suit suit) {
		switch (value) {
		case 1: // Ace
			this.value = 11;
			soft = true;
			name = "Ace";
			break;
			
		case 11: // Jack
			this.value = 10;
			soft = false;
			name = "Jack";
			break;
			
		case 12: // Queen
			this.value = 10;
			soft = false;
			name = "Queen";
			break;
			
		case 13: // King
			this.value = 10;
			soft = false;
			name = "King";
			break;
			
		default: // 2-10
			this.value = value;
			soft = false;
			name = String.valueOf(value);
		}
		
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
	 * Returns whether the card is soft or not.
	 * 
	 * @return {@code true} if the card is soft
	 */
	public boolean isSoft() {
		return soft;
	}
	
	/**
	 * Returns the name of the card.
	 * 
	 * @return Name of the card
	 */
	public String getName() {
		return name;
	}

	/**
	 * Turns a soft ace into a hard ace.
	 * Does nothing if card is not an ace or is already hard
	 */
	public void makeHard() {
		if (soft) {
			soft = false;
			value = 1;
		}
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
			return getValue() == c.getValue() || (getName().equals("Ace") && c.getName().equals("Ace"));
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
		return name + " of " + suit;
	}
}
