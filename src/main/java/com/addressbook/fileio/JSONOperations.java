package com.addressbook.fileio;

import com.addressbook.model.Contact;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class JSONOperations {

    public static final String INPUT_PATH = "src/main/resources/input/ABDataIn.json";
    public static final String OUTPUT_PATH = "output/ABDataOut.json";

    public List<Contact> getListOfData() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(INPUT_PATH));
            Gson gson = new Gson();
            return gson.fromJson(reader, new TypeToken<List<Contact>>(){}.getType());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, List<Contact>> readDictionary() {
        List<Contact> listOfContact = getListOfData();
        Map<String, List<Contact>> dictionary = new HashMap<>();
        for (Contact c : listOfContact) {
            if (dictionary.containsKey(c.getBookName())) {
                if (!dictionary.get(c.getBookName()).contains(c))
                    dictionary.get(c.getBookName()).add(c);
            } else {
                List<Contact> list = new ArrayList<>();
                list.add(c);
                dictionary.put(c.getBookName(), list);
            }
        }
        return dictionary;
    }

    public void writeDictionary(Map<String, List<Contact>> map) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer;
        try {
            writer = new FileWriter(OUTPUT_PATH);
            Type mapType = new TypeToken<Map<String, List<Contact>>>() {}.getType();
            gson.toJson(map, mapType, writer);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeListOfContacts(List<Contact> list) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer;
        try {
            writer = new FileWriter(OUTPUT_PATH);
            Type listType = new TypeToken<LinkedList<Contact>>() {}.getType();
            gson.toJson(list, listType, writer);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeCountDictionary(Map<String, Long> map){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer;
        try {
            writer = new FileWriter(OUTPUT_PATH);
            Type mapType = new TypeToken<Map<String, Long>>() {}.getType();
            gson.toJson(map, mapType, writer);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeStreamOfContact(Stream<Contact> stream) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer;
        try {
            writer = new FileWriter(OUTPUT_PATH);
            Type streamType = new TypeToken<Stream<Contact>>() {}.getType();
            gson.toJson(stream, streamType, writer);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

