package culminating.SamCameron;

import java.io.IOException;
import java.util.*;

public class Actions {

    private final Scanner sc = new Scanner(System.in);

    ArrayList<String> allowed_bets = new ArrayList<>();

    int previous_bet;

    public Actions() {
        List<String> bet_opts = Arrays.asList("1", "25", "50", "100", "500", "1000");
        allowed_bets.addAll(bet_opts);
    }

    // Make and shuffle the deck
    public void initDeck() {
        List<String> cards =
                Arrays.asList("A-S", "A-C", "A-H", "A-D", "2-S", "2-C", "2-H", "2-D", "3-S", "3-C", "3-H",
                        "3-D", "4-S", "4-C", "4-H", "4-D", "5-S", "5-C", "5-H", "5-D", "6-S", "6-C", "6-H", "6-D",
                        "7-S", "7-C", "7-H", "7-D", "8-S", "8-C", "8-H", "8-D", "9-S", "9-C", "9-H", "9-D", "X-S",
                        "X-C", "X-H", "X-D", "J-S", "J-C", "J-H", "J-D", "Q-S", "Q-C", "Q-H", "Q-D", "K-S",
                        "K-C", "K-H", "K-D");
        EnhancedBlackJack.deck.addAll(cards);
        Collections.shuffle(EnhancedBlackJack.deck);
    }

    // Get user's wager
    public void betting() {
        int bet;
        String bet_choices = null;

        if(EnhancedBlackJack.player_chips == 0) {
            gameOver();
        } else if(EnhancedBlackJack.player_chips >= 1 && EnhancedBlackJack.player_chips < 25) {
            bet_choices = "1";
        } else if(EnhancedBlackJack.player_chips >= 25 && EnhancedBlackJack.player_chips < 50) {
            bet_choices = "1, 25";
        } else if(EnhancedBlackJack.player_chips >= 50 && EnhancedBlackJack.player_chips < 100) {
            bet_choices = "1, 25, 50";
        } else if(EnhancedBlackJack.player_chips >= 100 && EnhancedBlackJack.player_chips < 500) {
            bet_choices = "1, 25, 50, 100";
        } else if(EnhancedBlackJack.player_chips >= 500 && EnhancedBlackJack.player_chips < 1000) {
            bet_choices = "1, 25, 50, 100, 500";
        } else if(EnhancedBlackJack.player_chips >= 1000) {
            bet_choices = "1, 25, 50, 100, 500, 1000";
        }

        System.out.print("Wager<" + bet_choices + ">: ");
        String temp_bet = sc.nextLine();

        temp_bet = Utilities.stringChooser(temp_bet, allowed_bets);

        switch(temp_bet) {
            case "1" -> bet = 1;
            case "25" -> bet = 25;
            case "50" -> bet = 50;
            case "100" -> bet = 100;
            case "500" -> bet = 500;
            case "1000" -> bet = 1000;
            default -> bet = 0;
        }

        EnhancedBlackJack.pot += bet * 2;
        EnhancedBlackJack.player_chips -= bet;

        previous_bet = bet;
    }

    public void betAgain() {
        String bet_again;

        if(EnhancedBlackJack.player_chips >= previous_bet) {
            System.out.print("Would you like to match your original bet<y/n>: ");
            bet_again = sc.nextLine();

            ArrayList<String> opts = new ArrayList<>();
            opts.add("y");
            opts.add("n");

            bet_again = Utilities.stringChooser(bet_again.toLowerCase(), opts);

            if(bet_again.equals("y")) {
                EnhancedBlackJack.pot += previous_bet * 2;
                EnhancedBlackJack.player_chips -= previous_bet;
            }
        }
    }

    // Calculate the sum of all the elements of an ArrayList in the form "<number>-<suit>"
    public int sumOfCards(ArrayList<String> cards) {
        int sum = 0;
        String str_value;
        int int_value;
        String temp_string;
        int aces_in_hand = 0;

        for(String card : cards) {
            temp_string = card;
            str_value = temp_string.substring(0, 1);

            switch(str_value) {
                case "A" -> { int_value = 11; aces_in_hand += 1; }
                case "2" -> int_value = 2;
                case "3" -> int_value = 3;
                case "4" -> int_value = 4;
                case "5" -> int_value = 5;
                case "6" -> int_value = 6;
                case "7" -> int_value = 7;
                case "8" -> int_value = 8;
                case "9" -> int_value = 9;
                case "X", "J", "Q", "K" -> int_value = 10;
                default -> int_value = 0;
            }
            sum += int_value;
        }

        int temp_aces = aces_in_hand;

        while(sum > 21) {
            if(temp_aces > 0) {
                sum -= 10;
                temp_aces -= 1;
            }
        }

        return sum;
    }

    public void dealPlayer() {
        EnhancedBlackJack.player_cards.add(EnhancedBlackJack.deck.remove(0));
    }

    public void dealBot() {
        EnhancedBlackJack.bot_cards.add(EnhancedBlackJack.deck.remove(0));
    }

    public void dispHand() {

        System.out.println("\nDealer: " + EnhancedBlackJack.bot_cards.get(0));

        System.out.print("\nYou: ");
        for (int i = 0; i < EnhancedBlackJack.player_cards.size(); i++) {
            System.out.print(EnhancedBlackJack.player_cards.get(i) + "    ");
        }

        System.out.println("\nTotal: " + sumOfCards(EnhancedBlackJack.player_cards) + "\n\n");

    }

    public void startingDeal() {
        dealPlayer();
        dealPlayer();
        dealBot();
        dealBot();
    }

