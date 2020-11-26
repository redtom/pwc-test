package haywood.tom.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Test class for ExitCommandProcessor.
 */
@ExtendWith(MockitoExtension.class)
public class ExitCommandProcessorTest {
        
    @Mock
    private AddressBookRepl application;
    
    @InjectMocks
    private ExitCommandProcessor exitCommandProcessor;
    
    @BeforeEach
    public void setUp() {
    }
    
    @Test
    public void testConstruction() {
        assertEquals(ExitCommandProcessor.COMMAND_TEXT, exitCommandProcessor.getCommandText());
        assertEquals(ExitCommandProcessor.MENU_TEXT, exitCommandProcessor.getMenuText());
        assertFalse(exitCommandProcessor.needsOpenAddressBook());
    }
    
    @Test
    public void testProcessCommand() {
        assertFalse(exitCommandProcessor.processCommand(application));
    }
}
