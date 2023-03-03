import java.util.ArrayList;
import java.util.Scanner;

public class AddressBook {

    static final ArrayList<Contact> book = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void editContact() {
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
                    case 1 -> {
                        System.out.print("Enter new first name : ");
                        c.firstName = sc.nextLine();
                    }
                    case 2 -> {
                        System.out.print("Enter new last name : ");
                        c.lastName = sc.nextLine();
                    }
                    case 3 -> {
                        System.out.print("Enter new address : ");
                        c.address = sc.nextLine();
                    }
                    case 4 -> {
                        System.out.print("Enter new city : ");
                        c.city = sc.nextLine();
                    }
                    case 5 -> {
                        System.out.print("Enter new state : ");
                        c.state = sc.nextLine();
                    }
                    case 6 -> {
                        System.out.print("Enter new pin : ");
                        c.pin = sc.nextInt();
                    }
                    case 7 -> {
                        System.out.print("Enter new phone number : ");
                        c.phoneNumber = sc.nextLong();
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
        while (true) {
            System.out.print("Enter choice : (1)Add contact (2)edit contact (3)delete contact (4)Print Address Book (0)Exit -> ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> {
                    Contact person = new Contact();
                    book.add(person);
                }
                case 2 -> editContact();
                case 3 -> deleteContact();
                case 4 -> printAddressBook();
            }
            if (choice == 0) break;
        }
    }
}
