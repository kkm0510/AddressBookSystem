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
    public Map<String, List<Contact>> readDictionary() {
        try (Reader reader = Files.newBufferedReader(Paths.get(INPUT_PATH))) {
            Gson gson = new Gson();
            Map<String, List<Contact>> dictionary = gson.fromJson(reader, new TypeToken<Map<String, List<Contact>>>() {
            }.getType());
            return filterInvalidData(dictionary);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Map<String, List<Contact>> filterInvalidData(Map<String, List<Contact>> map) {
        Map<String, List<Contact>> dictionary = new HashMap<>();
        for (Map.Entry<String, List<Contact>> entry : map.entrySet()) {
            List<Contact> list = new LinkedList<>();
            entry.getValue().forEach(contact -> {
                if (!isValidContact(contact)) {
                    System.out.println("\nSkipped -> invalid contact!!! \n" + contact);
                    InvalidContact invalidContact = new InvalidContact(contact, "is invalid: failed in regex check");
                    INVALID_DATA_LIST.add(invalidContact);
                } else if (list.contains(contact)) {
                    System.out.println("\nSkipped -> contact with name " + contact.getFirstName() + " " + contact.getLastName() +
                            " already exists in this book!!!\n" + contact);
                    InvalidContact invalidContact = new InvalidContact(contact, "is duplicate: contact with same name already exists in book");
                    INVALID_DATA_LIST.add(invalidContact);
                } else list.add(contact);
            });
            if(!list.isEmpty()) dictionary.put(entry.getKey(), list);
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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(OUTPUT_PATH)) {
            Type mapType = new TypeToken<Map<String, List<Contact>>>() {
            }.getType();
            gson.toJson(map, mapType, writer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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

