package haywood.tom.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tess class for the Contact POJO.
 * No setter/getter test, just compareTo and equals tests.
 */
public class ContactTest {

    private final Contact a1Entry = new Contact("a", "1");
    private final Contact a2Entry = new Contact("a", "2");
    private final Contact b1Entry = new Contact("b", "1");
    private final Contact b2Entry = new Contact("b", "2");
    
    @Test
    public void testCompareTo() {
        assertTrue(a1Entry.compareTo(a1Entry) == 0);
        assertTrue(a1Entry.compareTo(a2Entry) == 0);
        assertTrue(a1Entry.compareTo(b1Entry) < 0);
        assertTrue(b1Entry.compareTo(a1Entry) > 0);
    }
    
    @Test
    public void testEquals() {
        assertTrue(a1Entry.equals(a1Entry));
        assertTrue(a1Entry.equals(a2Entry));
        assertFalse(a1Entry.equals(b1Entry));
        assertFalse(a2Entry.equals(b2Entry));
    }
}
