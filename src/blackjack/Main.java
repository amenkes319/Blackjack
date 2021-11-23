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
public class Main extends Application {
	@Override
	public void init() {
		
	}
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.show();
		primaryStage.close();
	}
	
	@Override
	public void stop() {
		
	}
	
	public static void main(String[] args) {
		PTUI ptui = new PTUI();
		ptui.run();
		
//		List<Card> deckList = new ArrayList<>();
//		
//		for (Card.Suit suit : Card.Suit.values()) {
//			Card.Rank.stream()
//	        .filter(r -> r != Card.Rank.ACE_LOW)
//	        .forEach(r -> deckList.add(new Card(r, suit)));
//		}
//		Collections.shuffle(deckList);
//		Deque<Card> deck = new ArrayDeque<>(deckList);
//		System.out.println(deck);
//		Player p1 = new Player();
//		launch(args);
	}
}
