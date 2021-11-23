package blackjack.game;

public class Dealer {
	private Hand hand;

	public Dealer() {
		hand = new Hand();
	}

	@Override
	public String toString() {
		return "Dealer: " + hand;
	}
}
