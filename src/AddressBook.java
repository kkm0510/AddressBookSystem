import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static constants.AddressBookConstants.*;
import static constants.AddressBookConstants.LAST_NAME_INPUT;

public class AddressBook {

    public void operateBook(String bookName, List<Contact> book) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("\n" + bookName + " -> \n(1)Add contacts (2)Edit contact " +
                    "(3)Print Address Book  (4)Search contact (5)Delete contact  (0)Go back to main menu -> ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case ADD_CONTACT -> addContacts(book);
                case EDIT_CONTACT -> editContact(book);
                case DELETE_CONTACT -> deleteContact(book);
                case SEARCH -> searchContactInBook(book);
                case PRINT -> {
                    if (book.size() == 0) System.out.println("AddressBook is empty");
                    else System.out.println(book);
                }
                case EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input!!!");
            }
        }
    }

    private void addContacts(List<Contact> book) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nHow many contacts do you want to add? ");
        int noOfContacts = sc.nextInt();
        int count = 0;
        while (count < noOfContacts) {
            Contact contact = new Contact();
            int choice = takeInputInContact(contact, book);
            if (choice == 1) continue;
            if (choice == 0) return;
            book.add(contact);
            count++;
        }
    }

    private int takeInputInContact(Contact contact, List<Contact> book) {
        contact.setFirstName(takeValidInput(FIRST_NAME_INPUT));
        contact.setLastName(takeValidInput(LAST_NAME_INPUT));
        if (book.contains(contact)) {
            System.out.println("Contact already exists!!!");
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
                default -> System.out.println("Wrong input!!!");
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

    private void editContact(List<Contact> book) {
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
        String toEdit = validInputToEdit();
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

    private String validInputToEdit() {
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

    private void deleteContact(List<Contact> book) {
        if (book.isEmpty()) {
            System.out.println("AddressBook is empty");
            return;
        }
        String firstName = takeValidInput(FIRST_NAME_INPUT);
        String lastName = takeValidInput(LAST_NAME_INPUT);
        boolean removed = book.removeIf(contact -> contact.getFirstName().equals(firstName) && contact.getLastName().equals(lastName));
        if (!removed) {
            System.out.println("Contact doesn't exist!!!");
            return;
        }
        System.out.println("Contact deleted");
    }

    private void searchContactInBook(List<Contact> book) {
        String firstName = takeValidInput(FIRST_NAME_INPUT);
        String lastName = takeValidInput(LAST_NAME_INPUT);
        Contact contact = book.stream()
                .filter(c -> c.getFirstName().equals(firstName) && c.getLastName().equals(lastName))
                .findAny()
                .orElse(null);
        if (contact != null) System.out.println(contact);
        else System.out.println("Contact doesn't exist!!!");
    }
}
