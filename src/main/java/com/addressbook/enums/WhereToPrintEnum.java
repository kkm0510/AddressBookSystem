package com.addressbook.enums;

import com.addressbook.fileio.CSVOperations;
import com.addressbook.fileio.JSONOperations;
import com.addressbook.model.Contact;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
        public void printContactStream(Stream<Contact> stream) {
            stream.forEach(System.out::println);
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
        public void printContactStream(Stream<Contact> stream) {
            CSVOperations csv=new CSVOperations();
            csv.writeStreamOfContact(stream);
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
        public void printContactStream(Stream<Contact> stream) {
            JSONOperations json=new JSONOperations();
            json.writeStreamOfContact(stream);
            System.out.println("Data written successfully");
        }
    };

    public abstract void printDictionary(Map<String, List<Contact>> dictionary);

    public abstract void printCountDictionary(Map<String, Long> dictionary);

    public abstract void printContactStream(Stream<Contact> stream);

}
