package com.addressbook.fileio;

import com.addressbook.model.Contact;
import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class CSVOperations {

    public static final String INPUT_PATH = "src/main/resources/input/ABDataIn.csv";
    public static final String OUTPUT_PATH = "output/ABDataOut.csv";

    public List<Contact> getListOfData() {
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(INPUT_PATH));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        CsvToBeanBuilder<Contact> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(Contact.class);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        CsvToBean<Contact> csvToBean = csvToBeanBuilder.build();
        return csvToBean.stream().toList();
    }

    public Map<String, List<Contact>> readDictionary() {
        List<Contact> listOfContact = getListOfData();
        Map<String, List<Contact>> dictionary = new HashMap<>();
        for (Contact c : listOfContact) {
            if (c.getBookName().equals("Book Name")) continue;
            if (dictionary.containsKey(c.getBookName())) {
                if (!dictionary.get(c.getBookName()).contains(c))
                    dictionary.get(c.getBookName()).add(c);
            } else {
                List<Contact> list = new LinkedList<>();
                list.add(c);
                dictionary.put(c.getBookName(), list);
            }
        }
        return dictionary;
    }

    private Contact getHeaderObject(){
        Contact c = new Contact();
        c.setBookName("Book Name");
        c.setFirstName("First Name");
        c.setLastName("Last Name");
        c.setAddress("Address");
        c.setCity("City");
        c.setState("State");
        c.setPin("Pin");
        c.setPhoneNumber("Phone Number");
        c.setEmail("Email");
        return c;
    }

    public void writeDictionary(Map<String, List<Contact>> map) {
        try {
            Writer writer = new FileWriter(OUTPUT_PATH);
            StatefulBeanToCsvBuilder<Contact> builder = new StatefulBeanToCsvBuilder<>(writer);
            StatefulBeanToCsv<Contact> beanWriter = builder.build();
            List<Contact> list = new LinkedList<>();
            map.forEach((key, value) -> list.addAll(value));
            beanWriter.write(getHeaderObject());
            beanWriter.write(list);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeCountDictionary(Map<String, Long> map) {
        try {
            Writer writer = new FileWriter(OUTPUT_PATH);
            List<Map.Entry<String, Long>> list = new ArrayList<>(map.entrySet());
            list.forEach(entry -> {
                try {
                    writer.write(entry.getKey() + " : " + entry.getValue()+"\n");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeStreamOfContact(Stream<Contact> stream) {
        try {
            Writer writer = new FileWriter(OUTPUT_PATH);
            StatefulBeanToCsvBuilder<Contact> builder = new StatefulBeanToCsvBuilder<>(writer);
            StatefulBeanToCsv<Contact> beanWriter = builder.build();
            beanWriter.write(getHeaderObject());
            beanWriter.write(stream);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}