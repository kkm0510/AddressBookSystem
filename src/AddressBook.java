import java.util.*;

public class AddressBook {

    Map<String, ArrayList<Contact>> dictionary;

    public AddressBook() {
        dictionary = new HashMap<>();
    }

    private boolean searchInBook(String bookName, ArrayList<Contact> book, String input, int searchType) {
        boolean contactFound = false;
        for (Contact contact : book) {
            if (searchType == AddressBookConstants.SEARCH_BY_NAME
                    && (contact.getFirstName() + " " + contact.getLastName()).equals(input)
                    || searchType == AddressBookConstants.SEARCH_BY_PHONE_NUMBER
                    && contact.getPhoneNumber().equals(input)) {
                System.out.println("Contact found in : " + bookName + " address book");
                System.out.println(contact + "\n");
                contactFound = true;
            }
        }
        return contactFound;
    }

    private void searchInDictionary(String input, int searchType) {
        boolean contactFound = false;
        int trueCount = 0;
        for (Map.Entry<String, ArrayList<Contact>> book : dictionary.entrySet()) {
            contactFound = searchInBook(book.getKey(), book.getValue(), input, searchType);
            if (contactFound) trueCount++;
        }
        if (!contactFound && trueCount == 0) System.out.println("Contact not found \n");
    }

    public void search(int callType, String bookName) {
        if (dictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        if (callType == AddressBookConstants.SEARCH_IN_BOOK && dictionary.get(bookName).size() == 0) {
            System.out.println("Address book is empty");
            return;
        }
        String firstName, lastName, phoneNumber;
        Scanner sc = new Scanner(System.in);
        System.out.print("\nChoose : \n(1) Search by Name (2)Search by Phone Number (0)Go back to previous menu : ");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case AddressBookConstants.SEARCH_BY_NAME -> {
                firstName = inputCorrectName(AddressBookConstants.FIRST);
                lastName = inputCorrectName(AddressBookConstants.LAST);
                if (callType == AddressBookConstants.SEARCH_IN_BOOK) {
                    boolean contactFound = searchInBook(bookName, dictionary.get(bookName),
                            firstName + " " + lastName, AddressBookConstants.SEARCH_BY_NAME);
                    if (!contactFound) System.out.println("Contact not found \n");
                } else {
                    searchInDictionary(firstName + " " + lastName, AddressBookConstants.SEARCH_BY_NAME);
                }
            }
            case AddressBookConstants.SEARCH_BY_PHONE_NUMBER -> {
                phoneNumber = inputCorrectNumber(AddressBookConstants.PHONE_NUM);
                if (callType == AddressBookConstants.SEARCH_IN_BOOK) {
                    boolean contactFound = searchInBook(bookName,
                            dictionary.get(bookName), phoneNumber, AddressBookConstants.SEARCH_BY_PHONE_NUMBER);
                    if (!contactFound) System.out.println("Contact not found \n");
                } else {
                    searchInDictionary(phoneNumber, AddressBookConstants.SEARCH_BY_PHONE_NUMBER);
                }
            }
            case AddressBookConstants.EXIT -> {

            }
            default -> System.out.println("Wrong Input");
        }
    }

    public void takeInputInContact(Contact contact) {
        Scanner sc = new Scanner(System.in);
        contact.setFirstName(inputCorrectName(AddressBookConstants.FIRST));
        contact.setLastName(inputCorrectName(AddressBookConstants.LAST));
        System.out.print("Enter address : ");
        contact.setAddress(sc.nextLine());
        System.out.print("Enter city : ");
        contact.setCity(sc.next());
        sc.nextLine();
        System.out.print("Enter state : ");
        contact.setState(sc.nextLine());
        contact.setPin(inputCorrectNumber(AddressBookConstants.PIN_NUM));
        contact.setPhoneNumber(inputCorrectNumber(AddressBookConstants.PHONE_NUM));
        System.out.print("Enter email : ");
        contact.setEmail(sc.nextLine());
        System.out.println();
    }

    public void editContact(ArrayList<Contact> book) {
        if (book.size() == 0) {
            System.out.println("AddressBook is empty");
            return;
        }
        Scanner sc = new Scanner(System.in);
        String firstName = inputCorrectName(AddressBookConstants.FIRST);
        String lastName = inputCorrectName(AddressBookConstants.LAST);

        for (Contact contact : book) {
            if (contact.getFirstName().equals(firstName) && contact.getLastName().equals(lastName)) {
                String toEdit = inputToEdit();
                for (int i = 0; i < toEdit.length(); i++) {
                    if (toEdit.charAt(i) == ' ') continue;
                    int choice = toEdit.charAt(i) - 48;
                    switch (choice) {
                        case AddressBookConstants.FIRST_NAME ->
                                contact.setFirstName(inputCorrectName(AddressBookConstants.FIRST));
                        case AddressBookConstants.LAST_NAME ->
                                contact.setLastName(inputCorrectName(AddressBookConstants.LAST));
                        case AddressBookConstants.ADDRESS -> {
                            System.out.print("Enter new address : ");
                            contact.setAddress(sc.nextLine());
                        }
                        case AddressBookConstants.CITY -> {
                            System.out.print("Enter new city : ");
                            contact.setCity(sc.next());
                            sc.nextLine();
                        }
                        case AddressBookConstants.STATE -> {
                            System.out.print("Enter new state : ");
                            contact.setState(sc.nextLine());
                        }
                        case AddressBookConstants.PIN ->
                                contact.setPin(inputCorrectNumber(AddressBookConstants.PIN_NUM));
                        case AddressBookConstants.PHONE_NUMBER ->
                                contact.setPhoneNumber(inputCorrectNumber(AddressBookConstants.PHONE_NUM));
                        case AddressBookConstants.EMAIL -> {
                            System.out.print("Enter new email : ");
                            contact.setEmail(sc.nextLine());
                        }
                        default -> System.out.println("Wrong input");
                    }
                }
                System.out.println("Contact edited successfully");
                return;
            }
        }
        System.out.println("Contact doesn't exist in address book");
    }

