package haywood.tom.application;

/**
 * Abstract implementation of an CommandProcessor.
 * Does not allow execution if the command needs an open address book and there is not one open.
 */
public abstract class AbstractCommandProcessor implements CommandProcessor {

    public static String ERROR_NO_OPEN_ADDRESS_BOOK = "This command should not be executed without an open address book.";
    
    private final String commandText;
    private final String menuText;
    private final boolean needsOpenAddressBook;
    
    public AbstractCommandProcessor(String commandText, String menuText, boolean needsOpenAddressBook) {
        this.commandText = commandText;
        this.menuText = menuText;
        this.needsOpenAddressBook = needsOpenAddressBook;
    }

    protected abstract boolean doProcessCommand(AddressBookRepl application);
    
    public boolean processCommand(AddressBookRepl application) {
        if (needsOpenAddressBook && (application.getOpenAddressBook() == null)) {
            application.println(ERROR_NO_OPEN_ADDRESS_BOOK);
            return true;
        } else {
            return doProcessCommand(application);
        }
    }
    
    @Override
    public String getCommandText() {
        return commandText;
    }

    @Override
    public String getMenuText() {
        return menuText;
    }

    @Override
    public boolean needsOpenAddressBook() {
        return needsOpenAddressBook;
    }
}
