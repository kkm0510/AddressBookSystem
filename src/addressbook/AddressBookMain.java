package addressbook;

import java.util.Scanner;
import static addressbook.Util.*;

public class AddressBookMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AddressBookDictionary addressBookDictionary = new AddressBookDictionary();
        while (true) {
            System.out.print("\nMain menu -> \n(1)Create new address book " +
                    "(2)Choose an address book (3)Print (4)Search (5)Count (6)Sort (0)Exit : ");
            int choice =  sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case CREATE_ADDRESS_BOOK -> addressBookDictionary.createAddressBook();
                case CHOOSE_ADDRESS_BOOK -> addressBookDictionary.chooseAddressBook();
                case PRINT -> addressBookDictionary.printMenu();
                case SEARCH -> addressBookDictionary.searchMenu();
                case COUNT -> addressBookDictionary.countMenu();
                case SORT -> addressBookDictionary.sort();
                case EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input!!!");
            }
        }
    }
}
