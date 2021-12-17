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
	
	public boolean hasStand() {
		return hand.getValue() >= 17 && !hand.isSoft() ||
			   hand.getValue() >= 18 && hand.isSoft() && !hand.isBust();
	}
	
	@Override
	public String toString() {
		return "Dealer: " + hand;
	}
}
