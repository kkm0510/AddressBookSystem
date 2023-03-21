package constants;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressBookConstants {

    public static final int CREATE_ADDRESS_BOOK = 1;
    public static final int CHOOSE_ADDRESS_BOOK = 2;
    public static final int PRINT = 3;
    public static final int SEARCH = 4;
    public static final int COUNT=5;
    public static final int EXIT = 0;

    public static final int ADD_CONTACT = 1;
    public static final int EDIT_CONTACT = 2;
    public static final int DELETE_CONTACT = 5;
    public static final int ENTER_AGAIN = 1;

    public static final int FIRST_NAME = 1;
    public static final int LAST_NAME = 2;
    public static final int ADDRESS = 3;
    public static final int CITY = 4;
    public static final int STATE = 5;
    public static final int PIN = 6;
    public static final int PHONE_NUMBER = 7;
    public static final int EMAIL = 8;

    public static final String NAME_INPUT = "name";
    public static final String FIRST_NAME_INPUT = "first name";
    public static final String LAST_NAME_INPUT = "last name";
    public static final String PIN_INPUT = "pin";
    public static final String PHONE_NUMBER_INPUT = "phone number";
    public static final String CITY_INPUT = "city";
    public static final String STATE_INPUT = "state";
    public static final String EMAIL_INPUT = "email id";
    public static final String ADDRESS_INPUT = "address";

    public static final String VALID_NAME = "^[A-Z][a-z]{2,}$";
    public static final String VALID_PIN = "^[0-9]{6}$";
    public static final String VALID_MOBILE_NUMBER = "^[6-9][0-9]{9}$";
    public static final String VALID_EMAIL = "^[a-zA-Z0-9]+[._+-]?[a-zA-Z0-9]+@[a-zA-Z0-9]+[.]+[a-z]{2,4}[.]?[a-z]{0,3}$";
    public static final String VALID_CITY = "^[A-Z][a-z]{2,}$";
    public static final String VALID_STATE = "^[A-Z][a-z]{2,}([ ][A-Z][a-z]{2,}){0,}$";
    public static final String VALID_EDIT = "^(?!.*([0-8]).*\1)[0-8]([ ][0-8]){0,7}$";
    public static final String VALID_ADDRESS = "^[a-zA-Z0-9 -]{8,}$";

    public static final int SEARCH_CONTACT_BY_NAME=1;
    public static final int SEARCH_CONTACT_IN_CITY=2;
    public static final int SEARCH_CONTACT_IN_STATE=3;

    public static final int PRINT_MAIN_DICTIONARY=1;
    public static final int PRINT_CITY_DICTIONARY=2;
    public static final int PRINT_STATE_DICTIONARY=3;

    public static final int COUNT_IN_CITY_DICTIONARY=1;
    public static final int COUNT_IN_STATE_DICTIONARY=2;

    public static String takeValidInput(String type) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter " + type + " : ");
            String name = sc.nextLine();
            Pattern p = null;
            switch (type) {
                case FIRST_NAME_INPUT, LAST_NAME_INPUT -> p = Pattern.compile(VALID_NAME);
                case PHONE_NUMBER_INPUT -> p = Pattern.compile(VALID_MOBILE_NUMBER);
                case PIN_INPUT -> p = Pattern.compile(VALID_PIN);
                case CITY_INPUT -> p = Pattern.compile(VALID_CITY);
                case STATE_INPUT -> p = Pattern.compile(VALID_STATE);
                case EMAIL_INPUT -> p = Pattern.compile(VALID_EMAIL);
                case ADDRESS_INPUT -> p = Pattern.compile(VALID_ADDRESS);
            }
            Matcher m = p.matcher(name);
            if (m.matches()) return name;
            else System.out.println("Illegal Input!!!");
        }
    }

}
