package culminating.SamCameron;

import java.io.*;
import java.util.*;

public class Special {

    private final Scanner sc = new Scanner(System.in);

    ArrayList<String> questions = new ArrayList<>();

    public boolean mathQuestion() {

        Random rnd = new Random();
        Scanner sc = new Scanner(System.in);

        ArrayList<String> operators = new ArrayList<>();
        operators.add(" + ");
        operators.add(" - ");
        operators.add(" * ");
        operators.add(" / ");

        String operator = operators.get(rnd.nextInt(3));

        int num1 = rnd.nextInt(20);
        int num2 = (rnd.nextInt(20) + 1);

        int answer;


        switch (operator) {
            case " + ":
                System.out.print(num1 + operator + num2 + " = ");
                answer = sc.nextInt();

                // Consume the new line so it doesn't pass over an input
                sc.nextLine();

                return answer == num1 + num2;


            case " - ":
                System.out.print(num1 + operator + num2 + " = ");
                answer = sc.nextInt();

                // Consume the new line so it doesn't pass over an input
                sc.nextLine();

                return answer == num1 - num2;


            case " * ":
                System.out.print(num1 + operator + num2 + " = ");
                answer = sc.nextInt();

                // Consume the new line so it doesn't pass over an input
                sc.nextLine();

                return answer == num1 * num2;


            case " / ":
                System.out.print(num1 + operator + num2 + " (rounded down to the nearest tenth) = ");
                answer = sc.nextInt();

                // Consume the new line so it doesn't pass over an input
                sc.nextLine();

                return answer == num1 / num2;


            default:
                return false;
        }
    }

    public boolean winWithBust() {
        if (EnhancedBlackJack.player_cards.contains(EnhancedBlackJack.bust_card)) {
            EnhancedBlackJack.pot += EnhancedBlackJack.pot / 2;
            return true;
        }
        return false;
    }

    public boolean trivia() throws IOException {
        Random rnd = new Random();

        BufferedReader reader = new BufferedReader(new FileReader("trivia.txt"));
        String line = reader.readLine();
        ArrayList<String> all_questions = new ArrayList<>() {
            {
                add("How tall is the CN Tower?");
                add("What is the only edible food that never expires?");
                add("Approximately, how many times larger is the sun than the Earth (volume)?");
                add("When was Starbucks established?");
                add("What was the name of Adolf Hitler's political party (abbr.)?");
                add("Early American colonists boiled blueberries in milk to make what?");
                add("In what state of matter are atoms most tightly packed?");
                add("What is the fear of long words known as?");
                add("What does NATO stand for?");
                add("What is a female donkey called?");
            }
        };

        if(questions.size() == 0) {
            questions.addAll(all_questions);
        }

        int index = rnd.nextInt(questions.size());

        String question = questions.remove(index);

        while(!line.equals(question)) {
            line = reader.readLine();
        }

        System.out.println(question);
        while(!line.equals("+")) {
            line = reader.readLine();
            System.out.println(line);
        }
        System.out.print("Answer: ");
        String answer = sc.nextLine();
        answer = answer.toUpperCase();

        ArrayList<String> options = new ArrayList<>() {
            {
                add("A");
                add("B");
                add("C");
                add("D");
            }
        };

        answer = Utilities.stringChooser(answer, options);
        line = reader.readLine();

        if(answer.equals(line)) {
            System.out.println("\nYou got the answer right! You won the hand.\n");
            return true;
        } else {
            System.out.println("\nYou got the answer wrong . . . You lost the hand.\n");
            return false;
        }
    }

}
