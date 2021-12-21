package blackjack.game;

public class Dealer {
	private Hand hand;

	public Dealer() {
		hand = new Hand();
	}
	
	/**
	 * Give dealer a card to their current hand
	 * 
	 * @param card card dealer receives
	 */
	public void hit(Card card) {
		hand.addCard(card);
	}
	
	/**
	 * 
	 */
	public void resetHand() {
		hand = new Hand();
	}
	
	/**
	 * Makes all cards face up
	 */
	public void showCards() {
		for (Card c : hand.getCards()) {
			c.setFaceDown(false);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasBust() {
		return hand.isBust();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasStand() {
		return hand.getValue() >= 17 && !hand.isSoft() ||
			   hand.getValue() >= 18 && hand.isSoft() && !hand.isBust();
	}
	
	/**
	 * Gets the value of the showing cards only.
	 * The one shown card if not the dealers turn yet,
	 * total of cards if all showing.
	 * 
	 * @return Total value of face up cards.
	 */
	private int showingValue() {
		int val = 0;
		for (Card c : hand.getCards()) {
			if (!c.isFaceDown()) {
				val += c.getValue();
			}
		}
		return val;
	}
	
	@Override
	public String toString() {
		String ret = "Dealer: (";
		if (hand.isSoft()) {
			ret += showingValue() - 10 + "/";
		}
		ret += showingValue() + ") " + hand;
		return ret;
	}
}
