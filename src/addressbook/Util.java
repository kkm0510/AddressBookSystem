package addressbook;

import java.util.Scanner;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Util {

    public static final int CREATE_ADDRESS_BOOK = 1;
    public static final int CHOOSE_ADDRESS_BOOK = 2;
    public static final int PRINT = 3;
    public static final int SEARCH = 4;
    public static final int COUNT=5;
    public static final int SORT=6;
    public static final int EXIT = 0;

    public static final int ADD_CONTACT = 1;
    public static final int EDIT_CONTACT = 2;
    public static final int DELETE_CONTACT = 5;
    public static final int ENTER_AGAIN = 1;

    public static final int SEARCH_CONTACT_BY_NAME=1;
    public static final int SEARCH_CONTACT_IN_CITY=2;
    public static final int SEARCH_CONTACT_IN_STATE=3;

    public static final int PRINT_MAIN_DICTIONARY=1;
    public static final int PRINT_CITY_DICTIONARY=2;
    public static final int PRINT_STATE_DICTIONARY=3;

    public static final int COUNT_IN_CITY_DICTIONARY=1;
    public static final int COUNT_IN_STATE_DICTIONARY=2;


    public static String takeValidInput(InputEnum type) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter " + type.getValue() + " : ");
            String input = sc.nextLine();
            if (Pattern.compile(type.getRegex()).matcher(input).matches()) return input;
            else System.out.println("Illegal Input!!!");
        }
    }

    public static Predicate<Contact> matchingName(String firstName, String lastName){
        return c -> c.getFirstName().equals(firstName) && c.getLastName().equals(lastName);
    }
}
