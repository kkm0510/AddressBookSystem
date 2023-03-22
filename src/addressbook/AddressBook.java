package addressbook;

import java.util.List;
import java.util.Scanner;

import static addressbook.InputEnum.*;
import static addressbook.Util.*;


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
        contact.setFirstName(takeValidInput(FIRST_NAME));
        contact.setLastName(takeValidInput(LAST_NAME));
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
        contact.setAddress(takeValidInput(ADDRESS));
        contact.setCity(takeValidInput(CITY));
        contact.setState(takeValidInput(STATE));
        contact.setPin(takeValidInput(PIN));
        contact.setPhoneNumber(takeValidInput(PHONE_NUMBER));
        contact.setEmail(takeValidInput(EMAIL));
        System.out.println();
        return -1;
    }

    private void editContact(List<Contact> book) {
        if (book.size() == 0) {
            System.out.println("AddressBook is empty");
            return;
        }
        String firstName = takeValidInput(FIRST_NAME);
        String lastName = takeValidInput(LAST_NAME);
        Contact contact = book.stream()
                .filter(matchingName(firstName, lastName))
                .findAny()
                .orElse(null);
        if (contact == null) {
            System.out.println("Contact doesn't exist!!!");
            return;
        }
        String toEdit = takeValidInput(EDIT).replaceAll("\\s", "");
        for (int i = 0; i < toEdit.length(); i++) {
            int choice = toEdit.charAt(i) - 48;
            if(choice==0) return;
            InputEnum input=InputEnum.values()[choice-1];
            switch (input) {
                case FIRST_NAME -> contact.setFirstName(takeValidInput(FIRST_NAME));
                case LAST_NAME -> contact.setLastName(takeValidInput(LAST_NAME));
                case ADDRESS -> contact.setAddress(takeValidInput(ADDRESS));
                case CITY -> contact.setCity(takeValidInput(CITY));
                case STATE -> contact.setState(takeValidInput(STATE));
                case PIN -> contact.setPin(takeValidInput(PIN));
                case PHONE_NUMBER -> contact.setPhoneNumber(takeValidInput(PHONE_NUMBER));
                case EMAIL -> contact.setEmail(takeValidInput(EMAIL));
                default -> System.out.println("Wrong input");
            }
        }
        System.out.println("Contact edited successfully");
        System.out.println(contact);
    }

    private void deleteContact(List<Contact> book) {
        if (book.isEmpty()) {
            System.out.println("AddressBook is empty");
            return;
        }
        String firstName = takeValidInput(FIRST_NAME);
        String lastName = takeValidInput(LAST_NAME);
        boolean removed = book.removeIf(matchingName(firstName, lastName));
        if (!removed) {
            System.out.println("Contact doesn't exist!!!");
            return;
        }
        System.out.println("Contact deleted");
    }

    private void searchContactInBook(List<Contact> book) {
        String firstName = takeValidInput(FIRST_NAME);
        String lastName = takeValidInput(LAST_NAME);
        Contact contact = book.stream()
                .filter(matchingName(firstName, lastName))
                .findAny()
                .orElse(null);
        if (contact != null) System.out.println(contact);
        else System.out.println("Contact doesn't exist!!!");
    }
}
