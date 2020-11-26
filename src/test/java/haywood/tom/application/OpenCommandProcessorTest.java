package haywood.tom.application;

import haywood.tom.model.AddressBook;
import haywood.tom.service.AddressBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for OpenCommandProcessor.
 */
@ExtendWith(MockitoExtension.class)
public class OpenCommandProcessorTest {
    
    private static final String ADDRESS_BOOK_NAME = "addressBookName";

    @Mock
    private AddressBookRepository addressBookRepository;
    
    @Mock
    private AddressBookRepl application;
    
    @Mock
    private AddressBook addressBook;
    
    @InjectMocks
    private OpenCommandProcessor openCommandProcessor;
    
    @BeforeEach
    public void setUp() {
    }
    
    @Test
    public void testConstruction() {
        assertEquals(OpenCommandProcessor.COMMAND_TEXT, openCommandProcessor.getCommandText());
        assertEquals(OpenCommandProcessor.MENU_TEXT, openCommandProcessor.getMenuText());
        assertFalse(openCommandProcessor.needsOpenAddressBook());
    }
    
    @Test
    public void testProcessCommandBookAlreadyExists() {
        when(application.prompt(OpenCommandProcessor.ENTER_NAME_PROMPT)).thenReturn(ADDRESS_BOOK_NAME);
        when(addressBookRepository.get(ADDRESS_BOOK_NAME)).thenReturn(addressBook);
        
        assertTrue(openCommandProcessor.processCommand(application));
        
        verify(application).setOpenAddressBook(addressBook);
        verify(application).println("Address book " + ADDRESS_BOOK_NAME + " has been opened.");
    }
    
    @Test
    public void testProcessCommandBookDoesNotExist() {
        when(application.prompt(OpenCommandProcessor.ENTER_NAME_PROMPT)).thenReturn(ADDRESS_BOOK_NAME);
        when(addressBookRepository.get(ADDRESS_BOOK_NAME)).thenReturn(null);
        
        assertTrue(openCommandProcessor.processCommand(application));
        
        ArgumentCaptor<AddressBook> addressBookCaptorForSave = ArgumentCaptor.forClass(AddressBook.class);
        ArgumentCaptor<AddressBook> addressBookCaptorForSetOpen = ArgumentCaptor.forClass(AddressBook.class);
        
        verify(application).println(ADDRESS_BOOK_NAME + " does not exist, creating new.");
        
        verify(addressBookRepository).save(addressBookCaptorForSave.capture());
        assertEquals(ADDRESS_BOOK_NAME, addressBookCaptorForSave.getValue().getName());
        
        verify(application).setOpenAddressBook(addressBookCaptorForSetOpen.capture());
        assertEquals(ADDRESS_BOOK_NAME, addressBookCaptorForSetOpen.getValue().getName());
    }
    
    @Test
    public void testProcessCommandWithException() {
        RuntimeException exception = new RuntimeException();
        when(application.prompt(OpenCommandProcessor.ENTER_NAME_PROMPT)).thenThrow(exception);
        
        assertTrue(openCommandProcessor.processCommand(application));
        
        verify(application).log("Error opening null", exception);
    }
}