    public void deleteContact(ArrayList<Contact> book) {
        if (book.size() == 0) {
            System.out.println("AddressBook is empty");
            return;
        }
        String firstName = inputCorrectName(AddressBookConstants.FIRST);
        String lastName = inputCorrectName(AddressBookConstants.LAST);
        for (Contact c : book) {
            if (c.getFirstName().equals(firstName) && c.getLastName().equals(lastName)) {
                book.remove(c);
                System.out.println("Contact deleted");
                return;
            }
        }
        System.out.println("Contact doesn't exist");
    }

    private void addContacts(ArrayList<Contact> book) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nHow many contacts do you want to add? ");
        int noOfContacts = sc.nextInt();
        int count = 0;
        while (count < noOfContacts) {
            Contact person = new Contact();
            takeInputInContact(person);
            book.add(person);
            count++;
        }
    }

    public void operateBook(String bookName, ArrayList<Contact> book) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("\n" + bookName + " -> \n(1)Add contacts (2)Edit contact " +
                    "(3)Delete contact (4)Search contact (5)Print Address Book  (0)Go back to main menu -> ");
            int choice = sc.nextInt();
            switch (choice) {
                case AddressBookConstants.ADD_CONTACT -> addContacts(book);
                case AddressBookConstants.EDIT_CONTACT -> editContact(book);
                case AddressBookConstants.DELETE_CONTACT -> deleteContact(book);
                case AddressBookConstants.SEARCH_CONTACT -> search(AddressBookConstants.SEARCH_IN_BOOK, bookName);
                case AddressBookConstants.PRINT_BOOK -> {
                    if (book.size() == 0) System.out.println("AddressBook is empty");
                    else printAddressBook(book);
                }
                case AddressBookConstants.EXIT -> {
                    return;
                }
                default -> System.out.println("Wrong input");
            }
        }
    }

    public void createAddressBook() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter name of address book : ");
        String name = sc.nextLine();
        if (dictionary.containsKey(name)) {
            System.out.println("Book already exists \n");
            return;
        }
        ArrayList<Contact> book = new ArrayList<>();
        dictionary.put(name, book);
//        operateBook(name, book);
    }

    public void chooseAddressBook() {
        Scanner sc = new Scanner(System.in);
        if (dictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        System.out.println("\nAddress Books :- ");
        for (String name : dictionary.keySet()) {
            System.out.println(name);
        }
        System.out.print("Choose address book : ");
        String name = sc.nextLine();
        if (dictionary.containsKey(name))
            operateBook(name, dictionary.get(name));
        else
            System.out.println("Book doesn't exist \n");
    }

    private boolean checkNameIsLegit(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (!(name.charAt(i) >= 65 && name.charAt(i) <= 90 || name.charAt(i) >= 97 && name.charAt(i) <= 122)) {
                return false;
            }
        }
        return true;
    }

    private String inputCorrectName(String type) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter " + type + " : ");
            String name = sc.next();
            sc.nextLine();
            if (!checkNameIsLegit(name))
                System.out.println("Illegal Input!!!");
            else {
                return name;
            }
        }
    }

    private boolean checkToEditIsLegit(Set<Character> set, String toEdit) {
        for (int i = 0; i < toEdit.length(); i++) {
            if (!(toEdit.charAt(i) >= 49 && toEdit.charAt(i) <= 56 || toEdit.charAt(i) == ' ')) {
                return false;
            }
            set.add(toEdit.charAt(i));
        }
        return true;
    }

    private String inputToEdit() {
        Scanner sc = new Scanner(System.in);
        Set<Character> set;
        while (true) {
            set = new HashSet<>();
            System.out.print("\nWhat all do you want to edit? \n(1)First name " +
                    "(2)Last name (3)Address (4)City (5)State (6)Pin (7)Phone number (8)Email -> ");
            String toEdit = sc.nextLine();
            if (checkToEditIsLegit(set, toEdit)) {
                StringBuilder toEditWithoutDuplicates = new StringBuilder();
                for (Character c : set) {
                    toEditWithoutDuplicates.append(c);
                }
                return toEditWithoutDuplicates.toString();
            } else {
                System.out.println("Illegal Input!!!");
            }
        }
    }

    private boolean checkNumberIsLegit(String num, String type) {
        if (type.equals(AddressBookConstants.PIN_NUM) && num.length() != 6) return false;
        if (type.equals(AddressBookConstants.PHONE_NUM) && num.length() != 10) return false;
        for (int i = 0; i < num.length(); i++) {
            if (!(num.charAt(i) >= 48 && num.charAt(i) <= 57)) {
                return false;
            }
        }
        return true;
    }

    private String inputCorrectNumber(String type) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter " + type + " : ");
            String num = sc.next();
            sc.nextLine();
            if (!checkNumberIsLegit(num, type))
                System.out.println("Illegal Input!!!");
            else {
                return num;
            }
        }
    }

    public void printAddressBook(ArrayList<Contact> book) {
        for (Contact contact : book)
            System.out.println(contact);
        System.out.println();
    }

    public void printDictionary() {
        if (dictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        for (Map.Entry<String, ArrayList<Contact>> book : dictionary.entrySet()) {
            if (book.getValue().size() == 0) continue;
            System.out.println(book.getKey() + " -> ");
            printAddressBook(book.getValue());
        }
    }

}
