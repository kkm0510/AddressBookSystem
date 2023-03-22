import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static constants.AddressBookConstants.*;
import static constants.AddressBookConstants.takeValidInput;

public class AddressBookDictionary {

    Map<String, List<Contact>> mainDictionary;

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
                case SEARCH_CONTACT_IN_CITY -> searchByPlace(CITY_INPUT, DictionaryEnum.CITY_DICTIONARY);
                case SEARCH_CONTACT_IN_STATE -> searchByPlace(STATE_INPUT, DictionaryEnum.STATE_DICTIONARY);
                case EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input!!!");
            }
        }
    }

    //check 1
    private void searchByName(Map<String, List<Contact>> dictionary) {
        String firstName = takeValidInput(FIRST_NAME_INPUT);
        String lastName = takeValidInput(LAST_NAME_INPUT);
        List<Contact> listOfContactsWithGivenName =
                dictionary
                        .entrySet()
                        .stream()
                        .flatMap(entry -> entry.getValue().stream())
                        .filter(c -> c.getFirstName().equals(firstName) && c.getLastName().equals(lastName))
                        .toList();
        if (listOfContactsWithGivenName.size() == 0) {
            System.out.println("Contact doesn't exist!!!");
            return;
        }
        listOfContactsWithGivenName.forEach(System.out::println);
    }

    //check 1
    private void searchByPlace(String placeType, DictionaryEnum dictionaryType) {
        String placeName = takeValidInput(placeType);
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
                case PRINT_MAIN_DICTIONARY -> printDictionary(mainDictionary);
                case PRINT_CITY_DICTIONARY -> printDictionary(DictionaryEnum.CITY_DICTIONARY.getPlaceDictionary(getContactsStream()));
                case PRINT_STATE_DICTIONARY ->
                        printDictionary(DictionaryEnum.STATE_DICTIONARY.getPlaceDictionary(getContactsStream()));
                case EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input!!!");
            }
        }
    }

    //check1
    private void printDictionary(Map<String, List<Contact>> dictionary) {
        if (dictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
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
            System.out.print("\nCount menu -> \n(1)Count contacts in city dictionary (2)Count contacts in state dictionary (0)Go back to main menu : ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case COUNT_IN_CITY_DICTIONARY ->
                        countInDictionary(DictionaryEnum.CITY_DICTIONARY.getPlaceDictionary(getContactsStream()), DictionaryEnum.CITY_DICTIONARY);
                case COUNT_IN_STATE_DICTIONARY ->
                        countInDictionary(DictionaryEnum.STATE_DICTIONARY.getPlaceDictionary(getContactsStream()), DictionaryEnum.STATE_DICTIONARY);
                case EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input!!!");
            }
        }
    }

    //check 1
    public void countInDictionary(Map<String, List<Contact>> dictionary, DictionaryEnum dictionaryType) {
        if (dictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        Stream<Contact> stream = dictionary
                .values()
                .stream()
                .flatMap(List::stream);
        Map<String, Long> countMap;
        if (dictionaryType == DictionaryEnum.CITY_DICTIONARY)
            countMap = stream.collect(Collectors.groupingBy(Contact::getCity, Collectors.counting()));
        else
            countMap = stream.collect(Collectors.groupingBy(Contact::getState, Collectors.counting()));
        countMap.forEach((key, value) -> System.out.println(key + " -> \n" + value));
    }

    //check 1
    public Stream<Contact> getContactsStream() {
        return mainDictionary
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().stream());
    }
}