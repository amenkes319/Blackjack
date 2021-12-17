package blackjack.ptui;

import java.util.Scanner;

import blackjack.game.Blackjack;
import blackjack.game.Blackjack.DisplayState;
import blackjack.util.Observer;

public class PTUI implements Observer<DisplayState> {
	
	private Blackjack model;
	
	public PTUI() {
		model = new Blackjack();
		initView();
	}
	
	/**
	 * Initializes the view
	 */
	private void initView() {
		model.addObserver(this);
	}
	
	public void run() {
        try (Scanner userIn = new Scanner(System.in)) {
//            System.out.print("> ");
//            System.out.flush();
            
            mainLoop:
        	while (true) {
        		if (model.getState() == Blackjack.GameState.BET) {
        			model.deal();
        		} else if (model.getState() == Blackjack.GameState.DEALER_TURN) {
        			model.showDealerCards();
    				try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
    				model.hitDealer();
        		} else if (model.getState() == Blackjack.GameState.PLAYER_TURN){
	                System.out.print("> ");
	                System.out.flush();
	                String userCmd = userIn.nextLine();
	                switch (userCmd.toLowerCase()) {
	                    case "hit":
	                        model.hitPlayer();
	                        break;
	                    case "stand":
	                        model.stand();
	                        break;
	                    case "double":
	                    	if (!model.doubleDown()) {
	                    		error("Insufficient funds.", false);
	                    	}
	                    	break;
	                    case "split":
	                    	if (!model.split()) {
	                    		error("Cannot split.", false);
	                    	}
	                    	break;
	                    case "quit":
	                        break mainLoop;
	                    default:
	                        error("Illegal command.", false);
	                }
        		}
            }
        }
    }
	
	@Override
	public void update(DisplayState data) {
		System.out.println(model.getDealer() + "\n");
		System.out.println(model.getPlayer());
	}
	
	private void error(String message, boolean critical) {
        System.err.println(message);
        if (critical) System.exit(1); // NEVER do this with a GUI!
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
