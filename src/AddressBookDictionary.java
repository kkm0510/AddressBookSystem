import java.util.*;

public class AddressBookDictionary {

    Map<String, AddressBook> dictionary;

    public AddressBookDictionary() {
        dictionary = new HashMap<>();
    }

    public void createAddressBook() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter name of address book : ");
        String name = sc.nextLine();
        if (dictionary.containsKey(name)) {
            System.out.println("Book already exists \n");
            return;
        }
        AddressBook addressBook = new AddressBook(name);
        dictionary.put(name, addressBook);
        addressBook.operateBook();
    }

    public void chooseAddressBook() {
        Scanner sc = new Scanner(System.in);
        if (dictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        System.out.println("\nAddress Books :- ");
        for (String name : dictionary.keySet()) {
            System.out.println(name);
        }
        System.out.print("Choose address book : ");
        String name = sc.nextLine();
        if (dictionary.containsKey(name))
            dictionary.get(name).operateBook();
        else
            System.out.println("Book doesn't exist \n");
    }

    public void printDictionary() {
        if (dictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        dictionary.entrySet().stream()
                .filter(entry -> entry.getValue().book.size() != 0)
                .forEach(entry -> System.out.println(entry.getKey() + " -> \n" + entry.getValue()));
    }
}