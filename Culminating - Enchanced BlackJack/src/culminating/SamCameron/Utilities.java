package culminating.SamCameron;

import java.util.ArrayList;
import java.util.Scanner;

public class Utilities {

    private static final Scanner sc = new Scanner(System.in);

    // Possible use later
    /*// Make sure the number provided by the user is valid
    public static int numberChooser(int num, ArrayList<Integer> options, int upper_bound) {
    
    if(!(options.contains(num)) || num > upper_bound) {
    System.out.print("Please enter an acceptable number (one of the options provided): ");
    num = sc.nextInt();
    
    // Consume the new line
    sc.nextLine();
    
    numberChooser(num, options, upper_bound);
    }
    
    return num;
    }*/

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
