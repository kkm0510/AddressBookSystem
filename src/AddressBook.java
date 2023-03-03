import java.util.ArrayList;
import java.util.Scanner;

public class AddressBook {

    static final ArrayList<Contact> book=new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        Contact person=new Contact();
        System.out.println("Enter first name : ");
        String firstName=sc.nextLine();
        System.out.println("Enter last name : ");
        String lastName=sc.nextLine();
        System.out.println("Enter address : ");
        String address=sc.nextLine();
        System.out.println("Enter city : ");
        String city=sc.nextLine();
        System.out.println("Enter state : ");
        String state=sc.nextLine();
        System.out.println("Enter pin : ");
        int pin=sc.nextInt();
        System.out.println("Enter phone number : ");
        long phoneNumber=sc.nextLong();
        System.out.println("Enter email : ");
        String email=sc.nextLine();

        person.firstName=firstName;
        person.lastName= lastName;
        person.address=address;
        person.city=city;
        person.state=state;
        person.pin=pin;
        person.phoneNumber=phoneNumber;
        person.email=email;
        book.add(person);
    }
}
