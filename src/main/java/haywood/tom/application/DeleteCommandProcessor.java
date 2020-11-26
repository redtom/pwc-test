package haywood.tom.application;

import haywood.tom.model.Contact;
import haywood.tom.service.AddressBookRepository;
import org.springframework.stereotype.Component;

/**
 * Command processor to delete an entry from the address book.
 * Prompts the user for the name of the entry.
 * It will display an error if the entry does not exist.
 */
@Component
public class DeleteCommandProcessor extends AbstractCommandProcessor {
    
    public static final String COMMAND_TEXT = "delete";
    public static final String MENU_TEXT = "Delete an entry";

    public static final String ENTER_NAME_PROMPT = "Please enter name of entry to delete: ";
    
    private final AddressBookRepository addressBookRepository;

    public DeleteCommandProcessor(AddressBookRepository addressBookRepository) {
        super(COMMAND_TEXT, MENU_TEXT, true);
        this.addressBookRepository = addressBookRepository;
    }
    
    @Override
    protected boolean doProcessCommand(AddressBookRepl application) {
        String name = null;
        try {
            name = application.prompt(ENTER_NAME_PROMPT);
            Contact contact = application.getOpenAddressBook().get(name);
            if (contact == null) {
                application.println(name + " does not exist.");
            } else {
                application.getOpenAddressBook().delete(name);
                addressBookRepository.save(application.getOpenAddressBook());
                application.println(name + " deleted.");
            }
        } catch (Exception e) {
            application.log("Error deleting " + name, e);
        }
        
        return true;
    }

}
