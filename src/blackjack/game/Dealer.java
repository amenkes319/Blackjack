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
		hand.clearHand();
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
		return hand.hasBust();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasStand() {
		return !hand.isSoft() && hand.getValue() >= 17 ||
				hand.isSoft() && hand.getValue() >= 18 && !hand.hasBust();
	}
	
	/**
	 * Gets the value of the showing card(s) only.
	 * The one shown card if not the dealers turn yet,
	 * total of cards if all showing.
	 * 
	 * @return Total value of face up cards.
	 */
	public int showingValue() {
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
