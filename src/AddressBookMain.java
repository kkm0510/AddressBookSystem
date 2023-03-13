import java.util.Scanner;

public class AddressBookMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AddressBook addressBook = new AddressBook();
        while (true) {
            System.out.print("\nEnter choice : \n(1)Create new address book " +
                    "(2)Choose an address book (3)Print dictionary (4)Search contact (0)Exit : ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case AddressBookConstants.CREATE_ADDRESS_BOOK -> addressBook.createAddressBook();
                case AddressBookConstants.CHOOSE_ADDRESS_BOOK -> addressBook.chooseAddressBook();
                case AddressBookConstants.PRINT_ALL_BOOKS -> {
                    if (addressBook.dictionary.size() == 0) System.out.println("No address book present");
                    else System.out.println(addressBook.dictionary);
                }
                case AddressBookConstants.SEARCH_CONTACT -> addressBook.searchContact();
                case AddressBookConstants.EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input");
            }
        }
    }

}
