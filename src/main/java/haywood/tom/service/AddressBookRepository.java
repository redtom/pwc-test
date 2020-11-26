package haywood.tom.service;

import haywood.tom.model.AddressBook;

/**
 * Data access object interface for address books.
 */
public interface AddressBookRepository {

    /**
     * Gets an address book.
     * 
     * @param name the name of the address book to
     * @return the address book or null if it does not exist
     */
    AddressBook get(String name);
    
    /**
     * Saves an address book.
     * 
     * @param addressBook the address book to save
     */
    void save(AddressBook addressBook);
}
