import java.util.Scanner;

public class Contact {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private int pin;
    private long phoneNumber;
    private String email;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
