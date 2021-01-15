
package culminating.SamCameron;

import java.io.IOException;
import java.util.*;

/**
 *
 * @author Sam Cameron
 */
public class EnhancedBlackJack {

    // Important variables for the program to run

    private static final Scanner sc = new Scanner(System.in);

    public static int bot_difficulty;

    // The value assigned to bust_card is a placeholder
    public static String bust_card = "temp";

    public static final ArrayList<String> deck = new ArrayList<>();

    public static int player_chips;
    public static int pot;

    public static ArrayList<String> player_cards = new ArrayList<>();
    public static ArrayList<String> bot_cards = new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Actions actions = new Actions();
        boolean play_again;

        while(true) {
            setUp();

            while(player_chips > 0) {
                actions.hand();
            }

            play_again = actions.gameOver();
            if(!play_again) {
                System.out.println("\n\nThanks for playing\n\n");
                System.exit(0);
            }
        }
    }
    
    private static void setUp() {
        // Set Values
        player_chips = 2500;
        pot = 0;

        // Welcome
        System.out.println("\nHello and welcome to Enhanced BlackJack. Rev up your brain and get ready to play!\n");

        // Rules and card key
        System.out.println("""
                
                This game is like normal BlackJack except for a couple twists . . .
                1. If you get dealt a BlackJack you must quickly answer a math question to get double your bet or else you lose it.
                2. If you end up in a tie with the dealer, you have to answer a trivia question to win the hand
                3. If you bust on hand n, on hand n + 1, if you win with the bust card from hand n, you win another 50% of your bet (rounded down)
                Card key: S = Spades, C = Clubs, H = Hearts, D = Diamonds. A = Ace, X = 10, J = Jack, Q = Queen, K = King.
                """);

        //Bot difficulty selection
        System.out.print("\nSelect a difficulty level by typing the number corresponding to your choice "
                + "<1 - Easy, 2 - Medium, 3 - Hard>: ");
        String temp_bot_difficulty = sc.nextLine();
        ArrayList<String> difficulties = new ArrayList<>();
        difficulties.add("1");
        difficulties.add("2");
        difficulties.add("3");
        temp_bot_difficulty = Utilities.stringChooser(temp_bot_difficulty, difficulties);

        switch(temp_bot_difficulty) {
            case "1" -> bot_difficulty = 1;
            case "2" -> bot_difficulty = 2;
            case "3" -> bot_difficulty = 3;
            default -> {}
        }
    }

}
