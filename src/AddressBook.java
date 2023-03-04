import java.util.ArrayList;
import java.util.Scanner;

public class AddressBook {

    static final ArrayList<Contact> book = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void editContact() {

        final int FIRST_NAME=1;
        final int LAST_NAME=2;
        final int ADDRESS=3;
        final int CITY=4;
        final int STATE=5;
        final int PIN=6;
        final int PHONE_NUMBER=7;
        final int EMAIL=8;

        sc.nextLine();
        System.out.print("Enter first name : ");
        String firstName = sc.nextLine();
        System.out.print("Enter last name : ");
        String lastName = sc.nextLine();

        for (Contact c : book) {
            if (c.firstName.equals(firstName) && c.lastName.equals(lastName)) {
                System.out.print("What do you want to edit? (1)first name (2)last name (3)address (4)city (5)state (6)pin (7)phone number (8)email -> ");
                int choice = sc.nextInt();
                switch (choice) {
                    case FIRST_NAME -> {
                        System.out.print("Enter new first name : ");
                        c.firstName = sc.nextLine();
                    }
                    case LAST_NAME -> {
                        System.out.print("Enter new last name : ");
                        c.lastName = sc.nextLine();
                    }
                    case ADDRESS -> {
                        System.out.print("Enter new address : ");
                        c.address = sc.nextLine();
                    }
                    case  CITY -> {
                        System.out.print("Enter new city : ");
                        c.city = sc.nextLine();
                    }
                    case STATE -> {
                        System.out.print("Enter new state : ");
                        c.state = sc.nextLine();
                    }
                    case PIN -> {
                        System.out.print("Enter new pin : ");
                        c.pin = sc.nextInt();
                    }
                    case PHONE_NUMBER -> {
                        System.out.print("Enter new phone number : ");
                        c.phoneNumber = sc.nextLong();
                    }
                    case EMAIL -> {
                        System.out.print("Enter new EMAIL : ");
                        c.email = sc.nextLine();
                    }

                }
                System.out.println("Contact edited successfully");
                sc.nextLine();
                return;
            }
        }
        System.out.println("Contact doesn't exist in address book");
    }

    public static void deleteContact() {
        sc.nextLine();
        System.out.print("Enter first name : ");
        String firstName = sc.nextLine();
        System.out.print("Enter last name : ");
        String lastName = sc.nextLine();

        for (Contact c : book) {
            if (c.firstName.equals(firstName) && c.lastName.equals(lastName)) {
                book.remove(c);
            }
        }
        System.out.println("Contact deleted");
    }

    public static void printAddressBook() {
        for (Contact c : book) {
            System.out.println();
            System.out.println("Name : " + c.firstName + " " + c.lastName);
            System.out.println("Address : " + c.address);
            System.out.println("City : " + c.city);
            System.out.println("State : " + c.state);
            System.out.println("Pin : " + c.pin);
            System.out.println("Phone No : " + c.phoneNumber);
            System.out.println("Email : " + c.email);
        }
    }

    public static void main(String[] args) {
        final int ADD_CONTACT=1;
        final int EDIT_CONTACT=2;
        final int DELETE_CONTACT=3;
        final int PRINT_BOOK=4;
        while (true) {
            System.out.print("Enter choice : (1)Add contact (2)edit contact (3)delete contact (4)Print Address Book (0)Exit -> ");
            int choice = sc.nextInt();
            switch (choice) {
                case ADD_CONTACT -> {
                    Contact person = new Contact();
                    book.add(person);
                }
                case EDIT_CONTACT -> editContact();
                case DELETE_CONTACT -> deleteContact();
                case PRINT_BOOK -> printAddressBook();
            }
            if (choice == 0) break;
        }
    }
}
