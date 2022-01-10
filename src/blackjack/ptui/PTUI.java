package blackjack.ptui;

import java.util.Scanner;

import blackjack.game.Blackjack;
import blackjack.game.Blackjack.Display;
import blackjack.game.Blackjack.GameState;
import blackjack.util.Observer;

public class PTUI implements Observer<Display> {
	
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
					model.reset();
					System.out.println("New Hand:");
					int bet;
					do {
						System.out.println("Balance: " + model.player().getBalance());
						System.out.println("Please enter your bet: ");
						System.out.print("> ");
						System.out.flush();
						try {
							bet = Integer.parseInt(userIn.nextLine());
							if (model.bet(bet)) {
								break;
							}
						} catch (NumberFormatException e) {}
					} while(true);
					
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
					if (model.player().hasBust() || model.player().hasBlackjack()) {
						model.setGameState(GameState.END_HAND);
					} else {
						if (model.dealer().hasBust() || model.dealer().hasStand()) {
							model.setGameState(GameState.END_HAND);
						} else {
							model.hitDealer(false);
							model.notifyObservers(Display.HIT);
						}
					}
					break;
				case END_HAND:
					model.endgame();
					break;
				default:
					break;
        		}
            }
        }
    }
	
	@Override
	public void update(Display data) {
		System.out.println();
		switch (data) {
		case DEAL:
			System.out.println(model.dealer());
			System.out.println("\n" + model.player());
			break;
		case DOUBLE:
			System.out.println(model.dealer());
			System.out.println("\n" + model.player());
			break;
		case HIT:
			System.out.println(model.dealer());
			System.out.println("\n" + model.player());
			break;
		case SPLIT:
			System.out.println(model.dealer());
			System.out.println("\n" + model.player());
			break;
		case STAND:
			System.out.println(model.dealer());
			System.out.println("\n" + model.player());
			break;
		case PUSH:
			System.out.println("Push");
			break;
		case BLACKJACK_WIN:
			System.out.println(model.dealer());
			System.out.println("\n" + model.player());
			System.out.println("BLACKJACK!");
			break;
		case PLAYER_WIN:
			System.out.println("You win!");
			break;
		case PLAYER_BUST:
			System.out.println("Bust");
			break;
		case DEALER_WIN:
			System.out.println("Dealer wins.");
			break;
		}
		System.out.println();
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
