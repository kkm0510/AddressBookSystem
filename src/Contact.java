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
        System.out.println("~~~~New Entry~~~~ ");
        System.out.print("Enter first name : ");
        firstName=sc.nextLine();
        System.out.print("Enter last name : ");
        lastName=sc.nextLine();
        System.out.print("Enter address : ");
        address=sc.nextLine();
        System.out.print("Enter city : ");
        city=sc.nextLine();
        System.out.print("Enter state : ");
        state=sc.nextLine();
        System.out.print("Enter pin : ");
        pin=sc.nextInt();
        System.out.print("Enter phone number : ");
        phoneNumber=sc.nextLong();
        System.out.print("Enter email : ");
        email=sc.nextLine();
        sc.nextLine();
    }
}
