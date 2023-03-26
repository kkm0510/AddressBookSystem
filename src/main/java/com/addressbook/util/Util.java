package com.addressbook.util;

import com.addressbook.enums.WhereToPrintEnum;
import com.addressbook.exceptions.AddressBookException;
import com.addressbook.models.Contact;
import com.addressbook.enums.InputEnum;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import java.util.function.Predicate;

public class Util {

    public static final int CREATE_ADDRESS_BOOK = 1;
    public static final int CHOOSE_ADDRESS_BOOK = 2;
    public static final int FILE_IO = 3;
    public static final int PRINT = 4;
    public static final int SEARCH = 5;
    public static final int COUNT = 6;
    public static final int SORT = 7;
    public static final int EXIT = 0;

    public static final int ADD_CONTACT = 1;
    public static final int EDIT_CONTACT = 2;
    public static final int DELETE_CONTACT = 3;
    public static final int ENTER_AGAIN = 1;

    public static final int SEARCH_CONTACT_BY_NAME = 1;
    public static final int SEARCH_CONTACT_IN_CITY = 2;
    public static final int SEARCH_CONTACT_IN_STATE = 3;

    public static final int PRINT_MAIN_DICTIONARY = 1;
    public static final int PRINT_CITY_DICTIONARY = 2;
    public static final int PRINT_STATE_DICTIONARY = 3;

    public static final int READ_CSV = 1;
    public static final int READ_JSON = 2;
    public static final int WRITE_CSV = 3;
    public static final int WRITE_JSON = 4;

    public static String takeValidInput(InputEnum type) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter " + type.getValue() + " : ");
            String input = sc.nextLine();
            if (input.matches(type.getRegex())) return input;
            else try {
                throw new AddressBookException("Invalid Input!!!");
            } catch (AddressBookException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static Predicate<Contact> matchingName(String firstName, String lastName) {
        return c -> c.getFirstName().equals(firstName) && c.getLastName().equals(lastName);
    }

    public static void whereToPrintContactsList(List<Contact> list) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("\nWhere to print this list -> (1)Console (2)CSV file (3)JSON file (0)Go back to previous menu : ");
                int choice = sc.nextInt();
                sc.nextLine();
                if (choice == 0) return;
                WhereToPrintEnum.values()[choice - 1].printContactList(list);
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!!!");
                sc.nextLine();
            }
        }
    }
}
