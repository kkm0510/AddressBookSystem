
import java.util.Scanner;
import static constants.AddressBookConstants.*;

public class AddressBookMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AddressBook addressBook = new AddressBook();
        while (true) {
            System.out.print("\nMain menu -> \n(1)Create new address book " +
                    "(2)Choose an address book (3)Print dictionary (4)Search contact (0)Exit : ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case CREATE_ADDRESS_BOOK -> addressBook.createAddressBook();
                case CHOOSE_ADDRESS_BOOK -> addressBook.chooseAddressBook();
                case PRINT_DICTIONARY -> addressBook.printDictionary();
                case SEARCH_CONTACT ->
                        addressBook.search(SEARCH_IN_DICTIONARY, "");
                case EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input");
            }
        }
    }

}
