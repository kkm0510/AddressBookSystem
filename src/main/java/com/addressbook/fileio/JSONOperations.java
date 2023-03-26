package com.addressbook.fileio;

import com.addressbook.models.Contact;
import com.addressbook.models.InvalidContact;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static com.addressbook.enums.InputEnum.*;
import static com.addressbook.enums.InputEnum.EMAIL;

public class JSONOperations implements ABFileOperations {

    public static final String INPUT_PATH = "src/main/resources/input/ABDataIn.json";
    public static final String OUTPUT_PATH = "output/ABDataOut.json";
    public static final String INVALID_DATA_PATH = "output/invalid/ABInvalidData.json";

    private final List<InvalidContact> INVALID_DATA_LIST;

    public JSONOperations() {
        INVALID_DATA_LIST = new LinkedList<>();
    }

    public List<InvalidContact> getInvalidDataList() {
        return INVALID_DATA_LIST;
    }

    @Override
    public List<Contact> getListOfData() {
        try (Reader reader = Files.newBufferedReader(Paths.get(INPUT_PATH))) {
            Gson gson = new Gson();
            List<Contact> list = gson.fromJson(reader, new TypeToken<List<Contact>>() {
            }.getType());
            List<Contact> checkedList = new LinkedList<>();
            for (Contact c : list) {
                if (!isValidContact(c)) {
                    System.out.println("\nSkipped -> Invalid contact: \n" + c);
                    InvalidContact invalidContact = new InvalidContact(c, "Invalid: failed in regex check");
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
        List<Contact> listOfContact = getListOfData();
        Map<String, List<Contact>> dictionary = new HashMap<>();
        for (Contact c : listOfContact) {
            if (dictionary.containsKey(c.getBookName())) {
                if (!dictionary.get(c.getBookName()).contains(c))
                    dictionary.get(c.getBookName()).add(c);
                else {
                    System.out.println("\nSkipped -> Contact with name " + c.getFirstName() + " " + c.getLastName() +
                            " already exists in this book!!!\n" + c);
                    InvalidContact invalidContact = new InvalidContact(c, "Duplicate: contact with same name already exists in book");
                    INVALID_DATA_LIST.add(invalidContact);
                }
            } else {
                List<Contact> list = new ArrayList<>();
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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(OUTPUT_PATH)) {
            Type mapType = new TypeToken<Map<String, Long>>() {
            }.getType();
            gson.toJson(map, mapType, writer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void writeListOfContact(List<Contact> contactList) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(OUTPUT_PATH)) {
            Type listType = new TypeToken<List<Contact>>() {
            }.getType();
            gson.toJson(contactList, listType, writer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void writeListOfInvalidContact(List<InvalidContact> invalidContactList) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(INVALID_DATA_PATH)) {
            Type listType = new TypeToken<List<InvalidContact>>() {
            }.getType();
            gson.toJson(invalidContactList, listType, writer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

