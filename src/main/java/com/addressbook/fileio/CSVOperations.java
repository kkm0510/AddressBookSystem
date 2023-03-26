package com.addressbook.fileio;

import static com.addressbook.enums.InputEnum.*;

import com.addressbook.models.Contact;
import com.addressbook.models.InvalidContact;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;


import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CSVOperations implements ABFileOperations {

    public static final String INPUT_PATH = "src/main/resources/input/ABDataIn.csv";
    public static final String OUTPUT_PATH = "output/ABDataOut.csv";
    public static final String INVALID_DATA_PATH = "output/invalid/ABInvalidData.csv";

    private final List<InvalidContact> INVALID_DATA_LIST;

    public CSVOperations() {
        INVALID_DATA_LIST = new LinkedList<>();
    }

    public List<InvalidContact> getInvalidDataList() {
        return INVALID_DATA_LIST;
    }

    @Override
    public List<Contact> getListOfData() {
        try (Reader reader = Files.newBufferedReader(Paths.get(INPUT_PATH))) {
            CsvToBeanBuilder<Contact> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(Contact.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<Contact> csvToBean = csvToBeanBuilder.build();
            List<Contact> list = csvToBean.parse();
            List<Contact> checkedList = new LinkedList<>();
            for (Contact c : list) {
                if (c.getBookName().equals("Book Name")) continue;
                if (!isValidContact(c)) {
                    System.out.println("\nSkipped -> Invalid contact: \n" + c);
                    InvalidContact invalidContact = new InvalidContact(c, "is invalid: failed in regex check");
                    INVALID_DATA_LIST.add(invalidContact);
                    continue;
                }
                checkedList.add(c);
            }
            return checkedList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, List<Contact>> readDictionary() {
        List<Contact> listOfContact;
        try {
            listOfContact = getListOfData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        Map<String, List<Contact>> dictionary = new HashMap<>();
        for (Contact c : listOfContact) {
            if (c.getBookName().equals("Book Name")) continue;
            if (dictionary.containsKey(c.getBookName())) {
                if (!dictionary.get(c.getBookName()).contains(c))
                    dictionary.get(c.getBookName()).add(c);
                else {
                    System.out.println("\nSkipped -> Contact with name " + c.getFirstName() + " " + c.getLastName() +
                            " already exists in this book!!!\n" + c);
                    InvalidContact invalidContact = new InvalidContact(c, "is duplicate: contact with same name already exists in book");
                    INVALID_DATA_LIST.add(invalidContact);
                }
            } else {
                List<Contact> list = new LinkedList<>();
                list.add(c);
                dictionary.put(c.getBookName(), list);
            }
        }
        return dictionary;
    }

    @Override
    public boolean isValidContact(Contact contact) {
        if (!contact.getFirstName().matches(FIRST_NAME.getRegex())) {
            return false;
        }
        if (!contact.getLastName().matches(LAST_NAME.getRegex())) {
            return false;
        }
        if (!contact.getAddress().matches(ADDRESS.getRegex())) {
            return false;
        }
        if (!contact.getCity().matches(CITY.getRegex())) {
            return false;
        }
        if (!contact.getState().matches(STATE.getRegex())) {
            return false;
        }
        if (!contact.getPin().matches(PIN.getRegex())) {
            return false;
        }
        if (!contact.getPhoneNumber().matches(PHONE_NUMBER.getRegex())) {
            return false;
        }
        return contact.getEmail().matches(EMAIL.getRegex());
    }

    @Override
    public void writeDictionary(Map<String, List<Contact>> map) {
        List<Contact> list = new LinkedList<>();
        map.forEach((key, value) -> list.addAll(value));
        writeListOfContact(list);
    }

    @Override
    public void writeCountDictionary(Map<String, Long> map) {
        try (Writer writer = new FileWriter(OUTPUT_PATH)) {
            List<Map.Entry<String, Long>> list = new ArrayList<>(map.entrySet());
            for (Map.Entry<String, Long> entry : list) {
                writer.write(entry.getKey() + " : " + entry.getValue() + "\n");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void writeListOfContact(List<Contact> contactList) {
        try ( Writer writer = new FileWriter(OUTPUT_PATH)){
            StatefulBeanToCsvBuilder<Contact> builder = new StatefulBeanToCsvBuilder<>(writer);
            StatefulBeanToCsv<Contact> beanWriter = builder.build();
            Contact contact=new Contact();
            beanWriter.write(getHeaderForOutput(contact));
            beanWriter.write(contactList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void writeListOfInvalidContact(List<InvalidContact> invalidContactList) {
        try (Writer writer = new FileWriter(INVALID_DATA_PATH)) {
            StatefulBeanToCsvBuilder<InvalidContact> builder = new StatefulBeanToCsvBuilder<>(writer);
            StatefulBeanToCsv<InvalidContact> beanWriter = builder.build();
            InvalidContact invalidContact=new InvalidContact();
            invalidContact.setReason("Reason");
            beanWriter.write(getHeaderForOutput(invalidContact));
            beanWriter.write(invalidContactList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private <T extends Contact> T getHeaderForOutput(T contact) {
        contact.setBookName("Book Name");
        contact.setFirstName("First Name");
        contact.setLastName("Last Name");
        contact.setAddress("Address");
        contact.setCity("City");
        contact.setState("State");
        contact.setPin("Pin");
        contact.setPhoneNumber("Phone Number");
        contact.setEmail("Email");
        return contact;
    }
}