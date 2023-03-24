package com.addressbook.fileio.csvoperations;

import com.addressbook.model.Contact;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import java.util.*;

public class WriteData {

    public static final String OUTPUT_PATH = "output/csvoutput/address book output.csv";

    public void writeDataFromDictionary(Map<String, List<Contact>> map) {
        Writer writer;
        try {
            writer = new FileWriter(OUTPUT_PATH);
            StatefulBeanToCsvBuilder<AddressBookCSV> builder = new StatefulBeanToCsvBuilder<>(writer);
            StatefulBeanToCsv<AddressBookCSV> beanWriter = builder.build();
            List<AddressBookCSV> list = new ArrayList<>();
            map.forEach((key, value) -> value
                    .forEach(c -> {
                        AddressBookCSV ab = new AddressBookCSV();
                        ab.setBookName(key);
                        setAddressBookCSVData(ab, c);
                        list.add(ab);
                    }));
            AddressBookCSV ab=new AddressBookCSV();
            ab.setBookName("Book Name");
            ab.setFirstName("First Name");
            ab.setLastName("Last Name");
            ab.setAddress("Address");
            ab.setCity("City");
            ab.setState("State");
            ab.setPin("Pin");
            ab.setPhoneNumber("Phone Number");
            ab.setEmail("Email");
            beanWriter.write(ab);
            beanWriter.write(list);
            writer.close();
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    private void setAddressBookCSVData(AddressBookCSV ab, Contact c) {
        ab.setFirstName(c.getFirstName());
        ab.setLastName(c.getLastName());
        ab.setAddress(c.getAddress());
        ab.setCity(c.getCity());
        ab.setState(c.getState());
        ab.setPin(c.getPin());
        ab.setPhoneNumber(c.getPhoneNumber());
        ab.setEmail(c.getEmail());
    }
}
