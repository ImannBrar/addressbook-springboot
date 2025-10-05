package ca.carleton.s4806;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddressBookTest {

    @Test
    void addBuddy_addsBuddy() {
        AddressBook book = new AddressBook();
        BuddyInfo alice = new BuddyInfo("Alice", "555-0100");
        book.addBuddy(alice);
        assertTrue(book.getBuddies().contains(alice));
    }

    @Test
    void addBuddy_ignoresNull() {
        AddressBook book = new AddressBook();
        book.addBuddy(null);
        assertTrue(book.getBuddies().isEmpty());
    }

    @Test
    void removeBuddy_removesAndReturnsTrueIfPresent() {
        AddressBook book = new AddressBook();
        BuddyInfo bob = new BuddyInfo("Bob", "555-0200");
        book.addBuddy(bob);
        assertTrue(book.removeBuddy(bob));
        assertFalse(book.getBuddies().contains(bob));
    }

    @Test
    void getBuddies_isUnmodifiable() {
        AddressBook book = new AddressBook();
        List<BuddyInfo> view = book.getBuddies();
        assertThrows(UnsupportedOperationException.class,
                () -> view.add(new BuddyInfo("Eve", "555-0300")));
    }

    @Test
    void toString_listsBuddies() {
        AddressBook book = new AddressBook();
        book.addBuddy(new BuddyInfo("Alice", "555-0100"));
        String s = book.toString();
        assertTrue(s.contains("AddressBook:"));
        assertTrue(s.contains("Alice (555-0100)"));
    }
}
