package blackjack.ptui;

import java.util.Scanner;

import blackjack.game.Blackjack;
import blackjack.game.Blackjack.GameState;
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
            mainLoop:
        	while (true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
        		switch (model.getGameState()) {
				case BET:
					System.out.println("\nNew Hand:");
					model.deal();
					break;
				case PLAYER_TURN:
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
	                    		error("Cannot double.", false);
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
					break;
				case DEALER_TURN:
					if (model.getPlayer().hasBust()) {
						model.setGameState(GameState.END_HAND);
					} else {
						model.hitDealer();
						if (model.getDealer().hasBust() || model.getDealer().hasStand()) {
							model.setGameState(GameState.END_HAND);
						}
					}
					break;
				case END_HAND:
					if (model.getPlayer().hasBust()) {
						System.out.println("PLAYER BUST, DEALER WINS");
					} else { // multiple hands / player not bust
						if (model.getDealer().hasBust()) {
							// any player hands not busted wins
						} else {
							// compare all hands to dealer
						}
					}
					model.setGameState(GameState.BET);
					break;
        		}
            }
        }
    }
	
	@Override
	public void update(DisplayState data) {
		System.out.println("\n" + model.getDealer());
		System.out.println("\n" + model.getPlayer());
//		switch (data) {
//		case DEAL:
//			System.out.println(model.getDealer());
//			System.out.println("\n" + model.getPlayer());
//			break;
//		case DOUBLE:
//			
//			break;
//		case HIT:
//			
//			break;
//		case SPLIT:
//			
//			break;
//		case STAND:
//			
//			break;
//		}
	}
	
	private void error(String message, boolean critical) {
        System.err.println(message);
        if (critical) System.exit(1); // NEVER do this with a GUI!
    }
	
	public static void main(String[] args) {
		PTUI ptui = new PTUI();
		ptui.run();
	}
}
