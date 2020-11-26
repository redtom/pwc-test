package haywood.tom.application;

import com.google.common.collect.Lists;
import haywood.tom.model.AddressBook;
import org.apache.commons.io.input.ReaderInputStream;
import org.apache.commons.io.output.WriterOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for AddressBookApp.
 */
@ExtendWith(MockitoExtension.class)
public class AddressBookReplTest {
    
    @Mock
    private CommandProcessor command1;
    
    @Mock
    private CommandProcessor command2;
    
    @Mock
    private AddressBook addressBook;
    
    private InputStream getInputStream(String streamContents) {
        return new ReaderInputStream(new StringReader(streamContents), Charset.defaultCharset());
    }
    
    private PrintStream getPrintStream(StringWriter stringWriter) {
        return new PrintStream(new WriterOutputStream(stringWriter, Charset.defaultCharset()));
    }
    
    /**
     * Creates the application with mock objects.
     * 
     * @param commands the available commands
     * @param streamContents the users input
     * @param stringWriter where output should go.
     */
    private AddressBookRepl createApp(Collection<CommandProcessor> commands, String streamContents, StringWriter stringWriter) {
        return new AddressBookRepl(commands, getInputStream(streamContents), getPrintStream(stringWriter));
    }
    
    private void setupCommandMock(CommandProcessor command, String commandText, String menuText, boolean needsOpenBook) {
        when(command.getCommandText()).thenReturn(commandText);
        lenient().when(command.getMenuText()).thenReturn(menuText);
        when(command.needsOpenAddressBook()).thenReturn(needsOpenBook);
    }
    
    @BeforeEach
    public void setUp() {
        setupCommandMock(command1, "a", "menu1", false);
        setupCommandMock(command2, "b", "menu2", true);
    }
    
    @Test
    public void testGetTopLevelMenuNoOpenBook() {
        AddressBookRepl application = createApp(Lists.newArrayList(command2, command1), "", new StringWriter());
        assertEquals("Please choose command, from following list:\n" +
                     "  a : menu1\n" +
                     "command : ", application.getTopLevelMenu());
    }
    
    @Test
    public void testGetTopLevelMenuWithOpenBookSortsCommandsAlphabetically() {
        AddressBookRepl application = createApp(Lists.newArrayList(command2, command1), "", new StringWriter());
        application.setOpenAddressBook(addressBook);
        assertEquals("Please choose command, from following list:\n" +
                     "  a : menu1\n" +
                     "  b : menu2\n" +
                     "command : ", application.getTopLevelMenu());
    }
    
    @Test
    public void testRunWithOneCommand() {
        StringWriter stringWriter = new StringWriter();
        AddressBookRepl application = createApp(Lists.newArrayList(command2, command1), "a\n", stringWriter);
        application.setOpenAddressBook(addressBook);
        
        when(command1.processCommand(application)).thenReturn(false);
        
        application.run();
        
        verify(command1).processCommand(application);
    }
    
    @Test
    public void testRunWithTwoCommands() {
        StringWriter stringWriter = new StringWriter();
        AddressBookRepl application = createApp(Lists.newArrayList(command2, command1), "a\nb\n", stringWriter);
        application.setOpenAddressBook(addressBook);
        
        when(command1.processCommand(application)).thenReturn(true);
        when(command2.processCommand(application)).thenReturn(false);
        
        application.run();
        
        verify(command1).processCommand(application);
        verify(command2).processCommand(application);
    }
    
    @Test
    public void testRunWithTwoCommandsFirstOneExits() {
        StringWriter stringWriter = new StringWriter();
        AddressBookRepl application = createApp(Lists.newArrayList(command2, command1), "a\nb\n", stringWriter);
        application.setOpenAddressBook(addressBook);
        
        when(command1.processCommand(application)).thenReturn(false);
        
        application.run();
        
        verify(command1).processCommand(application);
        verify(command2, never()).processCommand(application);
    }
    
    @Test
    public void testRunWithInvalidCommand() {
        StringWriter stringWriter = new StringWriter();
        AddressBookRepl application = createApp(Lists.newArrayList(command2, command1), "c\n", stringWriter);
        application.setOpenAddressBook(addressBook);
        
        application.run();
        
        assertEquals("Please choose command, from following list:\n" +
            "  a : menu1\n" +
            "  b : menu2\n" +
            "command : Invalid command, please try again.\r\n", stringWriter.toString());
        verify(command1, never()).processCommand(application);
        verify(command2, never()).processCommand(application);
    }
}
