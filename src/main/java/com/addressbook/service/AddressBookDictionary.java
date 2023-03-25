package com.addressbook.service;

import com.addressbook.enums.DictionaryEnum;
import static com.addressbook.enums.DictionaryEnum.*;
import com.addressbook.enums.InputEnum;
import static com.addressbook.enums.InputEnum.*;
import com.addressbook.enums.SortEnum;
import com.addressbook.enums.WhereToPrintEnum;
import com.addressbook.exceptions.AddressBookException;
import com.addressbook.fileio.CSVOperations;
import com.addressbook.fileio.JSONOperations;
import com.addressbook.model.Contact;

import static com.addressbook.util.Util.*;

import java.util.*;
import java.util.stream.Stream;

public class AddressBookDictionary {

    private Map<String, List<Contact>> mainDictionary;

    public AddressBookDictionary() {
        mainDictionary = new HashMap<>();
    }

    public void createAddressBook() throws AddressBookException {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter name of address book : ");
        String name = sc.nextLine();
        if (mainDictionary.containsKey(name))
            throw new AddressBookException("Book already exists \n");
        List<Contact> addressBook = new LinkedList<>();
        mainDictionary.put(name, addressBook);
        new AddressBook().operateBook(name, addressBook);
    }

    public void chooseAddressBook() throws AddressBookException {
        Scanner sc = new Scanner(System.in);
        if (mainDictionary.size() == 0)
            throw new AddressBookException("No address book present!!!");
        System.out.println("\nAddress Books :- ");
        mainDictionary.keySet().forEach(System.out::println);
        System.out.print("Choose address book : ");
        String name = sc.nextLine();
        if (mainDictionary.containsKey(name))
            new AddressBook().operateBook(name, mainDictionary.get(name));
        else
            throw new AddressBookException("Book doesn't exist!!!");
    }

