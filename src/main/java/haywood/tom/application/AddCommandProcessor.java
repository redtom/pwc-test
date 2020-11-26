package haywood.tom.application;

import haywood.tom.model.Contact;
import haywood.tom.service.AddressBookRepository;
import org.springframework.stereotype.Component;

/**
 * Command processor to add a new entry to an address book.
 * Prompts the user for the name of the entry.
 * Will not allow the new entry if the name already exists.
 */
@Component
public class AddCommandProcessor extends AbstractCommandProcessor {
    
    public static final String COMMAND_TEXT = "add";
    public static final String MENU_TEXT = "Add new entry";

    public static final String ENTER_NAME_PROMPT = "Please enter the persons name: ";
    public static final String ENTER_PHONE_NUMBER_PROMPT = "Please enter the phone number: ";
    
    private final AddressBookRepository addressBookRepository;

    public AddCommandProcessor(AddressBookRepository addressBookRepository) {
        super(COMMAND_TEXT, MENU_TEXT, true);
        this.addressBookRepository = addressBookRepository;
    }
    
    @Override
    protected boolean doProcessCommand(AddressBookRepl application) {
        String name = null;
        try {
            name = application.prompt(ENTER_NAME_PROMPT);
            Contact contact = application.getOpenAddressBook().get(name);
            if (contact != null) {
                application.println(name + " already exists. Please use edit to modify an existing entry.");
            } else {
                String phoneNumber = application.prompt(ENTER_PHONE_NUMBER_PROMPT);
                application.getOpenAddressBook().add(new Contact(name, phoneNumber));
                addressBookRepository.save(application.getOpenAddressBook());
                application.println(name + " added.");
            }
        } catch (Exception e) {
            application.log("Error adding " + name, e);
        }
        
        return true;
    }

}
