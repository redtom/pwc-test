package haywood.tom.service;

import haywood.tom.AddressBookTestUtils;
import haywood.tom.model.AddressBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for AddressBookAnalyserImpl.
 * Not exhaustive as it is based on another library.
 */
public class AddressBookAnalyserImplTest {

    private AddressBookAnalyserImpl addressBookAnalyser;
    
    @BeforeEach
    public void setUp() {
        addressBookAnalyser = new AddressBookAnalyserImpl();
    }
    
    @Test
    public void testSymmetricDifference() {
        AddressBook book1 = new AddressBook("book1", AddressBookTestUtils.createEntryListWith1_2_and_3());
        AddressBook book2 = new AddressBook("book2", AddressBookTestUtils.createEntryListWith2_3_and_4());
        
        Collection<String> symmetricDifference = addressBookAnalyser.getSymmetricDifference(book1, book2);
        
        assertTrue(symmetricDifference.contains(AddressBookTestUtils.TEST_ENTRY_NAME_1));
        assertFalse(symmetricDifference.contains(AddressBookTestUtils.TEST_ENTRY_NAME_2));
        assertFalse(symmetricDifference.contains(AddressBookTestUtils.TEST_ENTRY_NAME_3));
        assertTrue(symmetricDifference.contains(AddressBookTestUtils.TEST_ENTRY_NAME_4));
    }
    
    @Test
    public void testSymmetricDifferenceSameBook() {
        AddressBook book = new AddressBook("book1", AddressBookTestUtils.createEntryListWith1_2_and_3());
        
        Collection<String> symmetricDifference = addressBookAnalyser.getSymmetricDifference(book, book);
        
        assertTrue(symmetricDifference.isEmpty());
    }
}
