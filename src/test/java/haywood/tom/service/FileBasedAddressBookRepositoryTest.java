package haywood.tom.service;

import haywood.tom.model.AddressBook;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.charset.Charset;

import static haywood.tom.AddressBookTestUtils.createEmptyInMemoryAddressBook;
import static haywood.tom.AddressBookTestUtils.createPopulatedAddressBookList;
import static haywood.tom.AddressBookTestUtils.createPopulatedInMemoryAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for FileBasedAddressBookRepository
 */
public class FileBasedAddressBookRepositoryTest {

    private static final String EMPTY_ADDRESS_BOOK_JSON_TEXT = "[]";
    private static final String POPULATED_ADDRESS_BOOK_JSON_TEXT = 
        "[{" +
    		"\"name\":\"Bob\"," +
    		"\"phoneNumber\":\"98765432\"" +
		"},{" +
    		"\"name\":\"Rod\"," +
    		"\"phoneNumber\":\"0401234567\"" +
		"},{" +
    		"\"name\":\"Todd\"," +
    		"\"phoneNumber\":\"23456789\"" +
		"}]";
    
    private FileBasedAddressBookRepository addressBookRepository;
    
    @BeforeEach
    public void setUp() {
        addressBookRepository = new FileBasedAddressBookRepository();
    }
    
    private void saveAndAssertAddressBook(AddressBook addressBook, String expectedJsonText) throws Exception {        
        addressBookRepository.save(addressBook);
        
        String fileContents = FileUtils.readFileToString(addressFile(addressBook.getName()), Charset.defaultCharset());
        assertEquals(expectedJsonText, fileContents);
    }
    
    @Test
    public void testSaveEmptyAddressBook() throws Exception {
        saveAndAssertAddressBook(createEmptyInMemoryAddressBook(), EMPTY_ADDRESS_BOOK_JSON_TEXT);
    }
    
    @Test
    public void testSavePopulatedAddressBook() throws Exception {
        saveAndAssertAddressBook(createPopulatedInMemoryAddressBook(), POPULATED_ADDRESS_BOOK_JSON_TEXT);
    }
    
    @Test
    public void testGetEmptyAddressBook() throws Exception {
        String name = "testEmptyAddressBook";
        FileUtils.writeStringToFile(addressFile(name), EMPTY_ADDRESS_BOOK_JSON_TEXT, Charset.defaultCharset());
        
        AddressBook addressBook = addressBookRepository.get(name);
        assertNotNull(addressBook);
        assertEquals(name, addressBook.getName());
        assertTrue(addressBook.getAllEntries().isEmpty());
    }
    
    @Test
    public void testGetPopulatedAddressBook() throws Exception {
        String name = "testPopulatedAddressBook";
        FileUtils.writeStringToFile(addressFile(name), POPULATED_ADDRESS_BOOK_JSON_TEXT, Charset.defaultCharset());
        
        AddressBook addressBook = addressBookRepository.get(name);
        assertNotNull(addressBook);
        assertEquals(name, addressBook.getName());
        assertTrue(addressBook.getAllEntries().containsAll(createPopulatedAddressBookList()));
    }
    
    @Test
    public void testGetNonExistentAddressBook() {
        AddressBook addressBook = addressBookRepository.get("non-existent");
        assertNull(addressBook);
    }

    public File addressFile(String name) {
        File addressFile = FileBasedAddressBookRepository.addressFile(name);
        addressFile.deleteOnExit();

        return addressFile;
    }
}
