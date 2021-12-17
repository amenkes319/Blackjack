package blackjack.game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import blackjack.util.Observer;

public class Blackjack {
	
	/**
	 * 
	 * @author Andrew Menkes
	 *
	 */
	public enum GameState {
		BET, PLAYER_TURN, DEALER_TURN, END_HAND
	}
	
	/**
	 * Used by observers as Subject data
	 * 
	 * @author Andrew Menkes
	 */
	public enum DisplayState {
		DEAL, HIT, STAND, SPLIT, DOUBLE;
	}

	/**
	 * Number of decks being used
	 */
	private final static int DECK_COUNT = 1;
	
	private Dealer dealer;
	private Player player;
	private GameState state;
	private List<Observer<DisplayState>> observers;
	private Deque<Card> deck;
	
	public Blackjack() {
		dealer = new Dealer();
		player = new Player(100);
		state = GameState.BET;
		observers = new LinkedList<>();
		deck = createDeck(DECK_COUNT);
	}
	
	/**
	 * Create deck of cards
	 * 
	 * @param num Number of decks being used
	 * @return {@code ArrayDeque} of cards
	 */
	private Deque<Card> createDeck(int num) {
		List<Card> deck = new ArrayList<>();
		
		for (int i = 0; i < num; i++) {
			for (Card.Suit suit : Card.Suit.values()) {
				Card.Rank.stream()
	        	.filter(r -> r != Card.Rank.ACE_LOW)
	        	.forEach(r -> deck.add(new Card(r, suit)));
			}
		}
		Collections.shuffle(deck);
		return new ArrayDeque<>(deck);
	}
	
	/**
	 * 
	 * @param observer
	 */
	public void addObserver(Observer<DisplayState> observer) {
        observers.add(observer);
    }
	
	/**
	 * 
	 */
	public void notifyObservers(DisplayState data) {
		for (Observer<DisplayState> observer : observers) {
			observer.update(data);
		}
	}
	
	/**
	 * 
	 */
	public void deal() {
		state = GameState.PLAYER_TURN;
		deck = createDeck(DECK_COUNT);
		player.resetHand();
		dealer.resetHand();
		player.hit(deck.pop());
		dealer.hit(deck.pop().setFaceDown(true));
		player.hit(deck.pop());
		dealer.hit(deck.pop());
		notifyObservers(DisplayState.DEAL);
	}
	
	/**
	 * Flips dealer cards up
	 */
	public void showDealerCards() {
		dealer.showCards();
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public Dealer getDealer() {
		return dealer;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Gets current state of the game
	 * 
	 * @return current state of the game
	 */
	public GameState getState() {
		return state;
	}
	
	/**
	 * 
	 */
	public void hitDealer() {
		dealer.hit(deck.pop());
		if (dealer.hasBust() || dealer.hasStand()) {
			
		}
		notifyObservers(DisplayState.HIT);
	}
	
	/**
	 * 
	 */
	public void hitPlayer() {
		player.hit(deck.pop());
		if (player.hasBust()) {
			if (!player.nextHand()) {
				state = GameState.DEALER_TURN;
			}
		}
        notifyObservers(DisplayState.HIT);
	}
	
	/**
	 * 
	 */
	public void stand() {
		if (!player.nextHand()) {
			state = GameState.DEALER_TURN;
		}
		notifyObservers(DisplayState.STAND);
	}
	
	/**
	 * 
	 */
	public boolean doubleDown() {
		boolean valid = player.doubleDown(deck.pop());
		if (valid) {
			if (!player.nextHand()) {
				state = GameState.DEALER_TURN;
			}
            notifyObservers(DisplayState.DOUBLE);
		}
		return valid;
	}
	
	/**
	 * 
	 * 
	 * @return {@code true} if split is valid
	 */
	public boolean split() {
		boolean valid = player.split();
		if (valid) {
			notifyObservers(DisplayState.SPLIT);
		}
		return valid;
	}
}