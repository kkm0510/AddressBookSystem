package com.addressbook.models;

import com.opencsv.bean.CsvBindByPosition;

public class InvalidContact {

//    @CsvBindByPosition(position = 0)
//    private final String REASON;
//
//    @CsvBindByPosition(position = 1)
//    private final Contact CONTACT;


    @CsvBindByPosition(position = 0)
    private String reason;

    @CsvBindByPosition(position = 1)
    private String bookName;

    @CsvBindByPosition(position = 2)
    private String firstName;

    @CsvBindByPosition(position = 3)
    private String lastName;

    @CsvBindByPosition(position = 4)
    private String address;

    @CsvBindByPosition(position = 5)
    private String city;

    @CsvBindByPosition(position = 6)
    private String state;

    @CsvBindByPosition(position = 7)
    private String pin;

    @CsvBindByPosition(position = 8)
    private String phoneNumber;

    @CsvBindByPosition(position = 9)
    private String email;

    public InvalidContact() {
    }

    public InvalidContact(Contact c, String reason) {
        bookName = c.getBookName();
        firstName = c.getFirstName();
        lastName = c.getLastName();
        address = c.getAddress();
        city = c.getCity();
        state = c.getState();
        pin = c.getPin();
        phoneNumber = c.getPhoneNumber();
        email = c.getEmail();
        this.reason = reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
        return "InvalidContact{" +
                "reason='" + reason + '\'' +
                ", bookName='" + bookName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", pin='" + pin + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
