import java.util.ArrayList;
import java.util.Scanner;

public class AddressBook {

    static final ArrayList<Contact> book=new ArrayList<>();

    public static void editContact(){
        Scanner sc=new Scanner(System.in);
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
        Contact person1=new Contact();
        Contact person2=new Contact();
        book.add(person1);
        book.add(person2);
        editContact();
    }
}
