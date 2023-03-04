import java.util.Scanner;

public class Contact {

    String firstName;
    String lastName;
    String address;
    String city;
    String state;
    int pin;
    long phoneNumber;
    String email;

    public Contact(){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter first name : ");
        firstName=sc.next();
        sc.nextLine();
        System.out.print("Enter last name : ");
        lastName=sc.next();
        sc.nextLine();
        System.out.print("Enter address : ");
        address=sc.nextLine();
        System.out.print("Enter city : ");
        city=sc.next();
        sc.nextLine();
        System.out.print("Enter state : ");
        state=sc.nextLine();
        System.out.print("Enter pin : ");
        pin=sc.nextInt();
        sc.nextLine();
        System.out.print("Enter phone number : ");
        phoneNumber=sc.nextLong();
        sc.nextLine();
        System.out.print("Enter email : ");
        email=sc.nextLine();
        System.out.println();
    }
}
