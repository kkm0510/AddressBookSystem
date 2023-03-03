import java.util.ArrayList;
import java.util.Scanner;

public class AddressBook {

    static final ArrayList<Contact> book = new ArrayList<>();

    public static void editContact() {
        Scanner sc = new Scanner(System.in);
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
                        c.firstName=sc.nextLine();
                    }
                    case 2 -> {
                        System.out.print("Enter new last name : ");
                        c.lastName=sc.nextLine();
                    }
                    case 3 -> {
                        System.out.print("Enter new address : ");
                        c.address=sc.nextLine();
                    }
                    case 4 -> {
                        System.out.println("Enter new city : ");
                        c.city=sc.nextLine();
                    }
                    case 5 -> {
                        System.out.println("Enter new state : ");
                        c.state=sc.nextLine();
                    }
                    case 6 -> {
                        System.out.println("Enter new pin : ");
                        c.pin=sc.nextInt();
                    }
                    case 7 -> {
                        System.out.println("Enter new phone number : ");
                        c.phoneNumber=sc.nextLong();
                    }
                }
            }
        }
        System.out.println("Contact doesn't exist in address book");
    }

    public static void main(String[] args) {
        Contact person1 = new Contact();
        Contact person2 = new Contact();
        book.add(person1);
        book.add(person2);
        editContact();
    }
}
