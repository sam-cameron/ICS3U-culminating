package culminating.SamCameron;

import java.util.ArrayList;
import java.util.Scanner;

public class Utilities {

    private static final Scanner sc = new Scanner(System.in);

    public static String stringChooser(String input, ArrayList<String> options) {

        if(!options.contains(input)) {
            System.out.print("Please choose one of the options provided: ");
            input = sc.nextLine();
            input = stringChooser(input, options);
        }

        return input;
    }

    // Shortcut to get last element in an ArrayList
    public static String getLast(ArrayList<String> arraylist) {
        return arraylist.get(arraylist.size() -1);
    }
}