    public void searchMenu() throws AddressBookException {
        if (mainDictionary.size() == 0)
            throw new AddressBookException("No address book present!!!");
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("\nSearch menu -> \n(1)Search contact by name " +
                        "(2)Search contacts in a city (3)Search contacts in a state (0) Go back to main menu : ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case SEARCH_CONTACT_BY_NAME -> searchByName(mainDictionary);
                    case SEARCH_CONTACT_IN_CITY -> searchByPlace(CITY, CITY_DICTIONARY);
                    case SEARCH_CONTACT_IN_STATE -> searchByPlace(STATE, STATE_DICTIONARY);
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

    private void searchByName(Map<String, List<Contact>> dictionary) throws AddressBookException {
        String firstName = takeValidInput(FIRST_NAME);
        String lastName = takeValidInput(LAST_NAME);
        List<Contact> listOfContactsWithGivenName =
                dictionary
                        .entrySet()
                        .stream()
                        .flatMap(entry -> entry.getValue().stream())
                        .filter(matchingName(firstName, lastName))
                        .toList();
        if (listOfContactsWithGivenName.size() == 0)
            throw new AddressBookException("Contact doesn't exist!!!");
        whereToPrintContactsStream(listOfContactsWithGivenName.stream());
    }

    private void searchByPlace(InputEnum placeType, DictionaryEnum dictionaryType) throws AddressBookException {
        String placeName = takeValidInput(placeType);
        Map<String, List<Contact>> dictionary = dictionaryType.getPlaceDictionary(getContactsStream());
        if (!dictionary.containsKey(placeName) || dictionary.get(placeName).size() == 0)
            throw new AddressBookException("No contacts in this city!!!");
        searchByName(dictionary);
    }

    public void printMenu() throws AddressBookException {
        if (mainDictionary.size() == 0)
            throw new AddressBookException("No address book present!!!");
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("\nPrint menu -> \n(1)Print main dictionary " +
                        "(2)Print city dictionary (3)Print state dictionary (0)Go back to main menu : ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case PRINT_MAIN_DICTIONARY -> whereToPrintDictionary(mainDictionary);
                    case PRINT_CITY_DICTIONARY ->
                            whereToPrintDictionary(CITY_DICTIONARY.getPlaceDictionary(getContactsStream()));
                    case PRINT_STATE_DICTIONARY ->
                            whereToPrintDictionary(STATE_DICTIONARY.getPlaceDictionary(getContactsStream()));
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

    private void whereToPrintDictionary(Map<String, List<Contact>> dictionary) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("\nWhere to print this dictionary -> (1)Console (2)CSV file (3)JSON file (0)Go back to main menu : ");
                int choice = sc.nextInt();
                sc.nextLine();
                if (choice == 0) return;
                WhereToPrintEnum.values()[choice - 1].printDictionary(dictionary);
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!!!");
                sc.nextLine();
            }
        }
    }

    public void count() throws AddressBookException {
        if (mainDictionary.size() == 0)
            throw new AddressBookException("No address book present!!!");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("\nCount menu -> \n(1)Count contacts in city dictionary " +
                    "(2)Count contacts in state dictionary (0)Go back to main menu : ");
            int choice = 0;
            try {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice == 0) return;
                if (choice > 2)
                    throw new AddressBookException("Invalid Input!!!");
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!!!");
                sc.nextLine();
            } catch (AddressBookException e) {
                System.out.println(e.getMessage());
            }
            whereToPrintCountDictionary(DictionaryEnum.values()[choice - 1].getCountDictionary(getContactsStream()));
        }
    }

    private void whereToPrintCountDictionary(Map<String, Long> dictionary) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("\nWhere to print this dictionary -> (1)Console (2)CSV file (3)JSON file (0)Go back to main menu: ");
                int choice = sc.nextInt();
                sc.nextLine();
                if (choice == 0) return;
                WhereToPrintEnum.values()[choice - 1].printCountDictionary(dictionary);
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!!!");
                sc.nextLine();
            }
        }
    }

    private Stream<Contact> getContactsStream() {
        return mainDictionary
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().stream());
    }

    public void sort() throws AddressBookException {
        if (mainDictionary.size() == 0)
            throw new AddressBookException("No address book present!!!");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("\nSort menu -> \n(1)Sort entries by name (2)Sort entries by city " +
                    "(3)Sort entries by state (4)Sort entries by pin (0) Go back to main menu : ");
            int choice = 0;
            try {
                choice = sc.nextInt();
                if (choice == EXIT) return;
                if (choice > 4)
                    throw new AddressBookException("Invalid Input!!!");
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!!!");
                sc.nextLine();
            } catch (AddressBookException e) {
                System.out.println(e.getMessage());
            }
            Stream<Contact> sortedStream = SortEnum.values()[choice - 1].getSortedContacts(getContactsStream());
            whereToPrintContactsStream(sortedStream);
        }
    }



    public void fileIO() throws AddressBookException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("\nEnter choice -> \n(1)Read CSV file (2)Write to CSV file " +
                        "(3)Read JSON file (4) Write to JSON file (0)Go back to main menu : ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case READ_CSV -> readCSVData();
                    case WRITE_CSV -> writeDataToCSV(mainDictionary);
                    case READ_JSON -> readJSONData();
                    case WRITE_JSON -> writeDataToJSON(mainDictionary);
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

    public void readCSVData() throws AddressBookException {
        CSVOperations read = new CSVOperations();
        mainDictionary = read.readDictionary();
        System.out.println("Data loaded successfully");
    }

    public void writeDataToCSV(Map<String, List<Contact>> dictionary) throws AddressBookException {
        if (mainDictionary.size() == 0)
            throw new AddressBookException("No address book present!!!");
        CSVOperations write = new CSVOperations();
        write.writeDictionary(dictionary);
        System.out.println("Data written successfully");
    }

    public void readJSONData() {
        JSONOperations read = new JSONOperations();
        mainDictionary = read.readDictionary();
        System.out.println("Data loaded successfully");
    }

    public void writeDataToJSON(Map<String, List<Contact>> dictionary) throws AddressBookException {
        if (mainDictionary.size() == 0)
            throw new AddressBookException("No address book present!!!");
        JSONOperations write = new JSONOperations();
        write.writeDictionary(dictionary);
        System.out.println("Data written successfully");
    }
}