package haywood.tom.service;

import com.google.common.collect.Sets;
import haywood.tom.model.AddressBook;
import haywood.tom.model.Contact;
import org.springframework.stereotype.Component;
import java.util.Collection;

import static java.util.stream.Collectors.toList;

/**
 * Simple implementation of AddressBookAnalyser that makes uses of google quava.
 */
@Component
public class AddressBookAnalyserImpl implements AddressBookAnalyser {

    @Override
    public Collection<String> getSymmetricDifference(AddressBook book1, AddressBook book2) {        
        return Sets.symmetricDifference(
            Sets.newHashSet(book1.getAllEntries()),
            Sets.newHashSet(book2.getAllEntries())
        )
            .stream()
            .map(Contact::getName)
            .collect(toList());
    }

}
