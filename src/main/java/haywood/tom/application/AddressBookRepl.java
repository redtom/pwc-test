package haywood.tom.application;

import com.google.common.collect.Lists;
import haywood.tom.model.AddressBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

/**
 * Address book REPL class
 * 
 * A collection of CommandProcessor objects are autowired on construction.
 * These processors are used to generate a menu that is repeatedly shown to the user.
 * The input is then used to lookup which processor should process the command.
 * Control then passes to the processor. This continues until a processor returns
 * false, indicating the application should be exited.
 */
@Component
public class AddressBookRepl {

    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    public static final String END_OF_LINE = "\n";
    
    private final Scanner inputScanner;
    private final PrintStream outputStream;
    private final List<CommandProcessor> commandProcessors;
    private final Map<String, CommandProcessor> commandProcessorMap;

    private AddressBook openAddressBook;

    public AddressBookRepl(Collection<CommandProcessor> commandProcessorCollection, InputStream inputStream, PrintStream outputStream) {
        this.inputScanner = new Scanner(inputStream);
        this.outputStream = outputStream;
        this.openAddressBook = null;
        this.commandProcessors = Lists.newArrayList(commandProcessorCollection);
        
        // Sort the commands, so they appear in alphabetical order in the menu
        commandProcessors.sort(Comparator.comparing(CommandProcessor::getCommandText));
        this.commandProcessorMap = commandProcessors.stream().collect(toMap(CommandProcessor::getCommandText, Function.identity()));
    }
    
    public String getTopLevelMenu() {
        StringBuilder builder = new StringBuilder();
        
        builder.append("Please choose command, from following list:").append(END_OF_LINE);
        for (CommandProcessor commandProcessor : commandProcessors) {
            // Only display commands that are available in this current context
            if (!commandProcessor.needsOpenAddressBook() || (openAddressBook != null)) {
                builder
                    .append("  ")
                    .append(commandProcessor.getCommandText())
                    .append(" : ")
                    .append(commandProcessor.getMenuText())
                    .append(END_OF_LINE);
            }
        }
        builder.append("command : ");
        
        return builder.toString();
    }

    public void run() {
        boolean continueExecution = false;
        do {
            // Display the menu and get the input command
            String command = prompt(getTopLevelMenu());
            
            // Find the processor for this command
            CommandProcessor commandProcessor = commandProcessorMap.get(command);
            if (commandProcessor == null) {
                println("Invalid command, please try again.");
            } else {
                // Execute the command, and exit if the commands tells us to.
                continueExecution = commandProcessor.processCommand(this);
            }
        } while (continueExecution);
        
        outputStream.close();
        inputScanner.close();
    }
    
    public AddressBook getOpenAddressBook() {
        return openAddressBook;
    }

    public void setOpenAddressBook(AddressBook openAddressBook) {
        this.openAddressBook = openAddressBook;
    }
    
    public String prompt(String text) {
        outputStream.print(text);
        return inputScanner.nextLine();
    }
    
    public void println(String text) {
        outputStream.println(text);
    }
    
    public void log(String text, Exception e) {
        logger.error(text, e);
    }
}
