package haywood.tom.model;

import lombok.Data;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

/**
 * This implementation of IAddresBook stores all entries in a map.
 * A simple Map<String, String> would satisfy the current requirements.
 * But it is very reasonable to expect something else added in the
 * future. e.g. add an email address as well.
 */
@Data
public class AddressBook {

    private final String name;
    private final Map<String, Contact> contactMap;
    
    public AddressBook(String name, Collection<Contact> entries) {
        this.name = name;
        this.contactMap = entries.stream().collect(toMap(Contact::getName, Function.identity()));
    }
    
    /**
     * Retrieve a specific entry.
     * 
     * @param name the name of the person whose entry is being retrieved.
     * @return the entry for that person or null if it does not exist.
     */
    public Contact get(String name) {
        return contactMap.get(name);
    }

    /**
     * Adds a new entry to the address book. Does not detect duplicates.
     * 
     * @param newEntry the new entry.
     */
    public void add(Contact newEntry) {
        contactMap.put(newEntry.getName(), newEntry);
    }

    /**
     * Deletes an entry from the address book.
     * 
     * @param name the name of the entry to delete.
     */
    public void delete(String name) {
        contactMap.remove(name);
    }

    public Collection<Contact> getAllEntries() {
        return Collections.unmodifiableCollection(contactMap.values());
    }
}
