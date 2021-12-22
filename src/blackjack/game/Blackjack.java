package blackjack.game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import blackjack.util.Observer;

/**
 * Class that operates the game
 * 
 * @author Andrew Menkes
 */
public class Blackjack {
	
	/**
	 * Represents the state of each round
	 *  
	 * @author Andrew Menkes
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
		DEAL, HIT, STAND, SPLIT, DOUBLE, PLAYER_WIN, DEALER_WIN;
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
	
	public void addObserver(Observer<DisplayState> observer) {
        observers.add(observer);
    }
	
	/**
	 * Update all observers
	 */
	public void notifyObservers(DisplayState data) {
		for (Observer<DisplayState> observer : observers) {
			observer.update(data);
		}
	}
	
	public void reset() {
		deck = createDeck(DECK_COUNT);
		player.resetHand();
		dealer.resetHand();
	}
	
	/**
	 * Initial bet before the cards are dealt
	 * 
	 * @param amount Amount to bet.
	 * @return {@code true} if the bet is valid.
	 */
	public boolean bet(int amount) {
		return player.bet(amount);
	}
	
	/**
	 * Deal cards out to player and dealer
	 */
	public void deal() {
		state = GameState.PLAYER_TURN;
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
	
	public Dealer getDealer() {
		return dealer;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public GameState getGameState() {
		return state;
	}
	
	public void setGameState(GameState state) {
		this.state = state;
		if (state == GameState.DEALER_TURN) {
			showDealerCards();
		}
	}
	
	/**
	 * 
	 */
	public void hitDealer() {
		dealer.hit(deck.pop());
		if (dealer.hasBust() || dealer.hasStand()) {
			setGameState(GameState.END_HAND);
		}
		notifyObservers(DisplayState.HIT);
	}
	
	/**
	 * 
	 */
	public void hitPlayer() {
		player.hit(deck.pop());
		if (player.hasCurrentBust()) {
			if (player.hasNextHand()) {
				player.nextHand(deck.pop());
			} else {
				setGameState(GameState.DEALER_TURN);
			}
		}
        notifyObservers(DisplayState.HIT);
	}
	
	/**
	 * 
	 */
	public void stand() {
		if (player.hasNextHand()) {
			player.nextHand(deck.pop());
		} else {
			setGameState(GameState.DEALER_TURN);
		}
		notifyObservers(DisplayState.STAND);
	}
	
	/**
	 * 
	 */
	public boolean doubleDown() {
		boolean valid = player.doubleDown(deck.pop());
		if (valid) {
			if (player.hasNextHand()) {
				player.nextHand(deck.pop());
			} else {
				setGameState(GameState.DEALER_TURN);
			}
            notifyObservers(DisplayState.DOUBLE);
		}
		return valid;
	}
	
	/**
	 * Split current player hand
	 * 
	 * @return {@code true} if split is valid
	 */
	public boolean split() {
		boolean valid = player.split();
		if (valid) {
			player.hit(deck.pop());
			notifyObservers(DisplayState.SPLIT);
		}
		return valid;
	}
}