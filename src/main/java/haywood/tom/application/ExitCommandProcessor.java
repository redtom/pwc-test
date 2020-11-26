package haywood.tom.application;

import org.springframework.stereotype.Component;

/**
 * Command processor to exit the application
 */
@Component
public class ExitCommandProcessor extends AbstractCommandProcessor {

    public static final String COMMAND_TEXT = "exit";
    public static final String MENU_TEXT = "Exit the application";

    public ExitCommandProcessor() {
        super(COMMAND_TEXT, MENU_TEXT, false);
    }

    @Override
    protected boolean doProcessCommand(AddressBookRepl application) {
        return false;
    }
}
