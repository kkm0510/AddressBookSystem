package com.addressbook.fileio;

import com.addressbook.models.Contact;
import com.addressbook.models.InvalidContact;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface ABFileOperations {

    List<Contact> getListOfData();

    Map<String, List<Contact>> readDictionary();

    boolean isValidContact(Contact contact);

    void writeDictionary(Map<String, List<Contact>> map);

    void writeCountDictionary(Map<String, Long> map);

    void writeListOfContact(List<Contact> contactList);

    void writeListOfInvalidContact(List<InvalidContact> invalidContactList);
}
