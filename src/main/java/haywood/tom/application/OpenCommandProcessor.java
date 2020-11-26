package haywood.tom.application;

import haywood.tom.model.AddressBook;
import haywood.tom.service.AddressBookRepository;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

/**
 * Command processor to open an address book.
 * Prompts the user for the name, opens or creates the book and then sets
 * the current open address book on the application object.
 */
@Component
public class OpenCommandProcessor extends AbstractCommandProcessor {
    
    public static final String COMMAND_TEXT = "open";
    public static final String MENU_TEXT = "Open an address book";
    public static final String ENTER_NAME_PROMPT = "Please enter name of address book: ";
    
    private final AddressBookRepository addressBookRepository;

    public OpenCommandProcessor(AddressBookRepository addressBookRepository) {
        super(COMMAND_TEXT, MENU_TEXT, false);
        this.addressBookRepository = addressBookRepository;
    }
    
    @Override
    protected boolean doProcessCommand(AddressBookRepl application) {
        String name = null;
        try {
            name = application.prompt(ENTER_NAME_PROMPT);
            AddressBook addressBook = addressBookRepository.get(name);
            if (addressBook == null) {
                application.println(name + " does not exist, creating new.");
                addressBook = new AddressBook(name, new ArrayList<>());
                addressBookRepository.save(addressBook);
            } else {
                application.println("Address book " + name + " has been opened.");
            }
            application.setOpenAddressBook(addressBook);
        } catch (Exception e) {
            application.log("Error opening " + name , e);
        }
        
        return true;
    }

}
