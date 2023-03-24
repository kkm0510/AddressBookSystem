package com.addressbook.fileio.csvoperations;

import com.addressbook.model.Contact;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ReadData {

    public static final String INPUT_PATH = "src/main/resources/input/csvdata/address book data.csv";

    public List<AddressBookCSV> getListOfData() {
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get(INPUT_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CsvToBeanBuilder<AddressBookCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(AddressBookCSV.class);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        CsvToBean<AddressBookCSV> csvToBean = csvToBeanBuilder.build();
        return csvToBean.stream().toList();
    }

    public boolean setContactData(Contact c, AddressBookCSV ab, List<Contact> book) {
        c.setFirstName(ab.getFirstName());
        c.setLastName(ab.getLastName());
        if(book.contains(c)) return true;
        c.setAddress(ab.getAddress());
        c.setCity(ab.getCity());
        c.setState(ab.getState());
        c.setPin(ab.getPin());
        c.setPhoneNumber(ab.getPhoneNumber());
        c.setEmail(ab.getEmail());
        return false;
    }

    public Map<String, List<Contact>> loadCSVDictionary() {
        List<AddressBookCSV> listOfCSVData = getListOfData();
        Map<String, List<Contact>> dictionary = new HashMap<>();
        for (AddressBookCSV ab : listOfCSVData) {
            if(ab.getBookName().equals("Book Name")) continue;
            if (dictionary.containsKey(ab.getBookName())) {
                Contact c = new Contact();
                boolean isDuplicate= setContactData(c, ab, dictionary.get(ab.getBookName()));
                if(isDuplicate) continue;
                dictionary.get(ab.getBookName()).add(c);
            } else {
                List<Contact> list = new LinkedList<>();
                Contact c = new Contact();
                setContactData(c, ab, list);
                list.add(c);
                dictionary.put(ab.getBookName(), list);
            }
        }
        return dictionary;
    }
}
