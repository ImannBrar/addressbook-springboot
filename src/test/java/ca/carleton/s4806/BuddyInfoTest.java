package ca.carleton.s4806;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuddyInfoTest {

    @Test
    void constructorAndGetters_work() {
        BuddyInfo b = new BuddyInfo("Alice", "555-0100");
        assertEquals("Alice", b.getName());
        assertEquals("555-0100", b.getPhone());
    }

    @Test
    void setters_updateFields() {
        BuddyInfo b = new BuddyInfo("A", "1");
        b.setName("Bob");
        b.setPhone("555-0200");
        assertEquals("Bob", b.getName());
        assertEquals("555-0200", b.getPhone());
    }

    @Test
    void toString_formatsNicely() {
        BuddyInfo b = new BuddyInfo("Alice", "555-0100");
        assertEquals("Alice (555-0100)", b.toString());
    }
}
