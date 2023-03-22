package addressbook;

import addressbook.Contact;

import java.util.Comparator;
import java.util.stream.Stream;

public enum SortEnum {

    SORT_BY_NAME {
        @Override
        public Stream<Contact> getSortedContacts(Stream<Contact> stream) {
            return stream.sorted();
        }
    },

    SORT_BY_CITY {
        @Override
        public Stream<Contact> getSortedContacts(Stream<Contact> stream) {
            return stream.sorted(Comparator.comparing(Contact::getCity));
        }
    },

    SORT_BY_STATE {
        @Override
        public Stream<Contact> getSortedContacts(Stream<Contact> stream) {
            return stream.sorted(Comparator.comparing(Contact::getState));
        }
    },

    SORT_BY_ZIP {
        @Override
        public Stream<Contact> getSortedContacts(Stream<Contact> stream) {
            return stream.sorted(Comparator.comparing(Contact::getPin));
        }
    };

    abstract Stream<Contact> getSortedContacts(Stream<Contact> stream);
}
