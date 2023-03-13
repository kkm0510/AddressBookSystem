import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {

    Map<String, ArrayList<Contact>> dictionary;

    public AddressBook() {
        dictionary = new HashMap<>();
    }

    //searches contact in the entire dictionary
    private void searchByNumber() {
        if (dictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        String phoneNumber = inputCorrectPhoneNumber();
        boolean contactFound = false;
        for (Map.Entry<String, ArrayList<Contact>> book : dictionary.entrySet()) {
            contactFound = searchInBookByNumber(book.getValue(), book.getKey(), phoneNumber);
        }
        if (!contactFound) System.out.println("Contact not found \n");
    }

    private boolean searchInBookByNumber(ArrayList<Contact> book, String bookName, String phoneNumber) {
        boolean contactFound = false;
        for (Contact contact : book) {
            if (contact.getPhoneNumber().equals(phoneNumber)) {
                System.out.println("Contact found in : " + bookName + " book");
                System.out.println(contact + "\n");
                contactFound = true;
            }
        }
        return contactFound;
    }

    //searches contact in the entire dictionary
    private void searchByName() {
        if (dictionary.size() == 0) {
            System.out.println("No address book present");
            return;
        }
        String firstName = inputCorrectName("first");
        String lastName = inputCorrectName("last");
        boolean contactFound = false;
        for (Map.Entry<String, ArrayList<Contact>> book : dictionary.entrySet()) {
            contactFound = searchInBookByName(book.getValue(), book.getKey(), firstName, lastName);
        }
        if (!contactFound) System.out.println("Contact not found \n");
    }

    private boolean searchInBookByName(ArrayList<Contact> book, String bookName,
                                       String firstName, String lastName) {
        boolean contactFound = false;
        for (Contact contact : book) {
            if (contact.getFirstName().equals(firstName) && contact.getLastName().equals(lastName)) {
                System.out.println("Contact found in : " + bookName + " book");
                System.out.println(contact + "\n");
                contactFound = true;
            }
        }
        return contactFound;
    }

    public void searchContact() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nChoose : \n(1) Search by Name (2)Search by Phone Number : ");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case AddressBookConstants.SEARCH_BY_NAME -> searchByName();
            case AddressBookConstants.SEARCH_BY_PHONE_NUMBER -> searchByNumber();
            default -> System.out.println("Wrong Input");
        }
    }

    public void takeInputInContact(Contact contact) {
        Scanner sc = new Scanner(System.in);
        contact.setFirstName(inputCorrectName("first"));
        contact.setLastName(inputCorrectName("last"));
        System.out.print("Enter address : ");
        contact.setAddress(sc.nextLine());
        System.out.print("Enter city : ");
        contact.setCity(sc.next());
        sc.nextLine();
        System.out.print("Enter state : ");
        contact.setState(sc.nextLine());
        contact.setPin(inputCorrectPin());
        contact.setPhoneNumber(inputCorrectPhoneNumber());
        System.out.print("Enter email : ");
        contact.setEmail(sc.nextLine());
    }

    public void editContact(ArrayList<Contact> book) {
        if (book.size() == 0) {
            System.out.println("AddressBook is empty");
            return;
        }
        Scanner sc = new Scanner(System.in);
        String firstName = inputCorrectName("first");
        String lastName = inputCorrectName("last");

        for (Contact contact : book) {
            if (contact.getFirstName().equals(firstName) && contact.getLastName().equals(lastName)) {
                String toEdit = inputToEdit();
                for (int i = 0; i < toEdit.length(); i++) {
                    if (toEdit.charAt(i) == ' ') continue;
                    int choice = toEdit.charAt(i) - 48;
                    switch (choice) {
                        case AddressBookConstants.FIRST_NAME -> contact.setFirstName(inputCorrectName("first"));
                        case AddressBookConstants.LAST_NAME -> contact.setLastName(inputCorrectName("last"));
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
                        case AddressBookConstants.PIN -> contact.setPin(inputCorrectPin());
                        case AddressBookConstants.PHONE_NUMBER -> contact.setPhoneNumber(inputCorrectPhoneNumber());
                        case AddressBookConstants.EMAIL -> {
                            System.out.print("Enter new EMAIL : ");
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
        String firstName = inputCorrectName("first");
        String lastName = inputCorrectName("last");
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
            System.out.print("\n" + bookName + " -> \nEnter choice : \n(1)Add contacts (2)Edit contact " +
                    "(3)Delete contact (4)Print Address Book (0)Go back to main menu -> ");
            int choice = sc.nextInt();
            switch (choice) {
                case AddressBookConstants.ADD_CONTACT -> addContacts(book);
                case AddressBookConstants.EDIT_CONTACT -> editContact(book);
                case AddressBookConstants.DELETE_CONTACT -> deleteContact(book);
                case AddressBookConstants.PRINT_BOOK -> {
                    if (book.size() == 0) System.out.println("AddressBook is empty");
                    else System.out.println(book);
                }
                case AddressBookConstants.BACK_TO_MAIN_MENU -> {
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
        operateBook(name, book);
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
            if (!(name.charAt(i) >= 65 && name.charAt(i) <= 90
                    || name.charAt(i) >= 97 && name.charAt(i) <= 122)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkPhoneNumberIsLegit(String phoneNumber) {
        if (phoneNumber.length() != 10) return false;
        for (int i = 0; i < phoneNumber.length(); i++) {
            if (!(phoneNumber.charAt(i) >= 48 && phoneNumber.charAt(i) <= 57)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkPinIsLegit(String pin) {
        if (pin.length() != 6) return false;
        for (int i = 0; i < pin.length(); i++) {
            if (!(pin.charAt(i) >= 48 && pin.charAt(i) <= 57)) {
                return false;
            }
        }
        return true;
    }

    private String inputCorrectName(String pos) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter " + pos + " name : ");
            String name = sc.next();
            sc.nextLine();
            if (!checkNameIsLegit(name))
                System.out.println("Illegal Input!!!");
            else {
                return name;
            }
        }
    }

    private String inputCorrectPhoneNumber() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter phone number : ");
            String phoneNumber = sc.next();
            sc.nextLine();
            if (!checkPhoneNumberIsLegit(phoneNumber))
                System.out.println("Illegal Input!!!");
            else {
                return phoneNumber;
            }
        }
    }

    private String inputCorrectPin() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter pin : ");
            String pin = sc.next();
            sc.nextLine();
            if (!checkPinIsLegit(pin))
                System.out.println("Illegal Input!!!");
            else {
                return pin;
            }
        }
    }

    private boolean checkToEditIsLegit(String toEdit) {
        for (int i = 0; i < toEdit.length(); i++) {
            if (!(toEdit.charAt(i) >= 49 && toEdit.charAt(i) <= 56 || toEdit.charAt(i) == ' ')) {
                return false;
            }
        }
        return true;
    }


    private String inputToEdit() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("\nWhat all do you want to edit? \n(1)First name " +
                    "(2)Last name (3)Address (4)City (5)State (6)Pin (7)Phone number (8)Email -> ");
            String toEdit = sc.nextLine();
            if (!checkToEditIsLegit(toEdit))
                System.out.println("Illegal Input!!!");
            else {
                return toEdit;
            }
        }
    }

}
