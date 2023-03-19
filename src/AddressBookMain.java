
import java.util.Scanner;


import static constants.AddressBookConstants.*;

public class AddressBookMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AddressBookDictionary addressBookDictionary = new AddressBookDictionary();
        while (true) {
            System.out.print("\nMain menu -> \n(1)Create new address book " +
                    "(2)Choose an address book (3)Print dictionary (4)Search menu (0)Exit : ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case CREATE_ADDRESS_BOOK -> addressBookDictionary.createAddressBook();
                case CHOOSE_ADDRESS_BOOK -> addressBookDictionary.chooseAddressBook();
                case PRINT_DICTIONARY -> addressBookDictionary.printDictionary();
                case SEARCH -> addressBookDictionary.searchMenu();
                case EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input!!!");
            }
        }
    }
}
