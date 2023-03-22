package addressbook;

public class Contact implements Comparable<Contact> {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String pin;
    private String phoneNumber;
    private String email;

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

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPin() {
        return pin;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "\nFirst Name: " + firstName +
                "\nLast Name: " + lastName +
                "\nAddress: " + address +
                "\nCity: " + city +
                "\nState: " + state +
                "\nPin: " + pin +
                "\nPhone Number: " + phoneNumber +
                "\nEmail: " + email +
                "\n";
    }

    @Override
    public int hashCode() {
        return firstName == null && lastName == null ? 0 : (firstName + lastName).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Contact c = (Contact) obj;
        return (this.firstName + this.lastName).equals(c.firstName + (c.lastName));
    }

    @Override
    public int compareTo(Contact o) {
        return (this.firstName+this.lastName).compareTo(o.firstName+o.lastName);
    }
}