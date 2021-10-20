package blackjack;
	
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import blackjack.game.Card;
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
			for (int val = 1; val <= 13; val++) {
				deckList.add(new Card(val, suit));
			}
		}
		Collections.shuffle(deckList);
		Deque<Card> deck = new ArrayDeque<>(deckList);
		System.out.println(deck);
		launch(args);
	}
}
