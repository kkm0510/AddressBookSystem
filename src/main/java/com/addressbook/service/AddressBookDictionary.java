package com.addressbook.service;

import com.addressbook.enums.DictionaryEnum;
import com.addressbook.enums.InputEnum;
import com.addressbook.enums.SortEnum;
import com.addressbook.fileio.csvoperations.ReadData;
import com.addressbook.fileio.csvoperations.WriteData;
import com.addressbook.model.Contact;
import com.addressbook.util.Util;

import java.util.*;
import java.util.stream.Stream;

public class AddressBookDictionary {

    private Map<String, List<Contact>> mainDictionary;

    public AddressBookDictionary() {
        mainDictionary = new HashMap<>();
    }

    //check 1
    public void createAddressBook() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter name of address book : ");
        String name = sc.nextLine();
        if (mainDictionary.containsKey(name)) {
            System.out.println("Book already exists \n");
            return;
        }
        List<Contact> addressBook = new LinkedList<>();
        mainDictionary.put(name, addressBook);
        new AddressBook().operateBook(name, addressBook);
    }

    //check 1
    public void chooseAddressBook() {
        Scanner sc = new Scanner(System.in);
        if (mainDictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        System.out.println("\nAddress Books :- ");
        mainDictionary.keySet().forEach(System.out::println);
        System.out.print("Choose address book : ");
        String name = sc.nextLine();
        if (mainDictionary.containsKey(name))
            new AddressBook().operateBook(name, mainDictionary.get(name));
        else
            System.out.println("Book doesn't exist \n");
    }

    //check 1
    public void searchMenu() {
        if (mainDictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("\nSearch menu -> \n(1)Search contact by name " +
                    "(2)Search contacts in a city (3)Search contacts in a state (0) Go back to main menu : ");
            int choice = sc.nextInt();
            switch (choice) {
                case Util.SEARCH_CONTACT_BY_NAME -> searchByName(mainDictionary);
                case Util.SEARCH_CONTACT_IN_CITY -> searchByPlace(InputEnum.CITY, DictionaryEnum.CITY_DICTIONARY);
                case Util.SEARCH_CONTACT_IN_STATE -> searchByPlace(InputEnum.STATE, DictionaryEnum.STATE_DICTIONARY);
                case Util.EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input!!!");
            }
        }
    }

    //check 1
    private void searchByName(Map<String, List<Contact>> dictionary) {
        String firstName = Util.takeValidInput(InputEnum.FIRST_NAME);
        String lastName = Util.takeValidInput(InputEnum.LAST_NAME);
        List<Contact> listOfContactsWithGivenName = getContactsStream(dictionary)
                .filter(Util.matchingName(firstName, lastName))
                .toList();
        if (listOfContactsWithGivenName.size() == 0) {
            System.out.println("Contact doesn't exist!!!");
            return;
        }
        listOfContactsWithGivenName.forEach(System.out::println);
    }

    //check 1
    private void searchByPlace(InputEnum placeType, DictionaryEnum dictionaryType) {
        String placeName = Util.takeValidInput(placeType);
        Map<String, List<Contact>> dictionary = dictionaryType.getPlaceDictionary(getContactsStream());
        if (!dictionary.containsKey(placeName) || dictionary.get(placeName).size() == 0)
            System.out.println("No contacts in this city!!!");
        searchByName(dictionary);
    }

    //check 1
    public void printMenu() {
        if (mainDictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("\nPrint menu -> \n(1)Print main dictionary " +
                    "(2)Print city dictionary (3)Print state dictionary (0)Go back to main menu : ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case Util.PRINT_MAIN_DICTIONARY -> printDictionary(mainDictionary);
                case Util.PRINT_CITY_DICTIONARY ->
                        printDictionary(DictionaryEnum.CITY_DICTIONARY.getPlaceDictionary(getContactsStream()));
                case Util.PRINT_STATE_DICTIONARY ->
                        printDictionary(DictionaryEnum.STATE_DICTIONARY.getPlaceDictionary(getContactsStream()));
                case Util.EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input!!!");
            }
        }
    }

    //check1
    private void printDictionary(Map<String, List<Contact>> dictionary) {
        dictionary.entrySet().stream()
                .filter(entry -> entry.getValue().size() != 0)
                .forEach(entry -> System.out.println("~~~~~~~~~~~~~\n" + entry.getKey() + " -> \n" + entry.getValue()));
    }

    //check 1
    public void count() {
        if (mainDictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("\nCount menu -> \n(1)Count contacts in city dictionary " +
                    "(2)Count contacts in state dictionary (0)Go back to main menu : ");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 0) return;
            if (choice > 2) {
                System.out.println("Wrong input!!!");
                continue;
            }
            DictionaryEnum.values()[choice - 1]
                    .getCountDictionary(getContactsStream())
                    .forEach((key, value) -> System.out.println(key + " -> " + value));
        }
    }

    //check 1
    private Stream<Contact> getContactsStream(Map<String, List<Contact>> dictionary) {
        return dictionary
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().stream());
    }

    private Stream<Contact> getContactsStream() {
        return mainDictionary
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().stream());
    }

    //check 1
    public void sort() {
        if (mainDictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("\nSort menu -> \n(1)Sort entries by name (2)Sort entries by city " +
                    "(3)Sort entries by state (4)Sort entries by pin (0) Go back to main menu : ");
            int choice = sc.nextInt();
            if (choice == Util.EXIT) return;
            if (choice > 4) {
                System.out.println("Wrong input!!!");
                continue;
            }
            SortEnum.values()[choice - 1]
                    .getSortedContacts(getContactsStream())
                    .forEach(System.out::println);
        }
    }

    public void readCSVData() {
        ReadData read=new ReadData();
        mainDictionary=read.loadCSVDictionary();
        System.out.println("Data loaded successfully");
    }

    public void writeDataToCSV() {
        WriteData write=new WriteData();
        write.writeDataFromDictionary(mainDictionary);
        System.out.println("Data written successfully");
    }
}