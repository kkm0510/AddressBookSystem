package addressbook;

public enum InputEnum {

    FIRST_NAME ("first name", "^[A-Z][a-z]{2,}$"),
    LAST_NAME  ("last name", "^[A-Z][a-z]{2,}$"),
    ADDRESS ("address", "^[a-zA-Z0-9 -]{8,}$"),
    CITY  ("city", "^[A-Z][a-z]{2,}$"),
    STATE  ("state", "^[A-Z][a-z]{2,}([ ][A-Z][a-z]{2,}){0,}$"),
    PIN ("pin", "^[0-9]{6}$"),
    PHONE_NUMBER  ("phone number", "^[6-9][0-9]{9}$"),
    EMAIL  ("email id", "^[a-zA-Z0-9]+[._+-]?[a-zA-Z0-9]+@[a-zA-Z0-9]+[.]+[a-z]{2,4}[.]?[a-z]{0,3}$"),
    EDIT("\nWhat all do you want to edit? \n(1)First name (2)Last name" +
            " (3)Address (4)City (5)State (6)Pin (7)Phone number (8)Email -> ", "^(?!.*([0-8]).*\1)[0-8]([ ][0-8]){0,7}$");

    private final String value;
    private final String regex;

    InputEnum(String value, String regex){
        this.value =value;
        this.regex=regex;
    }

    public String getValue() {
        return value;
    }

    public String getRegex() {
        return regex;
    }
}
