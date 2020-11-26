package haywood.tom.application;

import com.google.common.collect.Lists;
import haywood.tom.model.AddressBook;
import haywood.tom.service.AddressBookAnalyser;
import haywood.tom.service.AddressBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for UniqueCommandProcessor.
 */
@ExtendWith(MockitoExtension.class)
public class UniqueCommandProcessorTest {
    
    private static final String BOOK_1 = "book1";
    private static final String BOOK_2 = "book2";

    @Mock
    private AddressBookRepository addressBookRepository;
    
    @Mock
    private AddressBookRepl application;
    
    @Mock
    private AddressBookAnalyser addressBookAnalyser;
    
    @Mock
    private AddressBook addressBook1;
    
    @Mock
    private AddressBook addressBook2;
    
    @InjectMocks
    private UniqueCommandProcessor uniqueCommandProcessor;
    
    @BeforeEach
    public void setUp() {
    }
    
    @Test
    public void testConstruction() {
        assertEquals(UniqueCommandProcessor.COMMAND_TEXT, uniqueCommandProcessor.getCommandText());
        assertEquals(UniqueCommandProcessor.MENU_TEXT, uniqueCommandProcessor.getMenuText());
        assertFalse(uniqueCommandProcessor.needsOpenAddressBook());
    }
    
    private void setupPromptAndDAOExpectation(String prompt, String name, AddressBook book) {
        when(application.prompt(prompt)).thenReturn(name);
        when(addressBookRepository.get(name)).thenReturn(book);
    }
    
    @Test
    public void testProcessCommandTwoUniqueNames() {
        setupPromptAndDAOExpectation(UniqueCommandProcessor.ENTER_NAME_1_PROMPT, BOOK_1, addressBook1);
        setupPromptAndDAOExpectation(UniqueCommandProcessor.ENTER_NAME_2_PROMPT, BOOK_2, addressBook2);
        
        when(addressBookAnalyser.getSymmetricDifference(addressBook1, addressBook2)).thenReturn(Lists.newArrayList("b", "a"));
        
        assertTrue(uniqueCommandProcessor.processCommand(application));
        
        verify(application).println("Unique names : { \"a\", \"b\" }");
    }
    
    @Test
    public void testProcessCommandOneUniqueNames() {
        setupPromptAndDAOExpectation(UniqueCommandProcessor.ENTER_NAME_1_PROMPT, BOOK_1, addressBook1);
        setupPromptAndDAOExpectation(UniqueCommandProcessor.ENTER_NAME_2_PROMPT, BOOK_2, addressBook2);
        
        when(addressBookAnalyser.getSymmetricDifference(addressBook1, addressBook2)).thenReturn(Lists.newArrayList("b"));
        
        assertTrue(uniqueCommandProcessor.processCommand(application));
        
        verify(application).println("Unique names : { \"b\" }");
    }
    
    @Test
    public void testProcessCommandBook1DoesNotExist() {
        setupPromptAndDAOExpectation(UniqueCommandProcessor.ENTER_NAME_1_PROMPT, BOOK_1, null);
        
        assertTrue(uniqueCommandProcessor.processCommand(application));
        
        verify(application).println(BOOK_1 + " does not exist.");
    }
    
    @Test
    public void testProcessCommandBook2DoesNotExist() {
        setupPromptAndDAOExpectation(UniqueCommandProcessor.ENTER_NAME_1_PROMPT, BOOK_1, addressBook1);
        setupPromptAndDAOExpectation(UniqueCommandProcessor.ENTER_NAME_2_PROMPT, BOOK_2, null);
        
        assertTrue(uniqueCommandProcessor.processCommand(application));
        
        verify(application).println(BOOK_2 + " does not exist.");
    }
    
    @Test
    public void testProcessCommandWithException() {
        RuntimeException exception = new RuntimeException();
        when(application.prompt(UniqueCommandProcessor.ENTER_NAME_1_PROMPT)).thenThrow(exception);
        
        assertTrue(uniqueCommandProcessor.processCommand(application));
        
        verify(application).log("Error comparing address books", exception);
    }
}
