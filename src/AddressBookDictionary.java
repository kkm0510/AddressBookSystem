import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static constants.AddressBookConstants.*;
import static constants.AddressBookConstants.takeValidInput;

public class AddressBookDictionary {

    Map<String, AddressBook> mainDictionary;
    Map<String, List<Contact>> cityDictionary;
    Map<String, List<Contact>> stateDictionary;

    public AddressBookDictionary() {
        mainDictionary = new HashMap<>();
        cityDictionary = new HashMap<>();
        stateDictionary = new HashMap<>();
    }

    public void createAddressBook() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter name of address book : ");
        String name = sc.nextLine();
        if (mainDictionary.containsKey(name)) {
            System.out.println("Book already exists \n");
            return;
        }
        AddressBook addressBook = new AddressBook(name);
        mainDictionary.put(name, addressBook);
        addressBook.operateBook();
    }

    public void chooseAddressBook() {
        Scanner sc = new Scanner(System.in);
        if (mainDictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        System.out.println("\nAddress Books :- ");
        for (String name : mainDictionary.keySet()) {
            System.out.println(name);
        }
        System.out.print("Choose address book : ");
        String name = sc.nextLine();
        if (mainDictionary.containsKey(name))
            mainDictionary.get(name).operateBook();
        else
            System.out.println("Book doesn't exist \n");
    }

    public void printDictionary() {
        if (mainDictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        mainDictionary.entrySet().stream()
                .filter(entry -> entry.getValue().book.size() != 0)
                .forEach(entry -> System.out.println("~~~~~~~~~~~~~\n" + entry.getKey() + " -> \n" + entry.getValue()));
    }

    public void searchMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("\nSearch menu -> \n(1)Search contact by name " +
                    "(2)Search contacts in a city (3)Search contacts in a state (0) Go back to main menu : ");
            int choice = sc.nextInt();
            switch (choice) {
                case SEARCH_CONTACT_BY_NAME -> searchContacts(NAME_INPUT);
                case SEARCH_CONTACT_IN_CITY -> searchContacts(CITY_INPUT);
                case SEARCH_CONTACT_IN_STATE -> searchContacts(STATE_INPUT);
                case EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input!!!");
            }
        }
    }

    private void searchContacts(String type) {
        String firstName = takeValidInput(FIRST_NAME_INPUT);
        String lastName = takeValidInput(LAST_NAME_INPUT);
        List<Contact> listOfContactsWithGivenName =
                mainDictionary
                        .entrySet()
                        .stream()
                        .flatMap(entry -> entry.getValue().book.stream())
                        .filter(c -> c.getFirstName().equals(firstName) && c.getLastName().equals(lastName))
                        .toList();
        if (listOfContactsWithGivenName.size() == 0) {
            System.out.println("Contact doesn't exist");
            return;
        }
        if (type.equals(NAME_INPUT)) {
            listOfContactsWithGivenName
                    .forEach(System.out::println);
        } else if (type.equals(CITY_INPUT)) {
            String placeName = takeValidInput(CITY_INPUT);
            listOfContactsWithGivenName
                    .stream()
                    .filter(contact -> contact.getCity().equals(placeName))
                    .forEach(System.out::println);
        } else {
            String placeName = takeValidInput(STATE_INPUT);
            listOfContactsWithGivenName
                    .stream()
                    .filter(contact -> contact.getState().equals(placeName))
                    .forEach(System.out::println);
        }
    }

    private void fillPlaceDictionary(String placeType) {
        Stream<Contact> stream = mainDictionary
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().book.stream());
        if (placeType.equals(CITY_INPUT))
            cityDictionary = stream.collect(Collectors.groupingBy(Contact::getCity));
        else
            stateDictionary=stream.collect(Collectors.groupingBy(Contact::getState));
    }

    public void printPlaceDictionary(String placeType) {
        if (placeType.equals(CITY_INPUT)) {
            fillPlaceDictionary(CITY_INPUT);
            System.out.println(cityDictionary);
        } else {
            fillPlaceDictionary(STATE_INPUT);
            System.out.println(stateDictionary);
        }
    }
}