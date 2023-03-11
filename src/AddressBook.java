import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {

    static Scanner sc = new Scanner(System.in);
    Map<String, ArrayList<Contact>> DICTIONARY;

    public AddressBook() {
        DICTIONARY = new HashMap<>();
    }

    //searches contact in the entire dictionary
    public void searchByNumber() {
        System.out.print("Enter phone number : ");
        long phoneNumber = sc.nextLong();
        sc.nextLine();
        boolean contactFound = false;
        for (Map.Entry<String, ArrayList<Contact>> book : DICTIONARY.entrySet()) {
            contactFound = searchInBookByNumber(book.getValue(), book.getKey(), phoneNumber);
        }
        if (!contactFound) System.out.println("Contact not found");
    }

    private boolean searchInBookByNumber(ArrayList<Contact> book, String bookName, long phoneNumber) {
        boolean contactFound = false;
        for (Contact contact : book) {
            if (contact.getPhoneNumber() == phoneNumber) {
                System.out.println("Contact found in : " + bookName + " book");
                System.out.println(contact);
            }
        }
        return contactFound;
    }

    //searches contact in the entire dictionary
    public void searchByName() {
        System.out.print("Enter first name : ");
        String firstName = sc.next();
        System.out.print("Enter last name : ");
        String lastName = sc.next();
        boolean contactFound = false;
        for (Map.Entry<String, ArrayList<Contact>> book : DICTIONARY.entrySet()) {
            contactFound = searchInBookByName(book.getValue(), book.getKey(), firstName, lastName);
        }
        if (!contactFound) System.out.println("Contact not found");
    }

    private boolean searchInBookByName(ArrayList<Contact> book, String bookName, String firstName, String lastName) {
        boolean contactFound = false;
        for (Contact contact : book) {
            if (contact.getFirstName().equals(firstName) && contact.getLastName().equals(lastName)) {
                System.out.println("Contact found in : " + bookName + " book");
                System.out.println(contact);
            }
        }
        return contactFound;
    }

    public void takeInputInContact(Contact contact) {
        System.out.print("Enter first name : ");
        contact.setFirstName(sc.next());
        sc.nextLine();
        System.out.print("Enter last name : ");
        contact.setLastName(sc.next());
        sc.nextLine();
        System.out.print("Enter address : ");
        contact.setAddress(sc.nextLine());
        System.out.print("Enter city : ");
        contact.setCity(sc.next());
        sc.nextLine();
        System.out.print("Enter state : ");
        contact.setState(sc.nextLine());
        System.out.print("Enter pin : ");
        contact.setPin(sc.nextInt());
        sc.nextLine();
        System.out.print("Enter phone number : ");
        contact.setPhoneNumber(sc.nextLong());
        sc.nextLine();
        System.out.print("Enter email : ");
        contact.setEmail(sc.nextLine());
        System.out.println();
    }

    public void editContact(ArrayList<Contact> book) {
        final int FIRST_NAME = 1;
        final int LAST_NAME = 2;
        final int ADDRESS = 3;
        final int CITY = 4;
        final int STATE = 5;
        final int PIN = 6;
        final int PHONE_NUMBER = 7;
        final int EMAIL = 8;
        final int EVERYTHING = 9;

        System.out.print("Enter first name : ");
        String firstName = sc.next();
        System.out.print("Enter last name : ");
        String lastName = sc.next();

        for (Contact contact : book) {
            if (contact.getFirstName().equals(firstName) && contact.getLastName().equals(lastName)) {
                sc.nextLine();
                System.out.print("What do you want to edit? (1)first name (2)last name (3)address (4)city (5)state (6)pin (7)phone number (8)email (9)everything -> ");
                int choice = sc.nextInt();
                switch (choice) {
                    case FIRST_NAME -> {
                        System.out.print("Enter new first name : ");
                        contact.setFirstName(sc.next());
                    }
                    case LAST_NAME -> {
                        System.out.print("Enter new last name : ");
                        contact.setLastName(sc.next());
                    }
                    case ADDRESS -> {
                        System.out.print("Enter new address : ");
                        contact.setAddress(sc.nextLine());
                    }
                    case CITY -> {
                        System.out.print("Enter new city : ");
                        contact.setCity(sc.next());
                    }
                    case STATE -> {
                        System.out.print("Enter new state : ");
                        contact.setState(sc.nextLine());
                    }
                    case PIN -> {
                        System.out.print("Enter new pin : ");
                        contact.setPin(sc.nextInt());
                    }
                    case PHONE_NUMBER -> {
                        System.out.print("Enter new phone number : ");
                        contact.setPhoneNumber(sc.nextLong());
                    }
                    case EMAIL -> {
                        System.out.print("Enter new EMAIL : ");
                        contact.setEmail(sc.nextLine());
                    }
                    case EVERYTHING -> takeInputInContact(contact);
                    default -> System.out.println("Wrong input");
                }
                System.out.println("Contact edited successfully");
                System.out.println();
                return;
            }
        }
        System.out.println("Contact doesn't exist in address book");
        System.out.println();
    }

    public void deleteContact(ArrayList<Contact> book) {
        System.out.print("Enter first name : ");
        String firstName = sc.next();
        System.out.print("Enter last name : ");
        String lastName = sc.next();

        for (Contact c : book) {
            if (c.getFirstName().equals(firstName) && c.getLastName().equals(lastName)) {
                book.remove(c);
                System.out.println("Contact deleted");
                System.out.println();
                return;
            }
        }
        System.out.println("Contact doesn't exist");
        System.out.println();
    }

    public void operateBook(String bookName, ArrayList<Contact> book) {
        final int ADD_CONTACT = 1;
        final int EDIT_CONTACT = 2;
        final int DELETE_CONTACT = 3;
        final int PRINT_BOOK = 4;
        final int BACK_TO_MAIN_MENU = 0;
        while (true) {
            System.out.print(bookName + " -> Enter choice : (1)Add contact (2)Edit contact (3)Delete contact (4)Print Address Book (0)Go back to main menu -> ");
            int choice = sc.nextInt();
            switch (choice) {
                case ADD_CONTACT -> {
                    Contact person = new Contact();
                    takeInputInContact(person);
                    book.add(person);
                }
                case EDIT_CONTACT -> editContact(book);
                case DELETE_CONTACT -> deleteContact(book);
                case PRINT_BOOK -> {
                    System.out.println(book);
                    System.out.println();
                }
                case BACK_TO_MAIN_MENU -> {
                    return;
                }
                default -> System.out.println("Wrong input");
            }
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Map.Entry<String, ArrayList<Contact>> book : DICTIONARY.entrySet()) {
            s.append(book).append("   ");
        }
        return s.toString();
    }

    public void createAddressBook() {
        System.out.print("Enter name of address book : ");
        String name = sc.nextLine();
        if (DICTIONARY.containsKey(name)) {
            System.out.println("Book already exists");
            System.out.println();
            return;
        }
        ArrayList<Contact> book = new ArrayList<>();
        DICTIONARY.put(name, book);
        operateBook(name, book);
    }

    public void chooseAddressBook() {
        if(DICTIONARY.size()==0){
            System.out.println("No address book present");
        }
        System.out.println("Address Books :- ");
        for(String name : DICTIONARY.keySet()){
            System.out.println(name);
        }
        System.out.print("Enter name of address book : ");
        String name = sc.nextLine();
        if (DICTIONARY.containsKey(name))
            operateBook(name, DICTIONARY.get(name));
        else
            System.out.println("Book doesn't exist");
        System.out.println();
    }

    public static void main(String[] args) {
        final int CREATE_ADDRESS_BOOK = 1;
        final int CHOOSE_ADDRESS_BOOK = 2;
        final int PRINT_ALL_BOOKS = 3;
        final int SEARCH_CONTACT_BY_NAME = 4;
        final int SEARCH_CONTACT_BY_PHONE_NUMBER = 5;
        final int EXIT = 0;
        AddressBook addressBook = new AddressBook();

        while (true) {
            System.out.println();
            System.out.print("Enter choice : (1)Create new address book (2)Choose an address book (3)Print dictionary (4)Search contact by name (5)Search contact by phone number (0)Exit : ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case CREATE_ADDRESS_BOOK -> addressBook.createAddressBook();
                case CHOOSE_ADDRESS_BOOK -> addressBook.chooseAddressBook();
                case PRINT_ALL_BOOKS -> {
                    System.out.println(addressBook);
                    System.out.println();
                }
                case SEARCH_CONTACT_BY_NAME -> addressBook.searchByName();
                case SEARCH_CONTACT_BY_PHONE_NUMBER -> addressBook.searchByNumber();
                case EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input");
            }
        }
    }
}
