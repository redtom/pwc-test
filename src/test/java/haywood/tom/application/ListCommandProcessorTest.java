package haywood.tom.application;

import com.google.common.collect.Lists;
import haywood.tom.model.AddressBook;
import haywood.tom.model.Contact;
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
 * Test class for ListCommandProcessor.
 */
@ExtendWith(MockitoExtension.class)
public class ListCommandProcessorTest {
        
    @Mock
    private AddressBookRepl application;
    
    @Mock
    private AddressBook addressBook;
    
    @InjectMocks
    private ListCommandProcessor listCommandProcessor;
    
    @BeforeEach
    public void setUp() {
    }
    
    @Test
    public void testConstruction() {
        assertEquals(ListCommandProcessor.COMMAND_TEXT, listCommandProcessor.getCommandText());
        assertEquals(ListCommandProcessor.MENU_TEXT, listCommandProcessor.getMenuText());
        assertTrue(listCommandProcessor.needsOpenAddressBook());
    }
    
    @Test
    public void testProcessCommand() {
        when(application.getOpenAddressBook()).thenReturn(addressBook);
        when(addressBook.getAllEntries()).thenReturn(
            Lists.newArrayList(
                new Contact("Bob", "1234567"),
                new Contact("Alex", "987654")));
        
        assertTrue(listCommandProcessor.processCommand(application));
        
        verify(application).println("Alex : 987654\nBob : 1234567\n");
    }
    
    @Test
    public void testProcessCommandBookNotOpen() {
        when(application.getOpenAddressBook()).thenReturn(null);
        
        assertTrue(listCommandProcessor.processCommand(application));
        
        verify(application).println(ListCommandProcessor.ERROR_NO_OPEN_ADDRESS_BOOK);
    }
}
