public class Contact {

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

    public void setCity(String city) {
        this.city = city;
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
        return "[First Name: " + firstName +
                " | Last Name: " + lastName +
                " | Address: " + address +
                " | City: " + city +
                " | State: " + state +
                " | Pin: " + pin +
                " | Phone Number: " + phoneNumber +
                " | Email: " + email + "]";
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
}