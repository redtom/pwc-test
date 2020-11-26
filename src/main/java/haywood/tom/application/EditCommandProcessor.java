package haywood.tom.application;

import haywood.tom.model.Contact;
import haywood.tom.service.AddressBookRepository;
import org.springframework.stereotype.Component;

/**
 * Command processor to edit an existing entry.
 * Prompts the user for the name of the entry.
 * If the entry does not exist an error is displayed.
 * Otherwise the new phone number is requested and the
 * entry updated in the repository.
 */
@Component
public class EditCommandProcessor extends AbstractCommandProcessor {
    
    public static final String COMMAND_TEXT = "edit";
    public static final String MENU_TEXT = "Edit existing entry";
    
    public static final String ENTER_NAME_PROMPT = "Please enter name of entry to edit: ";
    public static final String ENTER_PHONE_NUMBER_PROMPT = "Please enter new phone number: ";
    
    private final AddressBookRepository addressBookRepository;

    public EditCommandProcessor(AddressBookRepository addressBookRepository) {
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
                String phoneNumber = application.prompt(ENTER_PHONE_NUMBER_PROMPT);
                contact.setPhoneNumber(phoneNumber);
                addressBookRepository.save(application.getOpenAddressBook());
                application.println(name + " updated.");
            }
        } catch (Exception e) {
            application.log("Error editing " + name, e);
        }
        
        return true;
    }

}
