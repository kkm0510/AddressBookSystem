package com.addressbook.enums;

import com.addressbook.models.Contact;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum DictionaryEnum {

    CITY_DICTIONARY {
        @Override
        public Map<String, List<Contact>> getPlaceDictionary(Stream<Contact> stream) {
            return stream.collect(Collectors.groupingBy(Contact::getCity));
        }

        @Override
        public Map<String, Long> getCountDictionary(Stream<Contact> stream) {
            return stream.collect(Collectors.groupingBy(Contact::getCity, Collectors.counting()));
        }
    },

    STATE_DICTIONARY {
        @Override
        public Map<String, List<Contact>> getPlaceDictionary(Stream<Contact> stream) {
            return stream.collect(Collectors.groupingBy(Contact::getState));
        }

        @Override
        public Map<String, Long> getCountDictionary(Stream<Contact> stream) {
            return stream.collect(Collectors.groupingBy(Contact::getState, Collectors.counting()));
        }
    };

    public abstract Map<String, List<Contact>> getPlaceDictionary(Stream<Contact> stream);

    public abstract Map<String, Long> getCountDictionary(Stream<Contact> stream);
}
