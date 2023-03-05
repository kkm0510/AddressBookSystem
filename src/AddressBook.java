import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {

    static Scanner sc = new Scanner(System.in);
    static final Map<String, ArrayList<Contact>> DICTIONARY = new HashMap<>();
    ArrayList<Contact> book;

    public AddressBook() {
        book = new ArrayList<>();
    }

    public static void editContact(ArrayList<Contact> book) {
        final int FIRST_NAME = 1;
        final int LAST_NAME = 2;
        final int ADDRESS = 3;
        final int CITY = 4;
        final int STATE = 5;
        final int PIN = 6;
        final int PHONE_NUMBER = 7;
        final int EMAIL = 8;
        final int EVERYTHING=9;

        System.out.print("Enter first name : ");
        String firstName = sc.next();
        System.out.print("Enter last name : ");
        String lastName = sc.next();

        for (int i=0; i< book.size(); i++) {
            if (book.get(i).firstName.equals(firstName) && book.get(i).lastName.equals(lastName)) {
                sc.nextLine();
                System.out.print("What do you want to edit? (1)first name (2)last name (3)address (4)city (5)state (6)pin (7)phone number (8)email (9)everything -> ");
                int choice = sc.nextInt();
                switch (choice) {
                    case FIRST_NAME -> {
                        System.out.print("Enter new first name : ");
                        book.get(i).firstName = sc.next();
                    }
                    case LAST_NAME -> {
                        System.out.print("Enter new last name : ");
                        book.get(i).lastName = sc.next();
                    }
                    case ADDRESS -> {
                        System.out.print("Enter new address : ");
                        book.get(i).address = sc.nextLine();
                    }
                    case CITY -> {
                        System.out.print("Enter new city : ");
                        book.get(i).city = sc.next();
                    }
                    case STATE -> {
                        System.out.print("Enter new state : ");
                        book.get(i).state = sc.nextLine();
                    }
                    case PIN -> {
                        System.out.print("Enter new pin : ");
                        book.get(i).pin = sc.nextInt();
                    }
                    case PHONE_NUMBER -> {
                        System.out.print("Enter new phone number : ");
                        book.get(i).phoneNumber = sc.nextLong();
                    }
                    case EMAIL -> {
                        System.out.print("Enter new EMAIL : ");
                        book.get(i).email = sc.nextLine();
                    }
                    case EVERYTHING -> book.set(i, new Contact());
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

    public static void deleteContact(ArrayList<Contact> book) {
        System.out.print("Enter first name : ");
        String firstName = sc.next();
        System.out.print("Enter last name : ");
        String lastName = sc.next();

        for (Contact c : book) {
            if (c.firstName.equals(firstName) && c.lastName.equals(lastName)) {
                book.remove(c);
                System.out.println("Contact deleted");
                System.out.println();
                return;
            }
        }
        System.out.println("Contact doesn't exist");
        System.out.println();
    }

    public static void printAddressBook(ArrayList<Contact> book) {
        for (Contact c : book) {
            System.out.println("~~~~~~");
            System.out.println("Name : " + c.firstName + " " + c.lastName);
            System.out.println("Address : " + c.address);
            System.out.println("City : " + c.city);
            System.out.println("State : " + c.state);
            System.out.println("Pin : " + c.pin);
            System.out.println("Phone No : " + c.phoneNumber);
            System.out.println("Email : " + c.email);
            System.out.println("~~~~~~");
        }
    }

    public static void operateBook(String bookName, ArrayList<Contact> book) {
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
                    book.add(person);
                }
                case EDIT_CONTACT -> editContact(book);
                case DELETE_CONTACT -> deleteContact(book);
                case PRINT_BOOK -> printAddressBook(book);
                case BACK_TO_MAIN_MENU -> {
                    return;
                }
                default -> System.out.println("Wrong input");
            }
        }
    }

    public static void printDictionary() {
        for (Map.Entry<String, ArrayList<Contact>> entry : DICTIONARY.entrySet()) {
            System.out.println("~~~~" + entry.getKey() + "~~~~~");
            printAddressBook(entry.getValue());
        }
    }

    public static void createAddressBook() {
        System.out.print("Enter name of address book : ");
        String name = sc.nextLine();
        if (DICTIONARY.containsKey(name)) {
            System.out.println("Book already exists");
            System.out.println();
            return;
        }
        AddressBook address = new AddressBook();
        DICTIONARY.put(name, address.book);
        operateBook(name, address.book);
    }

    public static void chooseAddressBook() {
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
        final int EXIT = 0;
        while (true) {
            System.out.println();
            System.out.print("Enter choice : (1)Create new address book (2)Choose an address book (3)Print dictionary (0)Exit : ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case CREATE_ADDRESS_BOOK -> createAddressBook();
                case CHOOSE_ADDRESS_BOOK -> chooseAddressBook();
                case PRINT_ALL_BOOKS -> printDictionary();
                case EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input");
            }
        }
    }
}
