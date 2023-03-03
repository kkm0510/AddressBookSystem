import java.util.ArrayList;
import java.util.Scanner;

public class AddressBook {

    static final ArrayList<Contact> book=new ArrayList<>();
    static Scanner sc=new Scanner(System.in);

    public static Contact createContact(){
        Contact person=new Contact();
        System.out.println("New Entry : ");
        System.out.print("Enter first name : ");
        String firstName=sc.nextLine();
        System.out.print("Enter last name : ");
        String lastName=sc.nextLine();
        System.out.print("Enter address : ");
        String address=sc.nextLine();
        System.out.print("Enter city : ");
        String city=sc.nextLine();
        System.out.print("Enter state : ");
        String state=sc.nextLine();
        System.out.print("Enter pin : ");
        int pin=sc.nextInt();
        System.out.print("Enter phone number : ");
        long phoneNumber=sc.nextLong();
        System.out.print("Enter email : ");
        String email=sc.nextLine();
        sc.nextLine();

        person.firstName=firstName;
        person.lastName= lastName;
        person.address=address;
        person.city=city;
        person.state=state;
        person.pin=pin;
        person.phoneNumber=phoneNumber;
        person.email=email;
        return person;
    }

    public static void editContact(){
        System.out.print("Enter first name : ");
        String firstName=sc.nextLine();
        System.out.print("Enter last name : ");
        String lastName=sc.nextLine();
        for(Contact c:book){
            if(c.firstName.equals(firstName) && c.lastName.equals(lastName)){
                book.remove(c);
                return;
            }
        }
        System.out.println("Contact doesn't exist in address book");
    }

    public static void main(String[] args) {
        Contact person1=createContact();
        Contact person2=createContact();
        book.add(person1);
        book.add(person2);
        editContact();
    }
}
