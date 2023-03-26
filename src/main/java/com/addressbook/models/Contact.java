package com.addressbook.models;

import com.opencsv.bean.CsvBindByPosition;

public class Contact implements Comparable<Contact> {

    @CsvBindByPosition(position = 0)
    protected String bookName;

    @CsvBindByPosition(position = 1)
    protected String firstName;

    @CsvBindByPosition(position = 2)
    protected String lastName;

    @CsvBindByPosition(position = 3)
    protected String address;

    @CsvBindByPosition(position = 4)
    protected String city;

    @CsvBindByPosition(position = 5)
    protected String state;

    @CsvBindByPosition(position = 6)
    protected String pin;

    @CsvBindByPosition(position = 7)
    protected String phoneNumber;

    @CsvBindByPosition(position = 8)
    protected String email;

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

    public String getAddress() {
        return address;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public String toString() {
        return "\nFirst Name = " + firstName +
                "\nLast Name = " + lastName  +
                "\nAddress = " + address  +
                "\nCity = " + city  +
                "\nState = " + state +
                "\nPin = " + pin +
                "\nPhone Number = " + phoneNumber +
                "\nEmail = " + email +
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
        return (this.firstName + this.lastName).compareTo(o.firstName + o.lastName);
    }

}