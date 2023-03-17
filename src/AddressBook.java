
import static constants.AddressBookConstants.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressBook {

    Map<String, ArrayList<Contact>> dictionary;

    public AddressBook() {
        dictionary = new HashMap<>();
    }

    private boolean searchInBook(String bookName, ArrayList<Contact> book, String input, int searchType, int callType) {
        boolean contactFound = false;
        for (Contact contact : book) {
            if (searchType == SEARCH_BY_NAME
                    && (contact.getFirstName() + " " + contact.getLastName()).equals(input)
                    || searchType == SEARCH_BY_PHONE_NUMBER
                    && contact.getPhoneNumber().equals(input)) {
                if (callType == SEARCH_IN_DICTIONARY)
                    System.out.println("Contact found in : " + bookName + " address book");
                System.out.println(contact + "\n");
                contactFound = true;
            }
        }
        return contactFound;
    }

    private void searchInDictionary(String input, int searchType) {
        boolean contactFound = false;
        int trueCount = 0;
        for (Map.Entry<String, ArrayList<Contact>> book : dictionary.entrySet()) {
            contactFound = searchInBook(book.getKey(), book.getValue(),
                    input, searchType, SEARCH_IN_DICTIONARY);
            if (contactFound) trueCount++;
        }
        if (!contactFound && trueCount == 0) System.out.println("Contact not found \n");
    }

    public void search(int callType, String bookName) {
        if (dictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        if (callType == SEARCH_IN_BOOK && dictionary.get(bookName).size() == 0) {
            System.out.println("Address book is empty");
            return;
        }
        String firstName, lastName, phoneNumber;
        Scanner sc = new Scanner(System.in);
        System.out.print("\nChoose : \n(1) Search by Name (2)Search by Phone Number (0)Go back to previous menu : ");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case SEARCH_BY_NAME -> {
                firstName = takeValidInput(FIRST_NAME_INPUT);
                lastName = takeValidInput(LAST_NAME_INPUT);
                if (callType == SEARCH_IN_BOOK) {
                    boolean contactFound = searchInBook(bookName,
                            dictionary.get(bookName), firstName + " " + lastName,
                            SEARCH_BY_NAME, SEARCH_IN_BOOK);
                    if (!contactFound) System.out.println("Contact not found \n");
                } else {
                    searchInDictionary(firstName + " " + lastName, SEARCH_BY_NAME);
                }
            }
            case SEARCH_BY_PHONE_NUMBER -> {
                phoneNumber = takeValidInput(PHONE_NUMBER_INPUT);
                if (callType == SEARCH_IN_BOOK) {
                    boolean contactFound = searchInBook(bookName,
                            dictionary.get(bookName), phoneNumber,
                            SEARCH_BY_PHONE_NUMBER, SEARCH_IN_BOOK);
                    if (!contactFound) System.out.println("Contact not found \n");
                } else {
                    searchInDictionary(phoneNumber, SEARCH_BY_PHONE_NUMBER);
                }
            }
            case EXIT -> {
            }
            default -> System.out.println("Wrong Input");
        }
    }

    private void takeInputInContact(Contact contact) {
        contact.setFirstName(takeValidInput(FIRST_NAME_INPUT));
        contact.setLastName(takeValidInput(LAST_NAME_INPUT));
        contact.setAddress(takeValidInput(ADDRESS_INPUT));
        contact.setCity(takeValidInput(CITY_INPUT));
        contact.setState(takeValidInput(STATE_INPUT));
        contact.setPin(takeValidInput(PIN_INPUT));
        contact.setPhoneNumber(takeValidInput(PHONE_NUMBER_INPUT));
        contact.setEmail(takeValidInput(EMAIL_INPUT));
    }

    private void editContact(ArrayList<Contact> book) {
        if (book.size() == 0) {
            System.out.println("AddressBook is empty");
            return;
        }
        String firstName = takeValidInput(FIRST_NAME_INPUT);
        String lastName = takeValidInput(LAST_NAME_INPUT);
        for (Contact contact : book) {
            if (contact.getFirstName().equals(firstName) && contact.getLastName().equals(lastName)) {
                String toEdit = inputToEdit();
                for (int i = 0; i < toEdit.length(); i++) {
                    if (toEdit.charAt(i) == ' ') continue;
                    int choice = toEdit.charAt(i) - 48;
                    switch (choice) {
                        case FIRST_NAME -> contact.setFirstName(takeValidInput(FIRST_NAME_INPUT));
                        case LAST_NAME -> contact.setLastName(takeValidInput(LAST_NAME_INPUT));
                        case ADDRESS -> contact.setAddress(takeValidInput(ADDRESS_INPUT));
                        case CITY -> contact.setCity(takeValidInput(CITY_INPUT));
                        case STATE -> contact.setState(takeValidInput(STATE_INPUT));
                        case PIN -> contact.setPin(takeValidInput(PIN_INPUT));
                        case PHONE_NUMBER -> contact.setPhoneNumber(takeValidInput(PHONE_NUMBER_INPUT));
                        case EMAIL -> contact.setEmail(takeValidInput(EMAIL_INPUT));
                        case EXIT -> {
                            return;
                        }
                        default -> System.out.println("Wrong input");
                    }
                }
                System.out.println("Contact edited successfully");
                return;
            }
        }
        System.out.println("Contact doesn't exist in address book");
    }

    private void deleteContact(ArrayList<Contact> book) {
        if (book.size() == 0) {
            System.out.println("AddressBook is empty");
            return;
        }
        String firstName = takeValidInput(FIRST_NAME_INPUT);
        String lastName = takeValidInput(LAST_NAME_INPUT);
        for (Contact c : book) {
            if (c.getFirstName().equals(firstName) && c.getLastName().equals(lastName)) {
                book.remove(c);
                System.out.println("Contact deleted");
                return;
            }
        }
        System.out.println("Contact doesn't exist");
    }

    private void addContacts(ArrayList<Contact> book) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nHow many contacts do you want to add? ");
        int noOfContacts = sc.nextInt();
        int count = 0;
        while (count < noOfContacts) {
            Contact person = new Contact();
            takeInputInContact(person);
            book.add(person);
            count++;
        }
    }

    public void operateBook(String bookName, ArrayList<Contact> book) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("\n" + bookName + " -> \n(1)Add contacts (2)Edit contact " +
                    "(3)Delete contact (4)Search contact (5)Print Address Book  (0)Go back to main menu -> ");
            int choice = sc.nextInt();
            switch (choice) {
                case ADD_CONTACT -> addContacts(book);
                case EDIT_CONTACT -> editContact(book);
                case DELETE_CONTACT -> deleteContact(book);
                case SEARCH_CONTACT -> search(SEARCH_IN_BOOK, bookName);
                case PRINT_BOOK -> {
                    if (book.size() == 0) System.out.println("AddressBook is empty");
                    else printAddressBook(book);
                }
                case EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input");
            }
        }
    }

    public void createAddressBook() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter name of address book : ");
        String name = sc.nextLine();
        if (dictionary.containsKey(name)) {
            System.out.println("Book already exists \n");
            return;
        }
        ArrayList<Contact> book = new ArrayList<>();
        dictionary.put(name, book);
        operateBook(name, book);
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
            operateBook(name, dictionary.get(name));
        else
            System.out.println("Book doesn't exist \n");
    }

    private String takeValidInput(String type) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter " + type + " : ");
            String name = sc.nextLine();
            Pattern p = null;
            switch(type){
                case FIRST_NAME_INPUT, LAST_NAME_INPUT -> p = Pattern.compile(VALID_NAME);
                case PHONE_NUMBER_INPUT -> p = Pattern.compile(VALID_MOBILE_NUMBER);
                case PIN_INPUT -> p = Pattern.compile(VALID_PIN);
                case CITY_INPUT -> p = Pattern.compile(VALID_CITY);
                case STATE_INPUT -> p = Pattern.compile(VALID_STATE);
                case EMAIL_INPUT -> p = Pattern.compile(VALID_EMAIL);
                case ADDRESS_INPUT -> p=Pattern.compile(VALID_ADDRESS);
            }
            Matcher m = p.matcher(name);
            if (m.matches()) return name;
            else System.out.println("Illegal Input!!!");
        }
    }

    private String inputToEdit() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("\nWhat all do you want to edit? \n(1)First name (2)Last name" +
                    " (3)Address (4)City (5)State (6)Pin (7)Phone number (8)Email -> ");
            String toEdit = sc.nextLine();
            Pattern p = Pattern.compile(VALID_EDIT);
            Matcher m = p.matcher(toEdit);
            if (m.matches()) return toEdit;
            else System.out.println("Illegal Input!!!");
        }
    }

    private void printAddressBook(ArrayList<Contact> book) {
        for (Contact contact : book)
            System.out.println(contact);
        System.out.println();
    }

    public void printDictionary() {
        if (dictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        for (Map.Entry<String, ArrayList<Contact>> book : dictionary.entrySet()) {
            if (book.getValue().size() == 0) continue;
            System.out.println(book.getKey() + " -> ");
            printAddressBook(book.getValue());
        }
    }

}
