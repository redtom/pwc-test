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
 * Test class for EditCommandProcessor.
 */
@ExtendWith(MockitoExtension.class)
public class EditCommandProcessorTest {
    
    private static final String PERSON_NAME = "addressBookName";
    private static final String PHONE_NUMBER = "123456789";

    @Mock
    private AddressBookRepository addressBookRepository;
    
    @Mock
    private AddressBookRepl application;
    
    @Mock
    private AddressBook addressBook;
    
    @Mock
    private Contact contact;
    
    @InjectMocks
    private EditCommandProcessor editCommandProcessor;
    
    @BeforeEach
    public void setUp() {
    }
    
    @Test
    public void testConstruction() {
        assertEquals(EditCommandProcessor.COMMAND_TEXT, editCommandProcessor.getCommandText());
        assertEquals(EditCommandProcessor.MENU_TEXT, editCommandProcessor.getMenuText());
        assertTrue(editCommandProcessor.needsOpenAddressBook());
    }
    
    @Test
    public void testProcessCommand() {
        when(application.getOpenAddressBook()).thenReturn(addressBook);
        when(application.prompt(EditCommandProcessor.ENTER_NAME_PROMPT)).thenReturn(PERSON_NAME);
        when(addressBook.get(PERSON_NAME)).thenReturn(contact);
        when(application.prompt(EditCommandProcessor.ENTER_PHONE_NUMBER_PROMPT)).thenReturn(PHONE_NUMBER);
        
        assertTrue(editCommandProcessor.processCommand(application));
        
        verify(contact).setPhoneNumber(PHONE_NUMBER);
        verify(addressBookRepository).save(addressBook);
        verify(application).println(PERSON_NAME + " updated.");
    }
    
    @Test
    public void testProcessCommandBookNotOpen() {
        when(application.getOpenAddressBook()).thenReturn(null);
        
        assertTrue(editCommandProcessor.processCommand(application));
        
        verify(application).println(EditCommandProcessor.ERROR_NO_OPEN_ADDRESS_BOOK);
    }
    
    @Test
    public void testProcessCommandPersonDoesNotExist() {
        when(application.getOpenAddressBook()).thenReturn(addressBook);
        when(application.prompt(EditCommandProcessor.ENTER_NAME_PROMPT)).thenReturn(PERSON_NAME);
        when(addressBook.get(PERSON_NAME)).thenReturn(null);
        
        assertTrue(editCommandProcessor.processCommand(application));
        
        verify(application).println(PERSON_NAME + " does not exist.");
    }
    
    @Test
    public void testProcessCommandWithException() {
        RuntimeException exception = new RuntimeException();
        when(application.getOpenAddressBook()).thenReturn(addressBook);
        when(application.prompt(EditCommandProcessor.ENTER_NAME_PROMPT)).thenThrow(exception);
        
        assertTrue(editCommandProcessor.processCommand(application));
        
        verify(application).log("Error editing null", exception);
    }
}
