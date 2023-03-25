package com.addressbook.models;

import com.opencsv.bean.CsvBindByPosition;

public class InvalidContact {

    @CsvBindByPosition(position = 0)
    private final String REASON;

    @CsvBindByPosition(position = 1)
    private final Contact CONTACT;

    public InvalidContact(Contact contact, String reason) {
        this.CONTACT = contact;
        this.REASON = reason;
    }

    @Override
    public String toString() {
        return "InvalidContact{" +
                "REASON='" + REASON + '\'' +
                ", CONTACT=" + CONTACT +
                '}';
    }
}
