package blackjack.game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import blackjack.Observer;

public class Blackjack {
	private final static int NUMBER_OF_DECKS = 1;
	
	private Dealer dealer;
	private Player player;
	private List<Observer<Blackjack>> observers;
	private Deque<Card> deck;
	
	public Blackjack() {
		dealer = new Dealer();
		player = new Player(100);
		observers = new LinkedList<>();
		
		deck = createDeck(NUMBER_OF_DECKS);
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
	public void addObserver( Observer<Blackjack> observer ){
        observers.add(observer);
    }
	
	/**
	 * 
	 */
	public void notifyObservers() {
		for (Observer<Blackjack> observer : observers) {
			observer.update(this);
		}
	}
	
	/**
	 * 
	 */
	public void hit() {
		player.hit(deck.pop());
		if (player.hasBust()) {
			if (!player.nextHand()) {
				
			}
		}
        notifyObservers();
	}
	
	/**
	 * 
	 */
	public void stand() {
		if (!player.nextHand()) {
			
		}
		notifyObservers();
	}
	
	/**
	 * 
	 */
	public boolean doubleDown() {
		boolean valid = player.doubleDown(deck.pop());
		if (valid) {
            notifyObservers();
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
			notifyObservers();
		}
		return valid;
	}
	
	@Override
	public String toString() {
		// TODO
		String ret = "";
		ret += dealer + "\n\n";
		ret += player;
		
		return ret;
	}
}