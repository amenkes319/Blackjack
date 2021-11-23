package blackjack;

import java.util.Scanner;

import blackjack.game.Blackjack;

public class PTUI implements Observer<Blackjack> {
	
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
            System.out.print("cmd> ");
            System.out.flush();
            
            mainLoop:
            while (userIn.hasNextLine()) {
                String userCmd = userIn.nextLine();
                switch ( userCmd ) {
                    case "hit":
                        model.hit();
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
                        // Direct controller -> view connection
                }
                System.out.print( "cmd> " );
                System.out.flush();
            }
        }
    }
	
	@Override
	public void update(Blackjack subject) {
		
	}
	
	private void error(String message, boolean critical) {
        System.err.println( message );
        if (critical) System.exit(1); // NEVER do this with a GUI!
    }
}
