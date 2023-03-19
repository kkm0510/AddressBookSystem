import static constants.AddressBookConstants.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressBook {

    String bookName;
    List<Contact> book;

    public AddressBook(String bookName) {
        this.bookName = bookName;
        book = new LinkedList<>();
    }

    public void operateBook() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("\n" + bookName + " -> \n(1)Add contacts (2)Edit contact " +
                    "(3)Delete contact (4)Search contact (5)Print Address Book  (0)Go back to main menu -> ");
            int choice = sc.nextInt();
            switch (choice) {
                case ADD_CONTACT -> addContacts();
                case EDIT_CONTACT -> editContact();
                case DELETE_CONTACT -> deleteContact();
                case SEARCH_CONTACT -> takeInputAndSearchContact();
                case PRINT_BOOK -> {
                    if (book.size() == 0) System.out.println("AddressBook is empty");
                    else System.out.println(this);
                }
                case EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input");
            }
        }
    }

    private void addContacts() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nHow many contacts do you want to add? ");
        int noOfContacts = sc.nextInt();
        int count = 0;
        while (count < noOfContacts) {
            Contact person = new Contact();
            int choice = takeInputInContact(person);
            if (choice == 1) continue;
            if (choice == 0) return;
            book.add(person);
            count++;
        }
    }

    private int takeInputInContact(Contact contact) {
        contact.setFirstName(takeValidInput(FIRST_NAME_INPUT));
        contact.setLastName(takeValidInput(LAST_NAME_INPUT));
        if (book.contains(contact)) {
            System.out.println("Contact already exists!!!");
            searchContact(contact.getFirstName(), contact.getLastName());
            System.out.println();
            Scanner sc = new Scanner(System.in);
            System.out.print("(1)Continue (0)Go back to last menu : ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case ENTER_AGAIN -> {
                    return ENTER_AGAIN;
                }
                case EXIT -> {
                    return EXIT;
                }
            }
            System.out.println();
        }
        contact.setAddress(takeValidInput(ADDRESS_INPUT));
        contact.setCity(takeValidInput(CITY_INPUT));
        contact.setState(takeValidInput(STATE_INPUT));
        contact.setPin(takeValidInput(PIN_INPUT));
        contact.setPhoneNumber(takeValidInput(PHONE_NUMBER_INPUT));
        contact.setEmail(takeValidInput(EMAIL_INPUT));
        System.out.println();
        return -1;
    }

    private void editContact() {
        if (book.size() == 0) {
            System.out.println("AddressBook is empty");
            return;
        }
        String firstName = takeValidInput(FIRST_NAME_INPUT);
        String lastName = takeValidInput(LAST_NAME_INPUT);
        Contact contact = book.stream()
                .filter(c -> c.getFirstName().equals(firstName) && c.getLastName().equals(lastName))
                .findAny()
                .orElse(null);
        if (contact == null) {
            System.out.println("Contact doesn't exist!!!");
            return;
        }
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
        System.out.println(contact);
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

    private void deleteContact() {
        if (book.size() == 0) {
            System.out.println("AddressBook is empty");
            return;
        }
        String firstName = takeValidInput(FIRST_NAME_INPUT);
        String lastName = takeValidInput(LAST_NAME_INPUT);
        boolean exist = book.stream()
                .anyMatch(contact -> contact.getFirstName().equals(firstName) && contact.getLastName().equals(lastName));
        if (!exist) {
            System.out.println("Contact doesn't exist!!!");
            return;
        }
        book = book.stream()
                .filter(contact -> !contact.getFirstName().equals(firstName) && !contact.getLastName().equals(lastName))
                .toList();
        System.out.println("Contact deleted");
    }

    public void searchContact(String firstName, String lastName) {
        Contact contact=book.stream()
                .filter(c -> c.getFirstName().equals(firstName) && c.getLastName().equals(lastName))
                .findAny()
                .orElse(null);
        if(contact!=null) System.out.println(contact);
        else System.out.println("Contact doesn't exist!!!");
    }

    private void takeInputAndSearchContact() {
        String firstName = takeValidInput(FIRST_NAME_INPUT);
        String lastName = takeValidInput(LAST_NAME_INPUT);
        searchContact(firstName, lastName);
    }

    private String takeValidInput(String type) {
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

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        book.forEach(contact -> str.append(contact).append("\n"));
        return str.toString();
    }
}
