package addressbook;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static addressbook.DictionaryEnum.*;
import static addressbook.InputEnum.*;
import static addressbook.Util.*;

public class AddressBookDictionary {

    private final Map<String, List<Contact>> mainDictionary;

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
                case SEARCH_CONTACT_BY_NAME -> searchByName(mainDictionary);
                case SEARCH_CONTACT_IN_CITY -> searchByPlace(CITY, CITY_DICTIONARY);
                case SEARCH_CONTACT_IN_STATE -> searchByPlace(STATE, STATE_DICTIONARY);
                case EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input!!!");
            }
        }
    }

    //check 1
    private void searchByName(Map<String, List<Contact>> dictionary) {
        String firstName = takeValidInput(FIRST_NAME);
        String lastName = takeValidInput(LAST_NAME);
        List<Contact> listOfContactsWithGivenName =
                getContactsStream(dictionary)
                        .filter(matchingName(firstName, lastName))
                        .toList();
        if (listOfContactsWithGivenName.size() == 0) {
            System.out.println("Contact doesn't exist!!!");
            return;
        }
        listOfContactsWithGivenName.forEach(System.out::println);
    }

    //check 1
    private void searchByPlace(InputEnum placeType, DictionaryEnum dictionaryType) {
        String placeName = takeValidInput(placeType);
        Map<String, List<Contact>> dictionary = dictionaryType.getPlaceDictionary(getContactsStream(mainDictionary));
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
                case PRINT_MAIN_DICTIONARY -> printDictionary(mainDictionary);
                case PRINT_CITY_DICTIONARY ->
                        printDictionary(CITY_DICTIONARY.getPlaceDictionary(getContactsStream(mainDictionary)));
                case PRINT_STATE_DICTIONARY ->
                        printDictionary(STATE_DICTIONARY.getPlaceDictionary(getContactsStream(mainDictionary)));
                case EXIT -> {
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
    public void countMenu() {
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
            switch (choice) {
                case COUNT_IN_CITY_DICTIONARY -> countInDictionary(CITY_DICTIONARY);
                case COUNT_IN_STATE_DICTIONARY -> countInDictionary(STATE_DICTIONARY);
                case EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input!!!");
            }
        }
    }

    //check 1
    private void countInDictionary(DictionaryEnum dictionaryType) {
        Stream<Contact> stream = getContactsStream(mainDictionary);
        if (dictionaryType == CITY_DICTIONARY)
            stream.collect(Collectors.groupingBy(Contact::getCity, Collectors.counting()))
                    .forEach((key, value) -> System.out.println(key + " -> " + value));
        else
            stream.collect(Collectors.groupingBy(Contact::getState, Collectors.counting()))
                    .forEach((key, value) -> System.out.println(key + " -> " + value));
    }

    //check 1
    private Stream<Contact> getContactsStream(Map<String, List<Contact>> dictionary) {
        return dictionary
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
            if (choice == EXIT) return;
            if (choice > 4) {
                System.out.println("Wrong input!!!");
                continue;
            }
            SortEnum.values()[choice - 1]
                    .getSortedContacts(getContactsStream(mainDictionary))
                    .forEach(System.out::println);
        }
    }
}