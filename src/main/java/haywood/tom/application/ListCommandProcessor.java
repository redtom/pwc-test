package haywood.tom.application;

import com.google.common.collect.Lists;
import haywood.tom.model.Contact;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;

/**
 * Command processor to list the contents of the open address book.
 */
@Component
public class ListCommandProcessor extends AbstractCommandProcessor {

    public static final String COMMAND_TEXT = "list";
    public static final String MENU_TEXT = "List all entries";
    
    public ListCommandProcessor() {
        super(COMMAND_TEXT, MENU_TEXT, true);
    }
    
    @Override
    public boolean doProcessCommand(AddressBookRepl application) {
        List<Contact> entries = Lists.newArrayList(application.getOpenAddressBook().getAllEntries());
        Collections.sort(entries);
        
        StringBuilder builder = new StringBuilder();
        for (Contact entry : entries) {
            builder.append(entry.getName()).append(" : ").append(entry.getPhoneNumber()).append(AddressBookRepl.END_OF_LINE);
        }
        
        application.println(builder.toString());

        return true;
    }

}
