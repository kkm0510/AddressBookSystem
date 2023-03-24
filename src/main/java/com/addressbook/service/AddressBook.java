package com.addressbook.service;

import com.addressbook.enums.InputEnum;
import com.addressbook.exceptions.AddressBookException;
import com.addressbook.model.Contact;

import static com.addressbook.util.Util.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AddressBook {

    public void operateBook(String bookName, List<Contact> book) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("\n" + bookName + " -> \n(1)Add contacts (2)Edit contact " +
                        "(3)Delete contact (4)Print Address Book  (5)Search contact   (0)Go back to main menu -> ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case ADD_CONTACT -> addContacts(book);
                    case EDIT_CONTACT -> editContact(book);
                    case DELETE_CONTACT -> deleteContact(book);
                    case SEARCH -> searchContactInBook(book);
                    case PRINT -> whereToPrintContactsStream(book.stream());
                    case EXIT -> {
                        return;
                    }
                    default -> throw new AddressBookException("Invalid Input!!!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!!!");
                sc.nextLine();
            } catch (AddressBookException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void addContacts(List<Contact> book) {
        Scanner sc = new Scanner(System.in);
        int noOfContacts = 0;
        while (true) {
            try {
                System.out.print("\nHow many contacts do you want to add? ");
                noOfContacts = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!!!");
                sc.nextLine();
            }
        }
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
        contact.setFirstName(takeValidInput(InputEnum.FIRST_NAME));
        contact.setLastName(takeValidInput(InputEnum.LAST_NAME));
        if (book.contains(contact)) {
            System.out.println("Contact already exists!!!");
            System.out.println();
            Scanner sc = new Scanner(System.in);
            System.out.print("(1)Continue (0)Go back to last menu : ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case ENTER_AGAIN -> {
                        return ENTER_AGAIN;
                    }
                    case EXIT -> {
                        return EXIT;
                    }
                    default -> throw new AddressBookException("Invalid Input!!!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!!!");
                sc.nextLine();
            } catch (AddressBookException e) {
                System.out.println(e.getMessage());
            }
            System.out.println();
        }
        contact.setAddress(takeValidInput(InputEnum.ADDRESS));
        contact.setCity(takeValidInput(InputEnum.CITY));
        contact.setState(takeValidInput(InputEnum.STATE));
        contact.setPin(takeValidInput(InputEnum.PIN));
        contact.setPhoneNumber(takeValidInput(InputEnum.PHONE_NUMBER));
        contact.setEmail(takeValidInput(InputEnum.EMAIL));
        System.out.println();
        return -1;
    }

    private void editContact(List<Contact> book) throws AddressBookException {
        if (book.size() == 0)
            throw new AddressBookException("AddressBook is empty");
        Scanner sc=new Scanner(System.in);
        String firstName = takeValidInput(InputEnum.FIRST_NAME);
        String lastName = takeValidInput(InputEnum.LAST_NAME);
        Contact contact = book.stream()
                .filter(matchingName(firstName, lastName))
                .findAny()
                .orElse(null);
        if (contact == null)
            throw new AddressBookException("Contact doesn't exist!!!");
        String toEdit = takeValidInput(InputEnum.EDIT).replaceAll("\\s", "");
        for (int i = 0; i < toEdit.length(); i++) {
            int choice = toEdit.charAt(i) - 48;
            try {
                if (choice == 0) return;
                InputEnum input = InputEnum.values()[choice - 1];
                switch (input) {
                    case FIRST_NAME -> contact.setFirstName(takeValidInput(InputEnum.FIRST_NAME));
                    case LAST_NAME -> contact.setLastName(takeValidInput(InputEnum.LAST_NAME));
                    case ADDRESS -> contact.setAddress(takeValidInput(InputEnum.ADDRESS));
                    case CITY -> contact.setCity(takeValidInput(InputEnum.CITY));
                    case STATE -> contact.setState(takeValidInput(InputEnum.STATE));
                    case PIN -> contact.setPin(takeValidInput(InputEnum.PIN));
                    case PHONE_NUMBER -> contact.setPhoneNumber(takeValidInput(InputEnum.PHONE_NUMBER));
                    case EMAIL -> contact.setEmail(takeValidInput(InputEnum.EMAIL));
                    default -> throw new AddressBookException("Invalid Input!!!");
                }
            } catch (Exception e) {
                System.out.println("Invalid Input");
                sc.nextLine();
            }
        }
        System.out.println("Contact edited successfully");
        System.out.println(contact);
    }

    private void deleteContact(List<Contact> book) throws AddressBookException {
        if (book.isEmpty())
            throw new AddressBookException("Invalid Input!!!");
        String firstName = takeValidInput(InputEnum.FIRST_NAME);
        String lastName = takeValidInput(InputEnum.LAST_NAME);
        boolean removed = book.removeIf(matchingName(firstName, lastName));
        if (!removed)
            throw new AddressBookException("Contact doesn't exist!!!");
        System.out.println("Contact deleted");
    }

    private void searchContactInBook(List<Contact> book) throws AddressBookException {
        String firstName = takeValidInput(InputEnum.FIRST_NAME);
        String lastName = takeValidInput(InputEnum.LAST_NAME);
        Contact contact = book.stream()
                .filter(matchingName(firstName, lastName))
                .findAny()
                .orElse(null);
        if (contact != null) System.out.println(contact);
        else throw new AddressBookException("Contact doesn't exist!!!");
    }
}
