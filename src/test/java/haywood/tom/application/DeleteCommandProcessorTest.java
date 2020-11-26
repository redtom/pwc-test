package haywood.tom.application;

import haywood.tom.model.AddressBook;
import haywood.tom.model.Contact;
import haywood.tom.service.AddressBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for DeleteCommandProcessor.
 */
@ExtendWith(MockitoExtension.class)
public class DeleteCommandProcessorTest {
    
    private static final String PERSON_NAME = "addressBookName";

    @Mock
    private AddressBookRepository addressBookRepository;
    
    @Mock
    private AddressBookRepl application;
    
    @Mock
    private AddressBook addressBook;
    
    @InjectMocks
    private DeleteCommandProcessor deleteCommandProcessor;
    
    @BeforeEach
    public void setUp() {
    }
    
    @Test
    public void testConstruction() {
        assertEquals(DeleteCommandProcessor.COMMAND_TEXT, deleteCommandProcessor.getCommandText());
        assertEquals(DeleteCommandProcessor.MENU_TEXT, deleteCommandProcessor.getMenuText());
        assertTrue(deleteCommandProcessor.needsOpenAddressBook());
    }
    
    @Test
    public void testProcessCommand() {
        when(application.getOpenAddressBook()).thenReturn(addressBook);
        when(application.prompt(DeleteCommandProcessor.ENTER_NAME_PROMPT)).thenReturn(PERSON_NAME);
        when(addressBook.get(PERSON_NAME)).thenReturn(new Contact());
        
        assertTrue(deleteCommandProcessor.processCommand(application));
        
        verify(addressBook).delete(PERSON_NAME);
        verify(addressBookRepository).save(addressBook);
        verify(application).println(PERSON_NAME + " deleted.");
    }
    
    @Test
    public void testProcessCommandBookNotOpen() {
        when(application.getOpenAddressBook()).thenReturn(null);
        
        assertTrue(deleteCommandProcessor.processCommand(application));
        
        verify(application).println(DeleteCommandProcessor.ERROR_NO_OPEN_ADDRESS_BOOK);
    }
    
    @Test
    public void testProcessCommandPersonDoesNotExist() {
        when(application.getOpenAddressBook()).thenReturn(addressBook);
        when(application.prompt(DeleteCommandProcessor.ENTER_NAME_PROMPT)).thenReturn(PERSON_NAME);
        when(addressBook.get(PERSON_NAME)).thenReturn(null);
        
        assertTrue(deleteCommandProcessor.processCommand(application));
        
        verify(application).println(PERSON_NAME + " does not exist.");
    }
    
    @Test
    public void testProcessCommandWithException() {
        RuntimeException exception = new RuntimeException();
        when(application.getOpenAddressBook()).thenReturn(addressBook);
        when(application.prompt(DeleteCommandProcessor.ENTER_NAME_PROMPT)).thenThrow(exception);
        
        assertTrue(deleteCommandProcessor.processCommand(application));
        
        verify(application).log("Error deleting null", exception);
    }
}
