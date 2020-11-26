package haywood.tom.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static haywood.tom.AddressBookTestUtils.EMPTY_ADDRESS_BOOK_NAME;
import static haywood.tom.AddressBookTestUtils.POPULATED_ADDRESS_BOOK_NAME;
import static haywood.tom.AddressBookTestUtils.TEST_ENTRY_NAME_1;
import static haywood.tom.AddressBookTestUtils.TEST_ENTRY_NAME_2;
import static haywood.tom.AddressBookTestUtils.TEST_ENTRY_NAME_3;
import static haywood.tom.AddressBookTestUtils.TEST_ENTRY_NAME_4;
import static haywood.tom.AddressBookTestUtils.TEST_NUMBER_1;
import static haywood.tom.AddressBookTestUtils.TEST_NUMBER_2;
import static haywood.tom.AddressBookTestUtils.TEST_NUMBER_3;
import static haywood.tom.AddressBookTestUtils.createEmptyInMemoryAddressBook;
import static haywood.tom.AddressBookTestUtils.createPopulatedAddressBookList;
import static haywood.tom.AddressBookTestUtils.createPopulatedInMemoryAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for AddressBook.
 */
public class AddressBookTest {
    
    private AddressBook emptyAddressBook;
    private AddressBook populatedAddressBook;
    
    @BeforeEach
    public void setUp() {
        emptyAddressBook = createEmptyInMemoryAddressBook();        
        populatedAddressBook = createPopulatedInMemoryAddressBook();
    }
    
    private void assertEntry(AddressBook addressBook, String name, String expectedPhoneNumber) {
        Contact entry = addressBook.get(name);
        assertNotNull(entry);
        assertEquals(name, entry.getName());
        assertEquals(expectedPhoneNumber, entry.getPhoneNumber());
    }
    
    @Test
    public void testCanCreateEmptyAddressBook() {
        assertEquals(0, emptyAddressBook.getAllEntries().size());
    }
    
    @Test
    public void testCanCreatePopulatedAddressBook() {
        assertEquals(3, populatedAddressBook.getAllEntries().size());
    }
    
    @Test
    public void testGet() {
        assertEntry(populatedAddressBook, TEST_ENTRY_NAME_1, TEST_NUMBER_1);
        assertEntry(populatedAddressBook, TEST_ENTRY_NAME_2, TEST_NUMBER_2);
        assertEntry(populatedAddressBook, TEST_ENTRY_NAME_3, TEST_NUMBER_3);
        assertNull(populatedAddressBook.get(TEST_ENTRY_NAME_4));
    }
    
    @Test
    public void testGetName() {
        assertEquals(EMPTY_ADDRESS_BOOK_NAME, emptyAddressBook.getName());
        assertEquals(POPULATED_ADDRESS_BOOK_NAME, populatedAddressBook.getName());
    }
    
    @Test
    public void testCanAddToAddressBook() {
        Contact newEntry = new Contact(TEST_ENTRY_NAME_1, TEST_NUMBER_1);
        emptyAddressBook.add(newEntry);

        assertEquals(1, emptyAddressBook.getAllEntries().size());
        
        Contact retrievedEntry = emptyAddressBook.get(TEST_ENTRY_NAME_1);
        assertEquals(newEntry, retrievedEntry);
    }
    
    @Test
    public void testCanDeleteFromAddressBook() {
        assertEquals(3, populatedAddressBook.getAllEntries().size());
        populatedAddressBook.delete(TEST_ENTRY_NAME_1);        

        assertEquals(2, populatedAddressBook.getAllEntries().size());
        assertNull(populatedAddressBook.get(TEST_ENTRY_NAME_1));
    }
    
    @Test
    public void testGetAllEntriesFromEmptyBook() {
        assertTrue(emptyAddressBook.getAllEntries().isEmpty());
    }
    
    @Test
    public void testGetAllEntriesFromPopulatedBook() {
        assertTrue(populatedAddressBook.getAllEntries().containsAll(createPopulatedAddressBookList()));
    }
}
