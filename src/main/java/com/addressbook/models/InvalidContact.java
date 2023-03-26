package com.addressbook.models;

import com.opencsv.bean.CsvBindByPosition;

public class InvalidContact extends Contact{

    @CsvBindByPosition(position = 9)
    private String reason;

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
