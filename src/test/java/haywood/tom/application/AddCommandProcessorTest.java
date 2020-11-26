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
 * Test class for AddCommandProcessor.
 */
@ExtendWith(MockitoExtension.class)
public class AddCommandProcessorTest {
    
    private static final String PERSON_NAME = "addressBookName";
    private static final String PHONE_NUMBER = "123456789";

    @Mock
    private AddressBookRepository addressBookRepository;
    
    @Mock
    private AddressBookRepl application;
    
    @Mock
    private AddressBook addressBook;
    
    @InjectMocks
    private AddCommandProcessor addCommandProcessor;
    
    @BeforeEach
    public void setUp() {
    }
    
    @Test
    public void testConstruction() {
        assertEquals(AddCommandProcessor.COMMAND_TEXT, addCommandProcessor.getCommandText());
        assertEquals(AddCommandProcessor.MENU_TEXT, addCommandProcessor.getMenuText());
        assertTrue(addCommandProcessor.needsOpenAddressBook());
    }
    
    @Test
    public void testProcessCommand() {
        when(application.getOpenAddressBook()).thenReturn(addressBook);
        when(application.prompt(AddCommandProcessor.ENTER_NAME_PROMPT)).thenReturn(PERSON_NAME);
        when(addressBook.get(PERSON_NAME)).thenReturn(null);
        when(application.prompt(AddCommandProcessor.ENTER_PHONE_NUMBER_PROMPT)).thenReturn(PHONE_NUMBER);
        
        assertTrue(addCommandProcessor.processCommand(application));
        
        verify(addressBook).add(new Contact(PERSON_NAME, PHONE_NUMBER));
        verify(addressBookRepository).save(addressBook);
        verify(application).println(PERSON_NAME + " added.");
    }
    
    @Test
    public void testProcessCommandBookNotOpen() {
        when(application.getOpenAddressBook()).thenReturn(null);
        
        assertTrue(addCommandProcessor.processCommand(application));
        
        verify(application).println(AddCommandProcessor.ERROR_NO_OPEN_ADDRESS_BOOK);
    }
    
    @Test
    public void testProcessCommandPersonAlreadyExists() {
        when(application.getOpenAddressBook()).thenReturn(addressBook);
        when(application.prompt(AddCommandProcessor.ENTER_NAME_PROMPT)).thenReturn(PERSON_NAME);
        when(addressBook.get(PERSON_NAME)).thenReturn(new Contact());
        
        assertTrue(addCommandProcessor.processCommand(application));
        
        verify(application).println(PERSON_NAME + " already exists. Please use edit to modify an existing entry.");
    }
    
    @Test
    public void testProcessCommandWithException() {
        RuntimeException exception = new RuntimeException();
        when(application.getOpenAddressBook()).thenReturn(addressBook);
        when(application.prompt(AddCommandProcessor.ENTER_NAME_PROMPT)).thenThrow(exception);
        
        assertTrue(addCommandProcessor.processCommand(application));
        
        verify(application).log("Error adding null", exception);
    }
}
