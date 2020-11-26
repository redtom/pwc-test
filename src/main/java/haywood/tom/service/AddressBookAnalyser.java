package haywood.tom.service;

import haywood.tom.model.AddressBook;

import java.util.Collection;

public interface AddressBookAnalyser {

    /**
     * Find the entries unique to either address book.
     * In mathematical terms the symmetric difference.
     * 
     * @param book1 the first book
     * @param book2 the second book
     * @return the names of the entries unique to either address book.
     */
    Collection<String> getSymmetricDifference(AddressBook book1, AddressBook book2);
}
