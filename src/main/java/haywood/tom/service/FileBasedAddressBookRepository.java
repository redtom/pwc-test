package haywood.tom.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import haywood.tom.model.AddressBook;
import haywood.tom.model.Contact;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.List;

/**
 * This class stores an entire address book in a file in JSON format.
 * The open source Jackson library is used for this purpose.
 */
@Component
public class FileBasedAddressBookRepository implements AddressBookRepository {

    public static final String FILE_SUFFIX = ".json";
    
    private final ObjectMapper objectMapper;
    
    public FileBasedAddressBookRepository() {
        objectMapper = new ObjectMapper();
    }
    
    @Override
    public AddressBook get(String name) {
        try {
            File file = addressFile(name);
            if (file.exists()) {
                List<Contact> addressBookEntries = objectMapper.readValue(file, new TypeReference<List<Contact>>(){});
                return new AddressBook(name, addressBookEntries);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading address book", e);
        }
    }

    @Override
    public void save(AddressBook addressBook) {
        try {
            File file = addressFile(addressBook.getName());
            objectMapper.writeValue(file, addressBook.getAllEntries());
        } catch (Exception e) {
            throw new RuntimeException("Error saving address book", e);
        }
    }

    public static File addressFile(String name) {
        return new File(name + FILE_SUFFIX);
    }
}
