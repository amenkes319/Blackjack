package blackjack;
	
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import blackjack.game.Card;
import blackjack.game.Player;
import javafx.application.Application;
import javafx.stage.Stage;

/*
 * Main class to run application
 * 
 * @author Andrew Menkes
 * @version 1.0.0
 */
public class Blackjack extends Application {
	
	/*
	 * Function to be run at the start of application
	 */
	@Override
	public void start(Stage primaryStage) {
		primaryStage.show();
		primaryStage.close();
	}
	
	public static void main(String[] args) {
		List<Card> deckList = new ArrayList<>();
		
		
		for (Card.Suit suit : Card.Suit.values()) {
			Card.Rank.stream()
	        .filter(r -> r != Card.Rank.ACE_LOW)
	        .forEach(r -> deckList.add(new Card(r, suit)));
		}
		Collections.shuffle(deckList);
		Deque<Card> deck = new ArrayDeque<>(deckList);
//		System.out.println(deck);
		Player p1 = new Player();
		p1.hit(new Card(Card.Rank.ACE_HIGH, Card.Suit.CLUBS));
		p1.hit(new Card(Card.Rank.ACE_HIGH, Card.Suit.SPADES));
		System.out.println(p1.hands);
		System.out.println(p1.split(p1.hands.get(0)));
		System.out.println(p1.hands);
		System.out.println(p1.hands.get(0).getValue());
		System.out.println(p1.hands.get(1).getValue());
		launch(args);
	}
}
