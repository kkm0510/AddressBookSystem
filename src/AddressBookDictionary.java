import java.util.*;
import java.util.stream.Collector;
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

    public void searchMenu() {
        if(mainDictionary.size()==0){
            System.out.println("No address book present");
            return;
        }
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("\nSearch menu -> \n(1)Search contact by name " +
                    "(2)Search contacts in a city (3)Search contacts in a state (0) Go back to main menu : ");
            int choice = sc.nextInt();
            switch (choice) {
                case SEARCH_CONTACT_BY_NAME -> searchByName();
                case SEARCH_CONTACT_IN_CITY -> searchByCity();
                case SEARCH_CONTACT_IN_STATE -> searchByState();
                case EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input!!!");
            }
        }
    }

    private void searchByName() {
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
            System.out.println("Contact doesn't exist!!!");
            return;
        }
        listOfContactsWithGivenName.forEach(System.out::println);
    }

    private void searchByCity() {
        String cityName = takeValidInput(CITY_INPUT);
        fillPlaceDictionary(CITY_INPUT);
        if (!cityDictionary.containsKey(cityName))
            System.out.println("No contacts in this city!!!");
        String firstName = takeValidInput(FIRST_NAME_INPUT);
        String lastName = takeValidInput(LAST_NAME_INPUT);
        cityDictionary
                .get(cityName)
                .stream()
                .filter(contact -> contact.getFirstName().equals(firstName) && contact.getLastName().equals(lastName))
                .forEach(System.out::println);
    }

    private void searchByState() {
        String stateName = takeValidInput(STATE_INPUT);
        fillPlaceDictionary(STATE_INPUT);
        if (!stateDictionary.containsKey(stateName))
            System.out.println("No contacts in this state!!!");
        String firstName = takeValidInput(FIRST_NAME_INPUT);
        String lastName = takeValidInput(LAST_NAME_INPUT);
        stateDictionary
                .get(stateName)
                .stream()
                .filter(contact -> contact.getFirstName().equals(firstName) && contact.getLastName().equals(lastName))
                .forEach(System.out::println);
    }

    private void fillPlaceDictionary(String placeType) {
        Stream<Contact> stream = mainDictionary
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().book.stream());
        if (placeType.equals(CITY_INPUT))
            cityDictionary = stream.collect(Collectors.groupingBy(Contact::getCity));
        else
            stateDictionary = stream.collect(Collectors.groupingBy(Contact::getState));
    }

    public void printMenu() {
        if(mainDictionary.size()==0){
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
                case PRINT_MAIN_DICTIONARY -> printMainDictionary();
                case PRINT_CITY_DICTIONARY -> printCityDictionary();
                case PRINT_STATE_DICTIONARY -> printStateDictionary();
                case EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input!!!");
            }
        }
    }

    public void printMainDictionary() {
        if (mainDictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        mainDictionary.entrySet().stream()
                .filter(entry -> entry.getValue().book.size() != 0)
                .forEach(entry -> System.out.println("~~~~~~~~~~~~~\n" + entry.getKey() + " -> \n" + entry.getValue()));
    }

    public void printCityDictionary() {
        fillPlaceDictionary(CITY_INPUT);
        if (cityDictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        cityDictionary.entrySet().stream()
                .filter(entry -> entry.getValue().size() != 0)
                .forEach(entry -> System.out.println("~~~~~~~~~~~~~\n" + entry.getKey() + " -> \n" + entry.getValue()));
    }

    public void printStateDictionary() {
        fillPlaceDictionary(STATE_INPUT);
        if (stateDictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        stateDictionary.entrySet().stream()
                .filter(entry -> entry.getValue().size() != 0)
                .forEach(entry -> System.out.println("~~~~~~~~~~~~~\n" + entry.getKey() + " -> \n" + entry.getValue()));
    }

    public void countMenu() {
        if(mainDictionary.size()==0){
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
                case COUNT_IN_CITY_DICTIONARY -> countInCityDictionary();
                case COUNT_IN_STATE_DICTIONARY -> countInStateDictionary();
                case EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input!!!");
            }
        }
    }

    public void countInCityDictionary() {
        fillPlaceDictionary(CITY_INPUT);
        if (cityDictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        cityDictionary
                .values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Contact::getCity, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry->entry.getValue()!=0)
                .forEach(System.out::println);
    }

    public void countInStateDictionary() {
        fillPlaceDictionary(STATE_INPUT);
        if (stateDictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        stateDictionary
                .values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Contact::getState, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry->entry.getValue()!=0)
                .forEach(System.out::println);
    }
}