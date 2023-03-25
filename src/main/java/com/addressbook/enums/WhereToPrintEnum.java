package com.addressbook.enums;

import com.addressbook.fileio.CSVOperations;
import com.addressbook.fileio.JSONOperations;
import com.addressbook.models.Contact;

import java.util.List;
import java.util.Map;

public enum WhereToPrintEnum {

    CONSOLE {

        @Override
        public void printDictionary(Map<String, List<Contact>> dictionary) {
            dictionary.entrySet().stream()
                    .filter(entry -> entry.getValue().size() != 0)
                    .forEach(entry -> System.out.println("~~~~~~~~~~~~~\n" + entry.getKey() + " -> \n" + entry.getValue()));
        }

        @Override
        public void printCountDictionary(Map<String, Long> dictionary) {
            dictionary.forEach((key, value) -> System.out.println(key + " -> " + value));
        }

        @Override
        public void printContactList(List<Contact> list) {
            list.forEach(System.out::println);
        }
    },

    CSV {

        @Override
        public void printDictionary(Map<String, List<Contact>> dictionary) {
            CSVOperations csv=new CSVOperations();
            csv.writeDictionary(dictionary);
            System.out.println("Data written successfully");
        }

        @Override
        public void printCountDictionary(Map<String, Long> dictionary) {
            CSVOperations csv=new CSVOperations();
            csv.writeCountDictionary(dictionary);
            System.out.println("Data written successfully");
        }

        @Override
        public void printContactList(List<Contact> list) {
            CSVOperations csv=new CSVOperations();
            csv.writeListOfContact(list);
            System.out.println("Data written successfully");
        }
    },

    JSON {

        @Override
        public void printDictionary(Map<String, List<Contact>> dictionary) {
            JSONOperations json=new JSONOperations();
            json.writeDictionary(dictionary);
            System.out.println("Data written successfully");
        }

        @Override
        public void printCountDictionary(Map<String, Long> dictionary) {
            JSONOperations json=new JSONOperations();
            json.writeCountDictionary(dictionary);
            System.out.println("Data written successfully");
        }

        @Override
        public void printContactList(List<Contact> list) {
            JSONOperations json=new JSONOperations();
            json.writeListOfContact(list);
            System.out.println("Data written successfully");
        }
    };

    public abstract void printDictionary(Map<String, List<Contact>> dictionary);

    public abstract void printCountDictionary(Map<String, Long> dictionary);

    public abstract void printContactList(List<Contact> list);

}
