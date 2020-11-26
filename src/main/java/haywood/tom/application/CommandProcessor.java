package haywood.tom.application;

/**
 * Interface for processing commands from user input.
 */
public interface CommandProcessor {
    
    /**
     * Return the input text for this command.
     * 
     * @return text the user should input to execute this command.
     */
    String getCommandText();
    
    /**
     * Return the menu text for this command.
     * 
     * @return text that will be displayed in the menu this command.
     */
    String getMenuText();

    /**
     * Does this command need an open address book to execute.
     * 
     * @return true or false
     */
    boolean needsOpenAddressBook();
    
    /**
     * Process the command.
     * 
     * @return true if the application should continue, false if it should exit.
     */
    boolean processCommand(AddressBookRepl application);
}
