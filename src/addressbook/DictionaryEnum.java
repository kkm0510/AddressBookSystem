package addressbook;


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
    },

    STATE_DICTIONARY {
        @Override
        public Map<String, List<Contact>> getPlaceDictionary(Stream<Contact> stream) {
            return stream.collect(Collectors.groupingBy(Contact::getState));
        }
    };

    abstract Map<String, List<Contact>> getPlaceDictionary(Stream<Contact> stream);
}