    public void checkNatural(ArrayList<String> cards) throws IOException {
        Special special = new Special();

        if (sumOfCards(cards) == 21) {
            System.out.println("BlackJack! Get ready to answer a math question!");
            if (special.mathQuestion()) {
                System.out.println("You got the answer right!");
                EnhancedBlackJack.pot += EnhancedBlackJack.pot;
                endHand(sumOfCards(EnhancedBlackJack.player_cards),
                        sumOfCards(EnhancedBlackJack.bot_cards), EnhancedBlackJack.player_cards);
            }
        }
    }

    public void botHOH(int bot_lvl) {

        Random rnd = new Random();

        int total = sumOfCards(EnhancedBlackJack.bot_cards);


        if(bot_lvl == 1) {
            while(total < 11) {
                dealBot();
                total = sumOfCards(EnhancedBlackJack.bot_cards);
            }

            while(total < 15) {
                dealBot();
                total = sumOfCards(EnhancedBlackJack.bot_cards);
            }

            while(total < 18 && rnd.nextInt((5) + 1) == 1) {
                dealBot();
            }


        } else if(bot_lvl == 2) {

            while(total < 11) {
                dealBot();
                total = sumOfCards(EnhancedBlackJack.bot_cards);
            }

            while(total < 15 && rnd.nextInt((5) + 1) > 3) {
                dealBot();
                total = sumOfCards(EnhancedBlackJack.bot_cards);
            }

            if(total < 18 && rnd.nextInt((10) + 1) == 1) {
                dealBot();
            }


        } else if(bot_lvl == 3) {

            while(total < 14) {
                dealBot();
                total = sumOfCards(EnhancedBlackJack.bot_cards);
            }

            while(total < 16 && rnd.nextInt((5) + 1) > 2) {
                dealBot();
                total = sumOfCards(EnhancedBlackJack.bot_cards);
            }

            if(total > 16 && rnd.nextInt((50) + 1) == 1) {
                dealBot();
            }

        }
    }

    public void hitOrHold() throws IOException {
        if(sumOfCards(EnhancedBlackJack.player_cards) > 21) {
            endHand(sumOfCards(EnhancedBlackJack.player_cards), sumOfCards(EnhancedBlackJack.bot_cards),
                    EnhancedBlackJack.player_cards);
        }

        String hoh;
        System.out.print("<1. Hit, 2. Hold>: ");
        ArrayList<String> hoh_opts = new ArrayList<>();
        hoh_opts.add("1");
        hoh_opts.add("2");
        hoh = sc.nextLine();
        hoh = Utilities.stringChooser(hoh, hoh_opts);

        switch(hoh) {
            case "1":
                dealPlayer();
                dispHand();
                hitOrHold();
            case "2":
                endHand(sumOfCards(EnhancedBlackJack.player_cards),
                        sumOfCards(EnhancedBlackJack.bot_cards), EnhancedBlackJack.player_cards);
            default:
                break;
        }
    }

    public void endHand(int p_sum, int b_sum, ArrayList<String> p_cards) throws IOException {

        Special special = new Special();

        System.out.println("You: " + p_sum);
        System.out.println("House: " + b_sum);

        if(p_sum == 21 && p_cards.size() == 2) {
            EnhancedBlackJack.player_chips += EnhancedBlackJack.pot;

        } else if (p_sum > 21 && b_sum <= 21) {
            System.out.println("\nBUST!\n");
            EnhancedBlackJack.bust_card = Utilities.getLast(p_cards);

        } else if (p_sum > 21) {
            System.out.println("\nYou both went over.\n");
            EnhancedBlackJack.bust_card = Utilities.getLast(p_cards);

        } else if (b_sum > 21) {
            System.out.println("\nThe house went over. You win!\n");

        } else if (p_sum > b_sum) {
            System.out.println("\nYou won the hand!\n");
            boolean bust_win = special.winWithBust();
            if(bust_win) {
                System.out.println("You won an extra 50% of the pot because " +
                        "you won this hand with last hand's bust card.");
            }
            EnhancedBlackJack.player_chips += EnhancedBlackJack.pot;

        } else if (b_sum > p_sum) {
            System.out.println("\nYou lost the hand!\n");

        } else {
            System.out.println("\nYou tied . . . TRIVIA TIME!\n");
            boolean trivia_win = special.trivia();

            if(trivia_win) {
                EnhancedBlackJack.player_chips += EnhancedBlackJack.pot;
            }
        }
        EnhancedBlackJack.pot = 0;
        System.out.println("Chips: " + EnhancedBlackJack.player_chips);
    }

    public boolean gameOver() {
        String choice;
        System.out.println("You lost all your money. That's too bad . . . Play again and try to get rich!");
        System.out.print("Play Again? <y/n>: ");
        choice = sc.nextLine();

        ArrayList<String> pa_opts = new ArrayList<>() {
            {
                add("y");
                add("n");
            }
        };

        choice = Utilities.stringChooser(choice.toLowerCase(), pa_opts);

        switch(choice) {
            case "y" -> { return true; }
            case "n" -> { return false; }
        }
        return false;
    }

    public void hand() throws IOException {

        while (EnhancedBlackJack.player_cards.size() > 0) {
            EnhancedBlackJack.player_cards.remove(0);
        }

        while (EnhancedBlackJack.bot_cards.size() > 0) {
            EnhancedBlackJack.bot_cards.remove(0);
        }

        initDeck();
        startingDeal();
        betting();
        dispHand();
        checkNatural(EnhancedBlackJack.player_cards);
        betAgain();
        botHOH(EnhancedBlackJack.bot_difficulty);
        hitOrHold();
    }

}
