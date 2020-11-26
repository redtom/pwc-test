package haywood.tom;

import com.google.common.collect.Lists;
import haywood.tom.model.AddressBook;
import haywood.tom.model.Contact;
import java.util.ArrayList;

/**
 * Utility class for testing
 */
public final class AddressBookTestUtils {
    
    public static final String EMPTY_ADDRESS_BOOK_NAME = "empty";
    public static final String POPULATED_ADDRESS_BOOK_NAME = "populated";
    
    public static final String TEST_ENTRY_NAME_1 = "Bob";
    public static final String TEST_ENTRY_NAME_2 = "Todd";
    public static final String TEST_ENTRY_NAME_3 = "Rod";
    public static final String TEST_ENTRY_NAME_4 = "Zodd";
    
    public static final String TEST_NUMBER_1 = "98765432";
    public static final String TEST_NUMBER_2 = "23456789";
    public static final String TEST_NUMBER_3 = "0401234567";
    public static final String TEST_NUMBER_4 = "+6112345678";
    
    private AddressBookTestUtils() {        
    }
    
    public static AddressBook createEmptyInMemoryAddressBook() {
        return new AddressBook(EMPTY_ADDRESS_BOOK_NAME, new ArrayList<>());
    }
    
    public static AddressBook createPopulatedInMemoryAddressBook() {
        return new AddressBook(POPULATED_ADDRESS_BOOK_NAME, createPopulatedAddressBookList());
    }

    public static ArrayList<Contact> createPopulatedAddressBookList() {
        return Lists.newArrayList(
            new Contact(TEST_ENTRY_NAME_1, TEST_NUMBER_1),
            new Contact(TEST_ENTRY_NAME_2, TEST_NUMBER_2),
            new Contact(TEST_ENTRY_NAME_3, TEST_NUMBER_3));
    }

    public static ArrayList<Contact> createEntryListWith1_2_and_3() {
        return Lists.newArrayList(
            new Contact(TEST_ENTRY_NAME_1, TEST_NUMBER_1),
            new Contact(TEST_ENTRY_NAME_2, TEST_NUMBER_2),
            new Contact(TEST_ENTRY_NAME_3, TEST_NUMBER_3));
    }

    public static ArrayList<Contact> createEntryListWith2_3_and_4() {
        return Lists.newArrayList(
            new Contact(TEST_ENTRY_NAME_2, TEST_NUMBER_2),
            new Contact(TEST_ENTRY_NAME_3, TEST_NUMBER_3),
            new Contact(TEST_ENTRY_NAME_4, TEST_NUMBER_4));
    }
}
