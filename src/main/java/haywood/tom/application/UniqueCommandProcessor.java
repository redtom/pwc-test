package haywood.tom.application;

import haywood.tom.model.AddressBook;
import haywood.tom.service.AddressBookAnalyser;
import haywood.tom.service.AddressBookRepository;
import org.springframework.stereotype.Component;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Command processor to compare two address books.
 * Prompts the user for the names of each book and then uses another
 * class to do the comparison.
 */
@Component
public class UniqueCommandProcessor extends AbstractCommandProcessor {
    
    public static final String COMMAND_TEXT = "unique";
    public static final String MENU_TEXT = "Find unique entries";

    public static final String ENTER_NAME_1_PROMPT = "Please enter name of address book 1: ";
    public static final String ENTER_NAME_2_PROMPT = "Please enter name of address book 2: ";
    
    private final AddressBookRepository addressBookRepository;
    private final AddressBookAnalyser addressBookAnalyser;

    public UniqueCommandProcessor(AddressBookRepository addressBookRepository, AddressBookAnalyser addressBookAnalyser) {
        super(COMMAND_TEXT, MENU_TEXT, false);
        this.addressBookRepository = addressBookRepository;
        this.addressBookAnalyser = addressBookAnalyser;
    }
    
    private String getUniqueNames(AddressBook addressBook1, AddressBook addressBook2) {
        List<String> names = addressBookAnalyser.getSymmetricDifference(addressBook1, addressBook2)
            .stream()
            .sorted()
            .map(name -> "\"" + name + "\"")
            .collect(toList());

        return "{ " + String.join(", ", names) + " }";
    }
    
    @Override
    protected boolean doProcessCommand(AddressBookRepl application) {
        try {
            String name1 = application.prompt(ENTER_NAME_1_PROMPT);
            AddressBook addressBook1 = addressBookRepository.get(name1);
            if (addressBook1 == null) {
                application.println(name1 + " does not exist.");
            } else {
                String name2 = application.prompt(ENTER_NAME_2_PROMPT);
                AddressBook addressBook2 = addressBookRepository.get(name2);
                if (addressBook2 == null) {
                    application.println(name2 + " does not exist.");
                } else {
                    application.println("Unique names : " + getUniqueNames(addressBook1, addressBook2));
                }
            }
        } catch (Exception e) {
            application.log("Error comparing address books", e);
        }
        
        return true;
    }

}
