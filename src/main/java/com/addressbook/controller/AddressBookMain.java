package com.addressbook.controller;

import static com.addressbook.util.Util.*;

import com.addressbook.exceptions.AddressBookException;
import com.addressbook.service.AddressBookDictionary;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AddressBookMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AddressBookDictionary addressBookDictionary = new AddressBookDictionary();
        while (true) {
            try {
                System.out.print("\nMain menu -> \n(1)Create new address book (2)Choose an address book " +
                        "(3)File Input/Output (4)Print (5)Search (6)Count (7)Sort (0)Exit : ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case CREATE_ADDRESS_BOOK -> addressBookDictionary.createAddressBook();
                    case CHOOSE_ADDRESS_BOOK -> addressBookDictionary.chooseAddressBook();
                    case FILE_IO -> addressBookDictionary.fileIO();
                    case PRINT -> addressBookDictionary.printMenu();
                    case SEARCH -> addressBookDictionary.searchMenu();
                    case COUNT -> addressBookDictionary.count();
                    case SORT -> addressBookDictionary.sort();
                    case EXIT -> {
                        return;
                    }
                    default -> throw new AddressBookException("Invalid Input!!!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!!!");
                sc.nextLine();
            } catch (AddressBookException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
